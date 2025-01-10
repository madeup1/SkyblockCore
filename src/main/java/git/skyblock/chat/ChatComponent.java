package git.skyblock.chat;

public class ChatComponent
{
    private final String message;
    public ChatComponent(String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return "{\"text\":\"" + this.message + "\"}";
    }
}
