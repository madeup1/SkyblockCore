package git.skyblock.protocol.c2s.play;

import git.skyblock.SkyblockCore;
import git.skyblock.network.PlayerConnection;
import git.skyblock.network.buffers.FixedBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IClientPacket;

public class CChatMessage implements IClientPacket
{
    public String message;
    public CChatMessage(FixedBuffer buffer)
    {
        this.read(buffer);
    }

    @Override
    public void read(FixedBuffer buffer)
    {
        this.message = buffer.readString();
    }

    @Override
    public void process(PlayerConnection connection)
    {
        SkyblockCore.logger().info("Player said '" + this.message + "'");

        if (this.message.charAt(0) == '/')
        {
            SkyblockCore.commands().command(this.message, connection);
        }
    }
}
