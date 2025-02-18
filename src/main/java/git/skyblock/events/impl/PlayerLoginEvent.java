package git.skyblock.events.impl;

import git.skyblock.events.CancellableEvent;
import git.skyblock.network.PlayerConnection;

public class PlayerLoginEvent extends CancellableEvent
{
    private PlayerConnection connection;
    public PlayerLoginEvent(PlayerConnection connection)
    {
        this.connection = connection;
    }

    public PlayerConnection connection()
    {
        return connection;
    }
}
