package git.skyblock.network;

import git.skyblock.SkyblockCore;
import git.skyblock.optimizations.Threadable;
import git.skyblock.util.Flags;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class SocketListener
{
    private ServerSocket socket;
    private boolean running = false;
    public SocketListener()
    {
        try
        {
            this.socket = new ServerSocket(Flags.SERVER_PORT);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void start() throws SocketException
    {
        this.running = true;
        this.socket.setReuseAddress(Flags.SOCKET_REUSE_ADDRESS);
        this.socket.setReceiveBufferSize(Flags.SOCKET_BUFFER_SIZE_RECEIVE);
    }

    public void poll()
    {
        try
        {
            Socket socket = this.socket.accept();

            SkyblockCore.connections().add(new PlayerConnection(socket));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void stop()
    {
        this.running = false;
    }
}
