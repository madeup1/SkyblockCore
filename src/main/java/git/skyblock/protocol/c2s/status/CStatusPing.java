package git.skyblock.protocol.c2s.status;

import git.skyblock.network.PlayerConnection;
import git.skyblock.network.buffers.FixedBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IClientPacket;
import git.skyblock.protocol.s2c.status.SStatusPong;

public class CStatusPing implements IClientPacket
{
    public long time;
    public CStatusPing(FixedBuffer buffer)
    {
        this.read(buffer);
    }

    @Override
    public int id(ConnectionState state)
    {
        return 0x01;
    }

    @Override
    public void read(FixedBuffer buffer)
    {
        this.time = buffer.readLong();
    }

    @Override
    public void process(PlayerConnection connection)
    {
        connection.sendPacket(new SStatusPong(this.time));
    }
}
