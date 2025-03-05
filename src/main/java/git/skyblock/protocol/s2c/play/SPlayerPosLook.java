package git.skyblock.protocol.s2c.play;

import git.skyblock.SkyblockCore;
import git.skyblock.network.buffers.ExpandingBuffer;
import git.skyblock.position.Vec3;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IServerPacket;

public class SPlayerPosLook implements IServerPacket
{
    public Vec3 position;
    public float yaw;
    public float pitch;

    public SPlayerPosLook(Vec3 position, float yaw, float pitch)
    {
        this.position = position;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public int id(ConnectionState state)
    {
        return 0x08;
    }

    @Override
    public void write(ExpandingBuffer buffer)
    {
        buffer.writeVec3(this.position);

        SkyblockCore.logger().info("POSITION:" + this.position.toString());

        buffer.writeFloat(yaw);
        buffer.writeFloat(pitch);
        buffer.writeByte((byte) 0);
    }
}
