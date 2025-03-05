package git.skyblock.protocol.s2c.play;

import git.skyblock.network.buffers.ExpandingBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IServerPacket;
import git.skyblock.util.ZlibUtils;
import git.skyblock.world.ChunkInfo;

import java.util.Arrays;

public class SChunkData implements IServerPacket
{
    public ChunkInfo chunk;
    public boolean unload;
    public SChunkData(ChunkInfo chunk, boolean unload)
    {
        this.chunk = chunk;
        this.unload = unload;
    }

    public SChunkData(ChunkInfo chunk)
    {
        this(chunk, false);
    }

    @Override
    public int id(ConnectionState state)
    {
        return 0x21;
    }

    @Override
    public void write(ExpandingBuffer buffer)
    {
        buffer.writeInt(chunk.pos.x());
        buffer.writeInt(chunk.pos.z());
        buffer.writeBoolean(true);

        if (unload)
        {
            buffer.writeShort((short) 0);
            buffer.writeVarInt(0);
        }
        else
        {
            // buffer.writeShort((short) 0xffff);
            buffer.writeShort((short) 0);

            byte[] chunkData = chunk.chunk.bytes();

            int chunkLength = chunkData.length;
            // System.out.println("Chunk length is " + chunkLength);

            //buffer.writeVarInt(chunkLength);
            buffer.writeVarInt(chunkLength);
            buffer.write(chunkData);
            //buffer.write(arr_4096);
            //buffer.write(arr_4096);
            //buffer.write(biome_mess);

            // TODO: MAKE ACTUAL LIGHT SYSTME

        }
    }

    private static final byte[] arr_4096 = new byte[4096];
    private static final byte[] biome_mess = new byte[256];
    static
    {
        Arrays.fill(arr_4096, (byte) 5);
        Arrays.fill(biome_mess, (byte) 1);
    }
}
