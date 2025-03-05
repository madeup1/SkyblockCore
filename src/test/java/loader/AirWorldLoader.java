package loader;

import git.skyblock.SkyblockCore;
import git.skyblock.blocks.Block;
import git.skyblock.position.BlockPos;
import git.skyblock.position.Vec2;
import git.skyblock.world.Chunk;
import git.skyblock.world.ChunkInfo;
import git.skyblock.world.IChunkGenerator;

import java.util.HashMap;

public class AirWorldLoader implements IChunkGenerator
{
    private HashMap<Vec2, ChunkInfo> worldData = new HashMap<>();
    private static final Chunk airChunk = new Chunk();

    static
    {
        Block block = new Block(7, 0);

        for (int x = 0; x < 16; x++)
        {
            for (int z = 0; z < 16; z++)
            {
                for (int y = 0; y < 5; y++)
                {

                    airChunk.setBlock(block, new BlockPos(x, y, z));
                }
            }
        }
    }

    @Override
    public ChunkInfo generateChunk(Vec2 vec)
    {
        if (worldData.containsKey(vec))
            return worldData.get(vec);

        ChunkInfo info = new ChunkInfo(airChunk, vec);
        worldData.put(vec, info);

        SkyblockCore.logger().info("Generating chunk!");

        // info.chunk.setBlock(SkyblockCore.materials().find(1).toBlock(), 10, 10, 10);

        return info;
    }

    @Override
    public void setChunk(Vec2 vec, Chunk chunk)
    {
        worldData.put(vec, new ChunkInfo(chunk, vec));
    }

    @Override
    public ChunkInfo getChunk(Vec2 vec)
    {
        return this.generateChunk(vec);
    }

    @Override
    public HashMap<Vec2, Chunk> getChunkMap()
    {
        return null;
    }
}
