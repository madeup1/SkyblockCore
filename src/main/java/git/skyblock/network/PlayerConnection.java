package git.skyblock.network;

import git.skyblock.SkyblockCore;

import java.net.Socket;

public class PlayerConnection
{
    private final Socket socket;
    private boolean connected = true;
    public PlayerConnection(Socket socket)
    {
        this.socket = socket;
    }

    public boolean connected()
    {
        return this.connected;
    }

    public void disconnect()
    {
        this.connected = false;

        // TODO: cleanup
        try
        {
            this.socket.close();
        }
        catch (Exception e)
        {
            SkyblockCore.logger().error(e.toString());
        }
    }
}
