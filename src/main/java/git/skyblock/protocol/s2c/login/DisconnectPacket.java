package git.skyblock.protocol.s2c.login;

import git.skyblock.network.buffers.ExpandingBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IServerPacket;

public class DisconnectPacket implements IServerPacket
{
    @Override
    public int id(ConnectionState state)
    {
        if (state == ConnectionState.Play)
            return 0x40;
        return 0x00;
    }

    @Override
    public void write(ExpandingBuffer buffer)
    {

    }
}
