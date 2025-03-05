package git.skyblock.network;

import git.skyblock.SkyblockCore;
import git.skyblock.crypt.CryptPair;
import git.skyblock.entities.EntityPlayer;
import git.skyblock.network.buffers.ExpandingBuffer;
import git.skyblock.network.buffers.FixedBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IClientPacket;
import git.skyblock.protocol.IServerPacket;
import git.skyblock.util.EntityUtils;
import git.skyblock.util.Flags;
import git.skyblock.util.PacketUtils;
import git.skyblock.util.ZlibUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;

public class PlayerConnection
{
    private final Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private boolean connected = true;
    private int compressionThreshold = -1;
    private boolean authenticated = false;
    private String name;
    private UUID uuid;
    private int protocol = 0;
    private CryptPair cryptPair;

    // player
    private EntityPlayer localPlayer;

    private ConnectionState state = ConnectionState.Handshake;
    public PlayerConnection(Socket socket)
    {
        this.socket = socket;

        try
        {
            this.inputStream = this.socket.getInputStream();
            this.outputStream = this.socket.getOutputStream();

            this.socket.setTcpNoDelay(Flags.SOCKET_NO_DELAY);
            this.socket.setReceiveBufferSize(Flags.SOCKET_BUFFER_SIZE_RECEIVE);
            this.socket.setSendBufferSize(Flags.SOCKET_BUFFER_SIZE_SEND);
            this.socket.setSoTimeout(Flags.SOCKET_TIMEOUT);
        }
        catch (Exception e)
        {
            SkyblockCore.logger().error(e.toString());
        }
    }

    public EntityPlayer player()
    {
        return this.localPlayer;
    }

    public void tick()
    {
        this.poll();

        if (this.localPlayer != null)
            this.localPlayer.tick();
    }

    public void poll()
    {
        try
        {
            while (inputStream.available() > 0)
            {
                int packetLen = PacketUtils.readVarInt(inputStream);
                byte[] data = new byte[packetLen];

                if (compressionThreshold != -1)
                {
                    int dataLength = PacketUtils.readVarInt(inputStream);
                    int actualLength = packetLen - PacketUtils.getVarIntLength(dataLength);

                    if (dataLength == 0)
                    {
                        data = new byte[actualLength];
                    } else
                    {
                        data = new byte[dataLength];
                    }
                }

                int check = inputStream.read(data);

                if (cryptPair != null)
                {
                    data = cryptPair.decrypt(data);
                }

                FixedBuffer buffer = new FixedBuffer(data);

                int packId = buffer.readVarInt();

                //SkyblockCore.logger().info("nerd info: packid: " + packId + " len: " + buffer.length());
                Function<FixedBuffer, IClientPacket> consumer = SkyblockCore.packets().find(this.state, packId);
                if (consumer == null)
                {
                    // this.disconnect();

                    System.out.println("State is " + this.state);
                    System.out.println("Cant find the right packet! id is " + packId);

                    return;
                }

                IClientPacket packet = consumer.apply(buffer);
                //SkyblockCore.logger().info("Packet class is " + packet.getClass().getSimpleName());
                packet.process(this);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

            this.disconnect();
        }
    }

    public void sendPacket(IServerPacket packet)
    {
        ExpandingBuffer buffer = new ExpandingBuffer();

        /*SkyblockCore.logger().info("id is " + packet.id(this.state));

        if (compressionThreshold == -1)
        {
            buffer.writeVarInt(packet.id(this.state));
            packet.write(buffer);

            byte[] data = buffer.compile();

            buffer.clear();
            buffer.writeVarInt(data.length);
            buffer.write(data);
        }
        else
        {
            buffer.writeVarInt(packet.id(this.state));
            packet.write(buffer);
            byte[] compiled = buffer.compile();

            boolean shouldCompress = (buffer.length() >= compressionThreshold);
            int dataLength = shouldCompress ? buffer.length() : 0;

            int dLength = PacketUtils.getVarIntLength(dataLength);

            byte[] compressed = ZlibUtils.compress(compiled);
            int packetLen = compressed.length + dLength;

            byte[] usedData = shouldCompress ? compressed : compiled;

            buffer.clear();
            buffer.writeVarInt(packetLen);
            buffer.writeVarInt(dataLength);
            buffer.write(usedData);
        }*/

        buffer.writeVarInt(packet.id(state));
        packet.write(buffer);

        byte[] data = buffer.compile();

        buffer.clear();

        boolean compress = (data.length >= compressionThreshold) && compressionThreshold != -1;

        int bonusLength = (compressionThreshold == -1 ? 0 : 1);
        int preCompressLength = data.length;

        if (compress)
        {
            data = ZlibUtils.compress(data);

            // SkyblockCore.logger().info("Compressed packet is " + packet.getClass().getSimpleName());
        }

        int dataLength = data.length;

        if (compress)
        {
            int packetLen = dataLength + PacketUtils.getVarIntLength(preCompressLength);

            buffer.writeVarInt(packetLen);
            buffer.writeVarInt(preCompressLength);

            //SkyblockCore.logger().info("Packet Length: " + packetLen);
            //SkyblockCore.logger().info("Data Length: " + preCompressLength);
            //SkyblockCore.logger().info("Difference: " + (preCompressLength - packetLen));

        }
        else
        {
            buffer.writeVarInt(dataLength + bonusLength);
            if (compressionThreshold != -1)
                buffer.writeVarInt(0);
        }

        buffer.write(data);


        try
        {
            //SkyblockCore.logger().info("Packet size send is " + buffer.length());
            //SkyblockCore.logger().info("Packet length = " + (dataLength + bonusLength));
            this.outputStream.write(buffer.compile());
            // this.outputStream.flush();
        } catch (IOException e)
        {
            // e.printStackTrace();

            this.disconnect();
        }
    }

    public void authenticate()
    {
        this.authenticated = true;
        this.uuid = UUID.randomUUID();

        this.localPlayer = new EntityPlayer(this);
    }

    public String name()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public UUID uuid()
    {
        return this.uuid;
    }

    public int protocol()
    {
        return this.protocol;
    }

    public void setProtocol(int version)
    {
        this.protocol = version;
    }

    public ConnectionState state()
    {
        return state;
    }

    public void setState(ConnectionState state)
    {
        this.state = state;
    }

    public boolean connected()
    {
        return this.connected;
    }

    public boolean authenticated()
    {
        return this.authenticated;
    }

    public void setCompression(int level)
    {
        this.compressionThreshold = level;
    }

    public void disconnect()
    {
        this.connected = false;

        // TODO: cleanup
        try
        {
            this.socket.close();
            SkyblockCore.connections().remove(this);
        }
        catch (Exception e)
        {
            SkyblockCore.logger().error(e.toString());
        }
    }

    public CryptPair cryptPair()
    {
        return this.cryptPair;
    }

    public void setSharedSecret(SecretKey spec)
    {
        this.cryptPair = new CryptPair(spec);
    }
}
