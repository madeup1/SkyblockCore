package git.skyblock.network;

import git.skyblock.util.Lock;

import java.util.LinkedList;
import java.util.function.Consumer;

public class ConnectionManager
{
    private int count = 0;
    private final LinkedList<PlayerConnection> connections;
    private final Lock lock = new Lock();
    public ConnectionManager()
    {
        this.connections = new LinkedList<>();
    }

    public void forEach(Consumer<PlayerConnection> consumer)
    {
        if (count == 0)
        {
            return;
        }
        lock.lock();
        connections.forEach(consumer);
        lock.unlock();
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
