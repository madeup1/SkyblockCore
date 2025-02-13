package git.skyblock.protocol.s2c.login;

import git.skyblock.network.PlayerConnection;
import git.skyblock.network.buffers.ExpandingBuffer;
import git.skyblock.network.buffers.FixedBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IClientPacket;
import git.skyblock.protocol.IServerPacket;

import java.security.PublicKey;

public class SEncryptionRequest implements IServerPacket
{
    public SEncryptionRequest(String serverId, PublicKey publicKey, byte[] verifyToken)
    {
        this.serverId = serverId;
        this.publicKey = publicKey;
        this.verifyToken = verifyToken;
    }

    public String serverId;
    public PublicKey publicKey;
    public byte[] verifyToken;
    @Override
    public int id(ConnectionState state)
    {
        return 0x01;
    }

    @Override
    public void write(ExpandingBuffer buffer)
    {
        buffer.writeString(serverId);
        byte[] encodedPublic = publicKey.getEncoded();
        buffer.writeVarInt(encodedPublic.length);
        buffer.write(encodedPublic);
        buffer.writeVarInt(verifyToken.length);
        buffer.write(verifyToken);
    }
}
