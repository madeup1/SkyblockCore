package git.skyblock.protocol.s2c.play;

import git.skyblock.entities.EntityPlayer;
import git.skyblock.network.PlayerConnection;
import git.skyblock.network.buffers.ExpandingBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IServerPacket;

public class SJoinGame implements IServerPacket
{
    private PlayerConnection connection;
    public SJoinGame(PlayerConnection connection)
    {
        this.connection = connection;
    }

    @Override
    public int id(ConnectionState state)
    {
        return 0x01;
    }

    @Override
    public void write(ExpandingBuffer buffer)
    {
        EntityPlayer player = connection.player();

        buffer.writeInt(player.entityId());
        buffer.writeByte(player.gamemode().mode());
        buffer.writeByte((byte) (player.world().dimension().dimension()));
        buffer.writeByte((byte) (player.world().difficulty().value()));
        buffer.writeByte((byte) 255);
        buffer.writeString("flat");
        buffer.writeBoolean(false);
    }
}
