package git.skyblock.world;

import git.skyblock.blocks.Block;
import git.skyblock.minecraft.Difficulty;
import git.skyblock.minecraft.Dimension;
import git.skyblock.position.Vec2;
import git.skyblock.position.BlockPos;
import git.skyblock.position.Vec3;
import git.skyblock.util.Lock;

import java.util.HashMap;

public class BasicWorld implements IWorldProvider
{
    private final IChunkGenerator generator;
    private final Lock lock = new Lock();
    private Dimension dimension = Dimension.Overworld;
    private Difficulty difficulty = Difficulty.Easy;

    public BasicWorld(IChunkGenerator generator)
    {
        this.generator = generator;
    }

    @Override
    public Block getBlock(BlockPos pos)
    {
        Vec2 chunkCoords = new Vec2(pos.x() >> 4, pos.z() >> 4);
        ChunkInfo chunk = this.getChunk(chunkCoords);

        return chunk.chunk.getBlock(pos);
    }

    @Override
    public Block getBlock(int x, int y, int z)
    {
        return null;
    }

    @Override
    public void setBlock(BlockPos pos)
    {

    }

    @Override
    public void setBlock(int x, int y, int z)
    {

    }

    @Override
    public IChunkGenerator generator()
    {
        return this.generator;
    }

    @Override
    public void generateChunk(Vec2 vec)
    {
        this.generator.generateChunk(vec);
    }

    @Override
    public void load(String worldDir)
    {
        // sigma load world
    }

    @Override
    public ChunkInfo getChunk(int x, int z)
    {
        return this.getChunk(new Vec2(x, z));
    }

    @Override
    public ChunkInfo getChunk(Vec2 vec)
    {
        return this.generator.generateChunk(vec);
    }

    @Override
    public void setChunk(Chunk chunk, int x, int z)
    {
        this.setChunk(chunk, new Vec2(x, z));
    }

    @Override
    public void setChunk(Chunk chunk, Vec2 vec)
    {
        generator.setChunk(vec, chunk);
    }

    @Override
    public Lock getLock()
    {
        return this.lock;
    }

    public Dimension dimension()
    {
        return this.dimension;
    }

    public Difficulty difficulty()
    {
        return this.difficulty;
    }

    @Override
    public Vec3 spawnPosition()
    {
        return new Vec3(0, 64, 0);
    }
}
