package git.skyblock.entities;

import git.skyblock.network.PlayerConnection;

public class EntityPlayer extends Entity
{
    private String name;
    private PlayerConnection connection;
    public EntityPlayer(PlayerConnection connection)
    {
        this.name = connection.name();
        this.connection = connection;
    }
}
