package git.skyblock.protocol.s2c.login;

import git.skyblock.chat.ChatComponent;
import git.skyblock.network.buffers.ExpandingBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IServerPacket;

public class SDisconnectPacket implements IServerPacket
{
    private ChatComponent reason;
    public SDisconnectPacket(String reason)
    {
        this.reason = new ChatComponent(reason);
    }

    @Override
    public int id(ConnectionState state)
    {
        return state == ConnectionState.Play ? 0x40 : 0x00;
    }

    @Override
    public void write(ExpandingBuffer buffer)
    {
        buffer.writeString(reason.toString());
    }
}
