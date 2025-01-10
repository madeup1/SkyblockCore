package git.skyblock.network;

import git.skyblock.util.Lock;

import java.util.LinkedList;

public class ConnectionManager
{
    private int count = 0;
    private final LinkedList<PlayerConnection> connections;
    private final Lock lock = new Lock();
    public ConnectionManager()
    {
        this.connections = new LinkedList<>();
    }

    public void add(PlayerConnection connection)
    {
        lock.schedule(() -> {
            connections.addLast(connection);

            count++;
        });
    }

    public void remove(PlayerConnection connection)
    {
        lock.schedule(() -> {
            connections.remove(connection);

            count--;
        });
    }
}
