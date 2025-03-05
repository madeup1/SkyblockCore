package git.skyblock.protocol.c2s.play;

import git.skyblock.network.PlayerConnection;
import git.skyblock.network.buffers.FixedBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IClientPacket;

public class CClientSettings implements IClientPacket
{
    public String locale;
    public int viewDistance;
    public int chatFlags;
    public boolean chatColors;
    public byte skinParts;
    public int mainHand;

    public CClientSettings(FixedBuffer buffer)
    {
        this.read(buffer);
    }

    @Override
    public void read(FixedBuffer buffer)
    {
        this.locale = buffer.readString();
        this.viewDistance = buffer.readByte();
        this.chatFlags = buffer.readVarInt();
        this.chatColors = buffer.readBoolean();
        this.skinParts = buffer.readByte();
        // this.mainHand = buffer.readVarInt();
    }

    @Override
    public void process(PlayerConnection connection)
    {
        connection.player().viewDistance = this.viewDistance;
    }
}
