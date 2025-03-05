package git.skyblock.protocol.c2s.play;

import git.skyblock.network.PlayerConnection;
import git.skyblock.network.buffers.FixedBuffer;
import git.skyblock.position.Vec3;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IClientPacket;

public class CPlayerPosition implements IClientPacket
{
    public Vec3 position;
    public boolean onGround;

    public CPlayerPosition(FixedBuffer buffer)
    {
        this.read(buffer);
    }

    @Override
    public void read(FixedBuffer buffer)
    {
        position = new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
        onGround = buffer.readBoolean();
    }

    @Override
    public void process(PlayerConnection connection)
    {
        connection.player().setPosition(position);
        connection.player().setOnGround(onGround);
    }
}
