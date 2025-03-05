package git.skyblock.protocol.s2c.play;

import git.skyblock.network.buffers.ExpandingBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IServerPacket;
import git.skyblock.world.Chunk;
import git.skyblock.world.ChunkInfo;

import java.util.LinkedList;
import java.util.List;

public class SMapChunkBulk implements IServerPacket
{
    public List<ChunkInfo> chunks;

    public SMapChunkBulk(List<ChunkInfo> chunks)
    {
        this.chunks = chunks;
    }


    @Override
    public int id(ConnectionState state)
    {
        return 0x26;
    }

    @Override
    public void write(ExpandingBuffer buffer)
    {
        buffer.writeBoolean(false);
        buffer.writeVarInt(chunks.size());

        chunks.forEach(c -> {
            buffer.writeInt(c.pos.x());
            buffer.writeInt(c.pos.z());

            buffer.writeShort((short) 0xffff);
        });

        chunks.forEach(c ->
        {
            byte[] chunkData = c.chunk.bytes();

            buffer.writeVarInt(chunkData.length);
            buffer.write(chunkData);
        });
    }
}
