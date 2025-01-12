package git.skyblock.protocol;

public enum ConnectionState
{
    Play(300),
    Status(200),
    Login(100),
    Handshake(0);

    private int offset;
    ConnectionState(int offset)
    {
        this.offset = offset;
    }

    public int offset()
    {
        return this.offset;
    }
}
