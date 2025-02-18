package git.skyblock.protocol.s2c.login;

import git.skyblock.network.buffers.ExpandingBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IServerPacket;

import java.util.UUID;

public class SLoginSuccess implements IServerPacket
{
    public String username;
    public UUID uuid;
    @Override
    public int id(ConnectionState state)
    {
        return 0x02;
    }

    @Override
    public void write(ExpandingBuffer buffer)
    {
        buffer.writeString(uuid.toString());
        buffer.writeString(username);
    }

    public SLoginSuccess(UUID uuid, String name)
    {
        this.username = name;
        this.uuid = uuid;
    }
}
