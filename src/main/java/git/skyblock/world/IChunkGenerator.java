package git.skyblock.world;

import git.skyblock.position.Vec2;

public interface IChunkGenerator
{
    Chunk generateChunk(Vec2 vec);
}
