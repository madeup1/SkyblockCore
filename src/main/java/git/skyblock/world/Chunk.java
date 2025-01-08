package git.skyblock.world;

import git.skyblock.SkyblockCore;
import git.skyblock.blocks.Block;
import git.skyblock.position.Vec3;

public class Chunk
{
    // contains id + metadata
    // first 16 bits is id, next 16 is metadata
    private final int[] blocks = new int[16*16*256];
    public Chunk()
    {

    }

    public void setBlock(Block block, int x, int y, int z)
    {
        int index = x + 16*z + 16*16*y;

        if (index >= 0 && index < blocks.length)
        {
            int value = (block.id() << 16 | block.metadata());
            this.blocks[index] =  value;
        }
    }

    public Block getBlock(int x, int y, int z)
    {
        int index = x + 16*z + 16*16*y;

        if (index >= 0 && index < blocks.length)
        {
            int value = this.blocks[index];
            int id = (value >> 16);
            int metadata = value - (id << 16);

            return new Block(id, metadata);
        }

        return null;
    }
}
