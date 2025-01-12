package git.skyblock.protocol.c2s.status;

import git.skyblock.SkyblockCore;
import git.skyblock.network.PlayerConnection;
import git.skyblock.network.buffers.FixedBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IClientPacket;
import git.skyblock.protocol.s2c.status.SStatusResponse;
import git.skyblock.util.status.StatusResponse;

public class CStatusRequest implements IClientPacket
{
    public CStatusRequest(FixedBuffer buffer)
    {
        this.read(buffer);
    }

    @Override
    public int id(ConnectionState state)
    {
        return 0;
    }

    @Override
    public void read(FixedBuffer buffer)
    {

    }

    @Override
    public void process(PlayerConnection connection)
    {
        SkyblockCore.logger().info("sent packet SStatusResponse!");
        connection.sendPacket(new SStatusResponse(new StatusResponse("skyblockcore", 47, 1000, 0, "The best server ever")));
    }
}
