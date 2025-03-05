package git.skyblock.minecraft;

public enum ChatPosition
{
    ChatBox((byte) 0),
    SystemMessage((byte) 1),
    ActionBar((byte) 2);

    byte value;
    ChatPosition(byte value)
    {
        this.value = value;
    }

    public byte value()
    {
        return this.value;
    }
}
