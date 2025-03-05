package git.skyblock.protocol.c2s.play;

import git.skyblock.network.PlayerConnection;
import git.skyblock.network.buffers.FixedBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IClientPacket;

public class CKeepAlive implements IClientPacket
{



    public CKeepAlive(FixedBuffer buffer)
    {
        this.read(buffer);
    }

    @Override
    public void read(FixedBuffer buffer)
    {

    }

    @Override
    public void process(PlayerConnection connection)
    {

    }
}
