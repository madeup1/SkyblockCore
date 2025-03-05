package git.skyblock.entities;

import git.skyblock.position.Vec2;
import git.skyblock.position.Vec3;
import git.skyblock.util.EntityUtils;

public abstract class Entity
{
    private int entityId;
    private Vec3 position;

    private float yaw;
    private float pitch;

    public Entity()
    {
        this.entityId = EntityUtils.getEntityId();
        this.position = new Vec3(0, 0, 0);
        this.yaw = 0;
        this.pitch = 0;
    }

    public int entityId()
    {
        return this.entityId;
    }

    public Vec3 position()
    {
        return this.position;
    }

    public void setPosition(Vec3 pos)
    {
        this.position = pos;
    }

    public Vec2 getChunkPosition()
    {
        return new Vec2((int) this.position.x() << 4, (int) this.position.y() << 4);
    }

    public float yaw()
    {
        return this.yaw;
    }

    public float pitch()
    {
        return this.pitch;
    }

    public void setYaw(float value)
    {
        this.yaw = value;
    }

    public void setPitch(float value)
    {
        this.pitch = value;
    }
}
