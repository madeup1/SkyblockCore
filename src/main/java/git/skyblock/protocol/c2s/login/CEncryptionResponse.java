package git.skyblock.protocol.c2s.login;

import git.skyblock.SkyblockCore;
import git.skyblock.network.PlayerConnection;
import git.skyblock.network.buffers.FixedBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IClientPacket;
import git.skyblock.protocol.s2c.login.SDisconnectPacket;
import git.skyblock.protocol.s2c.play.SSetCompression;

import javax.crypto.spec.SecretKeySpec;

public class CEncryptionResponse implements IClientPacket
{
    private byte[] sharedSecret;
    private byte[] verifyToken;

    @Override
    public int id(ConnectionState state)
    {
        return 0x01;
    }

    @Override
    public void read(FixedBuffer buffer)
    {
        this.sharedSecret = buffer.read(buffer.readVarInt());
        this.verifyToken = buffer.read(buffer.readVarInt());
    }

    @Override
    public void process(PlayerConnection connection)
    {
        SkyblockCore.logger().info("Received response from client " + connection.name());
        SkyblockCore.logger().info("Length of sharedSecret is " + sharedSecret.length);
        connection.setSharedSecret(SkyblockCore.encryption().bytesToSecret(sharedSecret));
        SkyblockCore.logger().info("Length of verifyToken is " + verifyToken.length);
        connection.setState(ConnectionState.Play);

        connection.sendPacket(new SDisconnectPacket("test"));
    }

    public CEncryptionResponse(FixedBuffer buffer)
    {
        this.read(buffer);
    }
}
