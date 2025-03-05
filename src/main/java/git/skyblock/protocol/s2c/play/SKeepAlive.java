package git.skyblock.protocol.s2c.play;

import git.skyblock.network.buffers.ExpandingBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IServerPacket;

public class SKeepAlive implements IServerPacket
{
    public int keepAliveId;
    public SKeepAlive(int aliveId)
    {
        this.keepAliveId = aliveId;
    }

    @Override
    public int id(ConnectionState state)
    {
        return 0x00;
    }

    @Override
    public void write(ExpandingBuffer buffer)
    {
        buffer.writeVarInt(this.keepAliveId);
    }
}
