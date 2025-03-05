package git.skyblock.minecraft;

public enum Gamemode
{
    Creative((byte) 1),
    Survival((byte) 0),
    Adventure((byte) 2),
    Spectator((byte) 3);

    private byte mode;
    Gamemode(byte mode)
    {
        this.mode = mode;
    }

    public byte mode()
    {
        return this.mode;
    }
}
