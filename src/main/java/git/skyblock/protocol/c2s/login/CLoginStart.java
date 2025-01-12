package git.skyblock.protocol.c2s.login;

import git.skyblock.network.PlayerConnection;
import git.skyblock.network.buffers.FixedBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IClientPacket;
import git.skyblock.protocol.s2c.login.DisconnectPacket;

public class CLoginStart implements IClientPacket
{
    public String name;
    public CLoginStart(FixedBuffer buffer)
    {
        this.read(buffer);
    }

    @Override
    public int id(ConnectionState state)
    {
        return 0;
    }

    @Override
    public void read(FixedBuffer buffer)
    {
        this.name = buffer.readString();
    }

    @Override
    public void process(PlayerConnection connection)
    {
        connection.sendPacket(new DisconnectPacket("bad"));
    }
}
