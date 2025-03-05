package git.skyblock.protocol.c2s.play;

import git.skyblock.entities.EntityPlayer;
import git.skyblock.network.PlayerConnection;
import git.skyblock.network.buffers.FixedBuffer;
import git.skyblock.position.Vec3;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IClientPacket;

public class CPlayerPositionLook implements IClientPacket
{
    public Vec3 position;
    public float yaw;
    public float pitch;
    public boolean onGround;
    public CPlayerPositionLook(FixedBuffer buffer)
    {
        this.read(buffer);
    }

    @Override
    public void read(FixedBuffer buffer)
    {
        position = new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());

        yaw = buffer.readFloat();
        pitch = buffer.readFloat();
        onGround = buffer.readBoolean();
    }

    @Override
    public void process(PlayerConnection connection)
    {
        EntityPlayer player = connection.player();

        player.setPosition(position);
        player.setYaw(yaw);
        player.setPitch(pitch);
        player.setOnGround(onGround);
    }
}
