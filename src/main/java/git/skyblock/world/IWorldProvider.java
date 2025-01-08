package git.skyblock.world;

import git.skyblock.blocks.Block;
import git.skyblock.position.Vec2;
import git.skyblock.position.Vec3;

public interface IWorldProvider
{
    Block getBlock(Vec3 vec);
    Block getBlock(int x, int y, int z);
    void setBlock(Vec3 vec);
    void setBlock(int x, int y, int z);

    IChunkGenerator generator();
    void generateChunk(Vec2 vec);
    void load(String worldDir);

    Chunk getChunk(int x, int z);
    Chunk getChunk(Vec2 vec);
    void setChunk(Chunk chunk, int x, int z);
    void setChunk(Chunk chunk, Vec2 vec);
}
