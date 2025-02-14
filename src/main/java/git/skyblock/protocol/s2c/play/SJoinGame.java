package git.skyblock.protocol.s2c.play;

import git.skyblock.network.buffers.ExpandingBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IServerPacket;

public class SJoinGame implements IServerPacket
{
    @Override
    public int id(ConnectionState state)
    {
        return 0;
    }

    @Override
    public void write(ExpandingBuffer buffer)
    {

    }
}
