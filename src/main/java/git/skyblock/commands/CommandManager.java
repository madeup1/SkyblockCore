package git.skyblock.commands;

import git.skyblock.SkyblockCore;
import git.skyblock.blocks.Block;
import git.skyblock.entities.EntityPlayer;
import git.skyblock.network.PlayerConnection;
import git.skyblock.position.BlockPos;
import git.skyblock.position.Vec3;
import git.skyblock.protocol.s2c.play.SBlockChange;
import git.skyblock.protocol.s2c.play.SChatMessage;
import git.skyblock.protocol.s2c.play.SEntityTeleport;
import git.skyblock.protocol.s2c.play.SPlayerPosLook;
import git.skyblock.world.ChunkInfo;

public class CommandManager
{
    public void command(String text, PlayerConnection connection)
    {
        switch (text)
        {
            case "/test":
                blockTest(connection);
                break;
        }
    }

    public void blockTest(PlayerConnection connection)
    {
        EntityPlayer player = connection.player();

        ChunkInfo chunk = player.world().getChunk(player.getChunkPosition());
        Block block = chunk.chunk.getBlock(new BlockPos(10, 10, 10));

        // connection.sendPacket(new SChatMessage("block id is " + block.material().namespace()));
        // connection.sendPacket(new SBlockChange(new BlockPos(10, 10, 10), block));
        // connection.sendPacket(new SEntityTeleport(new Vec3(100, 100, 100), player, 90, 90, true));
        connection.sendPacket(new SPlayerPosLook(new Vec3(100, 100, 100), 90, 90));

        chunk.chunk.setBlock(SkyblockCore.materials().find("bedrock").toBlock(), new BlockPos(10, 10, 10));
    }
}
