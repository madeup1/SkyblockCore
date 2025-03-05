package git.skyblock.protocol.s2c.play;

import git.skyblock.network.buffers.ExpandingBuffer;
import git.skyblock.position.Vec3;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IServerPacket;

public class SSpawnPosition implements IServerPacket
{
    private Vec3 position;
    public SSpawnPosition(Vec3 position)
    {
        this.position = position;
    }

    @Override
    public int id(ConnectionState state)
    {
        return 0x05;
    }

    @Override
    public void write(ExpandingBuffer buffer)
    {
        long data = (((long) position.x() & 0x3FFFFFF) << 38) | (((long) position.y() & 0xFFF) << 26) | ((long) position.z() & 0x3FFFFFF);

        buffer.writeLong(data);
    }
}
