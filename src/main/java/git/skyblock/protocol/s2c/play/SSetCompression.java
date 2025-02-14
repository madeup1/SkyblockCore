package git.skyblock.protocol.s2c.play;

import git.skyblock.network.buffers.ExpandingBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IServerPacket;

public class SSetCompression implements IServerPacket
{
    private int compression;
    public SSetCompression(int compression)
    {
        this.compression = compression;
    }

    @Override
    public int id(ConnectionState state)
    {
        return 0x46;
    }

    @Override
    public void write(ExpandingBuffer buffer)
    {
        buffer.writeVarInt(compression);
    }
}
