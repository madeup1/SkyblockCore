package git.skyblock.materials;

import git.skyblock.blocks.Block;
import git.skyblock.items.Item;

public class MaterialEntry
{
    private final int id;
    private final int metadata;
    private final String namespace;
    private final String displayName;
    public boolean isBlock = false;
    private int maxStackSize;
    public MaterialEntry(int id, int metadata, String namespace, String displayName, int maxStackSize)
    {
        this.id = id;
        this.metadata = metadata;
        this.namespace = namespace;
        this.displayName = displayName;
        this.maxStackSize = maxStackSize;
    }

    public Block toBlock()
    {
        if (this.isBlock)
            return new Block(this);
        return null;
    }

    public Item toItem()
    {
        return new Item();
    }

    public int id()
    {
        return this.id;
    }

    public int metadata()
    {
        return this.metadata;
    }

    public String namespace()
    {
        return this.namespace;
    }

    public boolean isBlock()
    {
        return this.isBlock;
    }
}
