package git.skyblock.protocol.s2c.play;

import git.skyblock.chat.ChatComponent;
import git.skyblock.minecraft.ChatPosition;
import git.skyblock.network.buffers.ExpandingBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IServerPacket;

public class SChatMessage implements IServerPacket
{
    public ChatComponent component;
    public ChatPosition chatPosition;

    public SChatMessage(ChatComponent component, ChatPosition chatPosition)
    {
        this.component = component;
        this.chatPosition = chatPosition;
    }

    public SChatMessage(ChatComponent component)
    {
        this(component, ChatPosition.ChatBox);
    }

    public SChatMessage(String text)
    {
        this(new ChatComponent(text), ChatPosition.ChatBox);
    }

    @Override
    public int id(ConnectionState state)
    {
        return 0x02;
    }

    @Override
    public void write(ExpandingBuffer buffer)
    {
        buffer.writeString(component.toString());
        buffer.writeByte(chatPosition.value());
    }
}
