package git.skyblock.entities;

import git.skyblock.SkyblockCore;
import git.skyblock.minecraft.Gamemode;
import git.skyblock.network.PlayerConnection;
import git.skyblock.optimizations.Threadable;
import git.skyblock.position.Vec2;
import git.skyblock.position.Vec3;
import git.skyblock.protocol.s2c.play.SChunkData;
import git.skyblock.protocol.s2c.play.SMapChunkBulk;
import git.skyblock.protocol.s2c.play.SPlayerPosLook;
import git.skyblock.util.ChunkUtils;
import git.skyblock.world.Chunk;
import git.skyblock.world.ChunkInfo;
import git.skyblock.world.IWorldProvider;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class EntityPlayer extends Entity
{
    private String name;
    private PlayerConnection connection;
    private Gamemode gamemode;
    private IWorldProvider world;

    private boolean onGround;
    public int viewDistance = 8;
    public HashMap<Vec2, ChunkInfo> loadedChunks = new HashMap<Vec2, ChunkInfo>();

    public EntityPlayer(PlayerConnection connection)
    {
        super();

        this.name = connection.name();
        this.connection = connection;
        this.gamemode = Gamemode.Creative;
        this.world = SkyblockCore.worlds().defaultWorld();

        this.setPosition(this.world.spawnPosition());
    }

    private boolean loaded = false;
    public void init()
    {
        if (!loaded)
        {
            // yada
        }

        this.updateChunks();

        this.connection.sendPacket(new SPlayerPosLook(this.position(), this.yaw(), this.pitch()));



        loaded = true;
    }

    public void updateChunks()
    {
        HashMap<Vec2, ChunkInfo> chunks = ChunkUtils.getChunksInRender(this);
        loadedChunks.values()
                .stream()
                .filter(c -> !chunks.containsKey(c.pos))
                .forEach(c -> {
                    ChunkUtils.unloadChunk(c, this);
                });

        List<ChunkInfo> chunkInfoList = chunks.values().stream().toList();

        Thread.startVirtualThread(() -> {
            chunkInfoList.forEach(c -> {
                if (!loadedChunks.containsKey(c.pos))
                    connection.sendPacket(new SChunkData(c));
            });
        });

        loadedChunks = chunks;
    }

    public void tick()
    {
        if (SkyblockCore.ticks() % 5 == 0)
        {
            this.updateChunks();
        }
    }

    public boolean onGround()
    {
        return this.onGround;
    }

    public void setOnGround(boolean value)
    {
        this.onGround = value;
    }

    public Gamemode gamemode()
    {
        return this.gamemode;
    }

    public PlayerConnection connection()
    {
        return this.connection;
    }

    public IWorldProvider world()
    {
        return this.world;
    }
}
