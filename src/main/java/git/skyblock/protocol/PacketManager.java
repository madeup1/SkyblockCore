package git.skyblock.protocol;

import git.skyblock.network.buffers.FixedBuffer;
import git.skyblock.protocol.c2s.handshake.CHandshake;
import git.skyblock.protocol.c2s.login.CEncryptionResponse;
import git.skyblock.protocol.c2s.login.CLoginStart;
import git.skyblock.protocol.c2s.play.*;
import git.skyblock.protocol.c2s.status.CStatusPing;
import git.skyblock.protocol.c2s.status.CStatusRequest;
import git.skyblock.protocol.s2c.play.SMapChunkBulk;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

import static git.skyblock.protocol.ConnectionState.*;

public class PacketManager
{
    private HashMap<ConnectionState, HashMap<Integer, Function<FixedBuffer, IClientPacket>>> packetProcessers = new HashMap<>();
    public PacketManager()
    {
        for (ConnectionState state : ConnectionState.values())
        {
            packetProcessers.put(state, new HashMap<>());
        }

        // handshake
        put(Handshake, 0, CHandshake::new);
        // status
        put(Status, 0, CStatusRequest::new);
        put(Status, 1, CStatusPing::new);
        // login
        put(Login, 0, CLoginStart::new);
        put(Login, 1, CEncryptionResponse::new);
        // play
        put(Play, 0x00, CKeepAlive::new);
        put(Play, 0x01, CChatMessage::new);
        put(Play, 0x03, CPacketPlayer::new);
        put(Play, 0x04, CPlayerPosition::new);
        put(Play, 0x05, CPlayerLook::new);
        put(Play, 0x06, CPlayerPositionLook::new);
        put(Play, 0x13, CPlayerAbilities::new);
        put(Play, 0x15, CClientSettings::new);
        put(Play, 0x16, CClientStatus::new);
        put(Play, 0x17, CPluginMessage::new);
    }

    public void put(ConnectionState state, int id, Function<FixedBuffer, IClientPacket> consumer)
    {
        packetProcessers.get(state).put(id, consumer);
    }

    public Function<FixedBuffer, IClientPacket> find(ConnectionState state, int id)
    {
        if (this.packetProcessers.get(state).containsKey(id))
            return this.packetProcessers.get(state).get(id);
        return null;
    }
}
