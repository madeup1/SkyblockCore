package git.skyblock.blocks;

import git.skyblock.SkyblockCore;
import git.skyblock.materials.MaterialEntry;

public class Block
{
    private int id;
    private int metadata;
    public Block(int id, int metadata)
    {
        this.id = id;
        this.metadata = metadata;
    }

    public Block(MaterialEntry entry)
    {
        this(entry.id(), entry.metadata());
    }

    public MaterialEntry material()
    {
        if (SkyblockCore.materials().has(id, metadata))
        {
            return SkyblockCore.materials().find(id, metadata);
        }
        return SkyblockCore.materials().find(this.id);
    }

    public int id()
    {
        return this.id;
    }

    public int metadata()
    {
        return this.metadata;
    }

    public void withMetadata(int value)
    {
        this.metadata = value;
    }

    public void withId(int id)
    {
        this.id = id;
    }
}
