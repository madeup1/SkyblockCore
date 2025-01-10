package git.skyblock.chat;

public enum MessageType
{
    CHAT(0),
    SYSTEM(1),
    ACTIONBAR(2);

    private final int id;
    MessageType(int internalId)
    {
        this.id = internalId;
    }

    public int id()
    {
        return this.id;
    }
}
