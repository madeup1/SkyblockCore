package git.skyblock.world;

import git.skyblock.position.Vec2;

import java.util.HashMap;

public interface IChunkGenerator
{
    ChunkInfo generateChunk(Vec2 vec);
    void setChunk(Vec2 vec, Chunk chunk);
    ChunkInfo getChunk(Vec2 vec);
    HashMap<Vec2, Chunk> getChunkMap();
}
