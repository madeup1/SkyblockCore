package git.skyblock.util;

import git.skyblock.entities.EntityPlayer;
import git.skyblock.position.Vec2;
import git.skyblock.protocol.s2c.play.SChunkData;
import git.skyblock.protocol.s2c.play.SMapChunkBulk;
import git.skyblock.world.ChunkInfo;
import git.skyblock.world.IWorldProvider;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ChunkUtils
{
    public static void unloadChunk(ChunkInfo info, EntityPlayer player)
    {
        player.connection().sendPacket(new SChunkData(info, true));
    }

    public static void sectionChunksForPacket(List<ChunkInfo> chunks, EntityPlayer player)
    {
        int MAGIC_CHUNK_NUMBER = 10;

        LinkedList<ChunkInfo> sendChunks = new LinkedList<>();
        for (int i = 0; i < chunks.size(); i++)
        {
            sendChunks.addLast(chunks.get(i));

            if (i % MAGIC_CHUNK_NUMBER == 0)
            {
                player.connection().sendPacket(new SMapChunkBulk(sendChunks));
                sendChunks.clear();
            }
        }

        if (!sendChunks.isEmpty())
        {
            player.connection().sendPacket(new SMapChunkBulk(sendChunks));
        }
    }

    public static HashMap<Vec2, ChunkInfo> getChunksInRender(EntityPlayer player)
    {
        IWorldProvider world = player.world();
        int viewDistance = player.viewDistance;

        int half = viewDistance / 2;
        Vec2 playerChunkPos = player.getChunkPosition();

        HashMap<Vec2, ChunkInfo> chunks = new HashMap<Vec2, ChunkInfo>();

        for (int x = -half; x <= half; x++)
        {
            for (int z = -half; z <= half; z++)
            {
                ChunkInfo info = world.getChunk(new Vec2(playerChunkPos.x() + x, playerChunkPos.z() + z));

                chunks.put(info.pos, info);
            }
        }

        return chunks;
    }
}
