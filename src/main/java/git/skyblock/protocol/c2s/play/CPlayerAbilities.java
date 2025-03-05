package git.skyblock.protocol.c2s.play;

import git.skyblock.network.PlayerConnection;
import git.skyblock.network.buffers.FixedBuffer;
import git.skyblock.protocol.IClientPacket;

public class CPlayerAbilities implements IClientPacket
{
    public byte flags;
    public float flySpeed;
    public float walkSpeed;

    public CPlayerAbilities(FixedBuffer buffer)
    {
        this.read(buffer);
    }

    @Override
    public void read(FixedBuffer buffer)
    {
        this.flags = buffer.readByte();
        this.flySpeed = buffer.readFloat();
        this.walkSpeed = buffer.readFloat();
    }

    @Override
    public void process(PlayerConnection connection)
    {

    }
}
