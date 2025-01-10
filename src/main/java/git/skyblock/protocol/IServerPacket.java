package git.skyblock.protocol;

import git.skyblock.network.buffers.ExpandingBuffer;

public interface IServerPacket
{
    int id(ConnectionState state);
    void write(ExpandingBuffer buffer);
}
