package git.skyblock.protocol.c2s.handshake;

import git.skyblock.SkyblockCore;
import git.skyblock.network.PlayerConnection;
import git.skyblock.network.buffers.FixedBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IClientPacket;

public class CHandshake implements IClientPacket
{
    public CHandshake(FixedBuffer buffer)
    {
        this.read(buffer);
    }

    public int protocolVersion;
    public String serverAddress;
    public short serverPort;
    public ConnectionState state;
    @Override
    public int id(ConnectionState state)
    {
        return 0;
    }

    @Override
    public void read(FixedBuffer buffer)
    {
        this.protocolVersion = buffer.readVarInt();
        this.serverAddress = buffer.readString();
        this.serverPort = buffer.readShort();
        this.state = buffer.readVarInt() == 1 ? ConnectionState.Status : ConnectionState.Login;
    }

    @Override
    public void process(PlayerConnection connection)
    {
        if (this.protocolVersion != 47)
        {
            connection.disconnect();
            return;
        }
        connection.setState(state);
    }
}
