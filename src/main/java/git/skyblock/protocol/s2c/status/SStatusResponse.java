package git.skyblock.protocol.s2c.status;

import git.skyblock.network.buffers.ExpandingBuffer;
import git.skyblock.protocol.ConnectionState;
import git.skyblock.protocol.IServerPacket;
import git.skyblock.util.status.StatusResponse;

public class SStatusResponse implements IServerPacket
{
    public StatusResponse response;
    public SStatusResponse(StatusResponse response)
    {
        this.response = response;
    }


    @Override
    public int id(ConnectionState state)
    {
        return 0x00;
    }

    @Override
    public void write(ExpandingBuffer buffer)
    {
        buffer.writeString(this.response.toString());
    }
}
