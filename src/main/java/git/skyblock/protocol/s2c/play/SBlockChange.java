package git.skyblock.protocol.s2c.play;

import git.skyblock.blocks.Block;
import git.skyblock.network.buffers.ExpandingBuffer;
import git.skyblock.position.BlockPos;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IServerPacket;

public class SBlockChange implements IServerPacket
{
    public BlockPos position;
    public Block block;
    public SBlockChange(BlockPos pos, Block block)
    {
        this.position = pos;
        this.block = block;
    }

    @Override
    public int id(ConnectionState state)
    {
        return 0x23;
    }

    @Override
    public void write(ExpandingBuffer buffer)
    {
        buffer.writeBlockPos(this.position);
        buffer.writeVarInt(block.id() << 4 | block.metadata());
    }
}
