package git.skyblock.protocol.c2s.play;

import git.skyblock.entities.EntityPlayer;
import git.skyblock.network.PlayerConnection;
import git.skyblock.network.buffers.FixedBuffer;
import git.skyblock.protocol.IClientPacket;

public class CPlayerLook implements IClientPacket
{
    public float yaw;
    public float pitch;
    public boolean onGround;

    public CPlayerLook(FixedBuffer buffer)
    {
        this.read(buffer);
    }

    @Override
    public void read(FixedBuffer buffer)
    {
        this.yaw = buffer.readFloat();
        this.pitch = buffer.readFloat();
        this.onGround = buffer.readBoolean();
    }

    @Override
    public void process(PlayerConnection connection)
    {
        EntityPlayer player = connection.player();

        player.setYaw(this.yaw);
        player.setPitch(this.pitch);
        player.setOnGround(onGround);
    }
}
