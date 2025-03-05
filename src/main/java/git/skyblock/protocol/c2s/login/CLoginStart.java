package git.skyblock.protocol.c2s.login;

import git.skyblock.SkyblockCore;
import git.skyblock.events.impl.PlayerLoginEvent;
import git.skyblock.network.PlayerConnection;
import git.skyblock.network.buffers.FixedBuffer;
import git.skyblock.optimizations.Threadable;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IClientPacket;
import git.skyblock.protocol.s2c.login.SDisconnectPacket;
import git.skyblock.protocol.s2c.login.SEncryptionRequest;
import git.skyblock.protocol.s2c.login.SLoginSuccess;
import git.skyblock.protocol.s2c.play.SJoinGame;
import git.skyblock.protocol.s2c.play.SSetCompression;
import git.skyblock.protocol.s2c.play.SSpawnPosition;
import git.skyblock.util.Flags;

public class CLoginStart implements IClientPacket
{
    public String name;
    public CLoginStart(FixedBuffer buffer)
    {
        this.read(buffer);
    }

    @Override
    public void read(FixedBuffer buffer)
    {
        this.name = buffer.readString();
    }

    @Override
    public void process(PlayerConnection connection)
    {
        // connection.sendPacket(new SDisconnectPacket("IGN - " + this.name));
        // connection.disconnect();
        connection.setName(this.name);

        new Threadable(() -> {
            // TODO: add encryption
            // connection.sendPacket(new SEncryptionRequest("", SkyblockCore.encryption().keyPair().getPublic(), SkyblockCore.encryption().getRandomToken()));
            connection.authenticate();

            if (!connection.authenticated())
            {
                connection.sendPacket(new SDisconnectPacket("Not authenticated!"));
                connection.disconnect();

                return;
            }

            if (this.name.isEmpty())
            {
                connection.sendPacket(new SDisconnectPacket("Username is invalid!"));
                connection.disconnect();

                return;
            }

            connection.sendPacket(new SLoginSuccess(connection.uuid(), connection.name()));
            connection.setState(ConnectionState.Play);

            connection.sendPacket(new SSetCompression(Flags.COMPRESSION_LEVEL));
            connection.setCompression(Flags.COMPRESSION_LEVEL);

            connection.sendPacket(new SJoinGame(connection));
            connection.sendPacket(new SSpawnPosition(connection.player().world().spawnPosition()));

            SkyblockCore.events().post(new PlayerLoginEvent(connection));

            connection.player().init();
        }).start();
    }
}
