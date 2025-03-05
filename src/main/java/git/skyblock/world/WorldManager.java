package git.skyblock.world;

import java.util.LinkedList;

public class WorldManager
{
    private IWorldProvider defaultWorld;
    private LinkedList<IWorldProvider> worlds;
    public WorldManager(IWorldProvider defaultWorld)
    {
        this.defaultWorld = defaultWorld;
        this.worlds = new LinkedList<>();
        this.worlds.add(defaultWorld);
    }

    public IWorldProvider defaultWorld()
    {
        return this.defaultWorld;
    }

    public LinkedList<IWorldProvider> worlds()
    {
        return this.worlds;
    }
}
