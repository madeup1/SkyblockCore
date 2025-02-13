package git.skyblock.protocol.s2c.status;

import git.skyblock.network.buffers.ExpandingBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IServerPacket;

public class SStatusPong implements IServerPacket
{
    public long time;
    public SStatusPong(long time)
    {
        this.time = time;
    }
    @Override
    public int id(ConnectionState state)
    {
        return 0x01;
    }

    @Override
    public void write(ExpandingBuffer buffer)
    {
        buffer.writeLong(time);
    }
}
