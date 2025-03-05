package git.skyblock.world;

import git.skyblock.blocks.Block;
import git.skyblock.minecraft.Difficulty;
import git.skyblock.minecraft.Dimension;
import git.skyblock.position.Vec2;
import git.skyblock.position.BlockPos;
import git.skyblock.position.Vec3;
import git.skyblock.util.Lock;

public interface IWorldProvider
{
    Block getBlock(BlockPos vec);
    Block getBlock(int x, int y, int z);
    void setBlock(BlockPos vec);
    void setBlock(int x, int y, int z);

    IChunkGenerator generator();
    void generateChunk(Vec2 vec);
    void load(String worldDir);

    ChunkInfo getChunk(int x, int z);
    ChunkInfo getChunk(Vec2 vec);
    void setChunk(Chunk chunk, int x, int z);
    void setChunk(Chunk chunk, Vec2 vec);

    Dimension dimension();
    Difficulty difficulty();
    Vec3 spawnPosition();

    Lock getLock();
}
