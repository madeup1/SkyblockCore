package git.skyblock.world;

import git.skyblock.position.Vec2;

public class ChunkInfo
{
    public Chunk chunk;
    public Vec2 pos;

    public ChunkInfo(Chunk chunk, Vec2 pos)
    {
        this.chunk = chunk;
        this.pos = pos;
    }
}
