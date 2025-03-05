package git.skyblock.world;

import git.skyblock.SkyblockCore;
import git.skyblock.blocks.Block;
import git.skyblock.position.BlockPos;
import git.skyblock.position.Vec2;

public class Chunk
{
    // contains id + metadata
    // first 16 bits is id, next 16 is metadata
    private final byte[] blocks = new byte[16 * 16 * 256 * 2]; // THIS IS BECAUSE IT MAKES IT WAY FASTER TO SEND
    public Chunk()
    {

    }

    public void setBlock(Block block, int x, int y, int z)
    {
        this.setBlock(block, new BlockPos(x, y, z));
    }

    public void setBlock(Block block, BlockPos pos)
    {
        int index = (pos.x() + 16*pos.z() + 16*16*pos.y()) * 2;

        if (index >= 0 && index < blocks.length)
        {
            short value = (short) (block.id() << 4 | block.metadata());

            this.blocks[index] = (byte) (value >> 8);
            this.blocks[index+1] = (byte) (value);

            // SkyblockCore.logger().info("b:(" + this.blocks[index] + "," + this.blocks[index+1] + ")");
        }
    }

    public Block getBlock(int x, int y, int z)
    {
        return this.getBlock(new BlockPos(x, y, z));
    }

    public Block getBlock(BlockPos pos)
    {
        int index = (pos.x() + 16*pos.z() + 16*16*pos.y()) * 2;

        if (index >= 0 && index < blocks.length)
        {
            short value = (short) (((this.blocks[index] & 0xFF) << 8) | (this.blocks[index+1] & 0xFF));

            int id = value >> 4;
            int metadata = value - (id << 4);

            return new Block(id, metadata);
        }

        return null;
    }

    public byte[] bytes()
    {
        return this.blocks;
    }
}
