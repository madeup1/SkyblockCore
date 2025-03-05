package git.skyblock.protocol.s2c.play;

import git.skyblock.entities.Entity;
import git.skyblock.network.buffers.ExpandingBuffer;
import git.skyblock.position.Vec3;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IServerPacket;

public class SEntityTeleport implements IServerPacket
{
    public Vec3 position;
    public Entity entity;
    public float yaw;
    public float pitch;
    public boolean onGround;
    public SEntityTeleport(Vec3 position, Entity entity, float yaw, float pitch, boolean onGround)
    {
        this.position = position;
        this.entity = entity;
        this.yaw = yaw;
        this.pitch = pitch;
        this.onGround = onGround;
    }


    @Override
    public int id(ConnectionState state)
    {
        return 0x18;
    }

    @Override
    public void write(ExpandingBuffer buffer)
    {
        buffer.writeVarInt(entity.entityId());
        buffer.writeInt((int) (position.x() * 32));
        buffer.writeInt((int) (position.y() * 32));
        buffer.writeInt((int) (position.z() * 32));
        buffer.writeByte((byte)((yaw / 360) * 256));
        buffer.writeByte((byte)pitch);
        buffer.writeBoolean(onGround);
    }
}
