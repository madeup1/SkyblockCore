package git.skyblock.protocol;

import git.skyblock.network.PlayerConnection;
import git.skyblock.network.buffers.FixedBuffer;

public interface IClientPacket
{
    int id(ConnectionState state);
    void read(FixedBuffer buffer);
    void process(PlayerConnection connection);
}
