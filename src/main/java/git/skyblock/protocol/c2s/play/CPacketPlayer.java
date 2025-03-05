package git.skyblock.protocol.c2s.play;

import git.skyblock.network.PlayerConnection;
import git.skyblock.network.buffers.FixedBuffer;
import git.skyblock.protocol.IClientPacket;

public class CPacketPlayer implements IClientPacket
{
    public boolean onGround;
    public CPacketPlayer(FixedBuffer buffer)
    {
        this.read(buffer);
    }

    @Override
    public void read(FixedBuffer buffer)
    {
        this.onGround = buffer.readBoolean();
    }

    @Override
    public void process(PlayerConnection connection)
    {
        connection.player().setOnGround(onGround);
    }
}
