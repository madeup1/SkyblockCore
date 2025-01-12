package git.skyblock.network;

import git.skyblock.SkyblockCore;
import git.skyblock.network.buffers.ExpandingBuffer;
import git.skyblock.network.buffers.FixedBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IClientPacket;
import git.skyblock.protocol.IServerPacket;
import git.skyblock.util.Flags;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.channels.ServerSocketChannel;
import java.util.function.Consumer;
import java.util.function.Function;

public class PlayerConnection
{
    private final Socket socket;
    private InputStream inputStream;
    private OutputStream outputStream;
    private boolean connected = true;
    private int compressionThreshold = -1;
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

    public void poll()
    {
        try
        {
            if (inputStream.available() == 0)
            {
                return;
            }

            int len = inputStream.read();

            if (len == -1)
            {
                this.disconnect();

                return;
            }

            byte[] data = new byte[len];

            int check = inputStream.read(data);
            FixedBuffer buffer = new FixedBuffer(data);
            int packId = buffer.readVarInt();

            SkyblockCore.logger().info("PacketID is " + packId + "\n len is " + buffer.length());

            Function<FixedBuffer, IClientPacket> consumer = SkyblockCore.packets().find(this.state, packId);
            if (consumer == null)
            {
                this.disconnect();
                return;
            }

            IClientPacket packet = consumer.apply(buffer);
            SkyblockCore.logger().info("Packet class is " + packet.getClass().getSimpleName());
            packet.process(this);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void sendPacket(IServerPacket packet)
    {
        ExpandingBuffer buffer = new ExpandingBuffer();

        SkyblockCore.logger().info("id is " + packet.id(this.state));

        if (compressionThreshold == -1)
        {
            buffer.writeVarInt(packet.id(this.state));
            packet.write(buffer);
            buffer.writeVarIntBefore(buffer.length());
        }
        else
        {
            
        }

        try
        {
            this.outputStream.write(buffer.compile());
            this.outputStream.flush();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
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
}
