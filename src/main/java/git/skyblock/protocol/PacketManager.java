package git.skyblock.protocol;

import git.skyblock.network.buffers.FixedBuffer;
import git.skyblock.protocol.c2s.handshake.CHandshake;
import git.skyblock.protocol.c2s.login.CLoginStart;
import git.skyblock.protocol.c2s.status.CStatusRequest;

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
        // login
        put(Login, 0, CLoginStart::new);
        // play
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
