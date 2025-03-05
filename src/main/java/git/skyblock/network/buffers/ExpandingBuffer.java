package git.skyblock.network.buffers;

import git.skyblock.SkyblockCore;
import git.skyblock.position.BlockPos;
import git.skyblock.position.Vec3;
import git.skyblock.util.Flags;
import git.skyblock.util.PacketUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;

public class ExpandingBuffer
{
    private LinkedList<BufferSegment> links;
    private int length;
    public ExpandingBuffer()
    {
        this.links = new LinkedList<>();
    }

    public void writeLong(long value)
    {
        if (Flags.ENDIAN_CONVERSION)
        {
            value = Long.reverseBytes(value);
        }

        byte[] data = new byte[8];
        for (int i = 0; i < 8; i++)
        {
            data[i] = (byte) (value >> (56 - (i * 8)));
        }

        this.write(data);
    }

    public void writeInt(int value)
    {
        if (Flags.ENDIAN_CONVERSION)
        {
            value = Integer.reverseBytes(value);
        }

        byte[] data = new byte[4];
        for (int i = 0; i < 4; i++)
        {
            data[i] = (byte) (value >> (24 - (i * 8)));
        }

        this.write(data);
    }

    public void writeIntBefore(int value)
    {
        if (Flags.ENDIAN_CONVERSION)
        {
            value = Integer.reverseBytes(value);
        }

        byte[] data = new byte[4];
        for (int i = 0; i < 4; i++)
        {
            data[i] = (byte) (value >> (24 - (i * 8)));
        }

        this.writeBefore(data);
    }

    public void writeFloat(float fValue)
    {
        int value = Float.floatToIntBits(fValue);

        this.writeInt(value);
    }

    public void writeDouble(double dValue)
    {
        long value = Double.doubleToLongBits(dValue);

        this.writeLong(value);
    }

    public void writeVarInt(int value)
    {
        while ((value & -128) != 0)
        {
            this.writeByte((byte) (value & 127 | 128));
            value >>>= 7;
        }

        this.writeByte((byte) value);
    }

    public void writeVarIntBefore(int value)
    {
        while ((value & -128) != 0)
        {
            this.writeByteBefore((byte) (value & 127 | 128));
            value >>>= 7;
        }
    }

    public void writeVarLong(long value)
    {
        while ((value & -128) != 0)
        {
            this.writeByte((byte) (value & 127 | 128));
            value >>= 7;
        }

        this.writeByte((byte) value);
    }

    public void writeString(String value)
    {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        this.writeVarInt(value.length());
        this.write(bytes);
    }

    public void writeBoolean(boolean value)
    {
        this.writeByte((byte) (value ? 0x01 : 0x00));
    }

    public void writeShort(short value)
    {
        if (Flags.ENDIAN_CONVERSION)
        {
            value = Short.reverseBytes(value);
        }

        byte[] data = new byte[2];
        for (int i = 0; i < 2; i++)
        {
            data[i] = (byte) (value >> (8 - (i * 8)));
        }

        this.write(data);
    }

    public void writeUuid(UUID uuid)
    {
        this.writeLong(uuid.getLeastSignificantBits());
        this.writeLong(uuid.getMostSignificantBits());
    }

    public void write(byte[] data)
    {
        BufferSegment segment = new BufferSegment(data, data.length);

        this.length += data.length;

        this.links.addLast(segment);
    }

    public void writeVec3(Vec3 vec)
    {
        this.writeDouble(vec.x());
        this.writeDouble(vec.y());
        this.writeDouble(vec.z());
    }

    public void writeBlockPos(BlockPos pos)
    {
        this.writeLong(((long) (pos.x() & 0x3FFFFFF) << 38) | ((long) (pos.y() & 0xFFF) << 26) | (pos.z() & 0x3FFFFFF));
    }

    public void writeSegment(BufferSegment segment)
    {
        this.length += segment.length();

        this.links.addLast(segment);
    }

    public void writeBefore(byte[] data)
    {
        BufferSegment segment = new BufferSegment(data, data.length);

        this.length += data.length;

        this.links.addFirst(segment);
    }

    public void writeByte(byte value)
    {
        this.write(new byte[]{value});
    }

    public void writeByteArray(byte[] value)
    {
        this.writeVarInt(value.length);
        SkyblockCore.logger().info("write length is " + value.length);
        this.write(value);
    }

    public void writeByteBefore(byte value)
    {
        this.writeBefore(new byte[]{value});
    }

    public byte[] compile()
    {
        byte[] data = new byte[this.length];

        Iterator<BufferSegment> iter = links.iterator();
        int index = 0;
        while (iter.hasNext())
        {
            BufferSegment segment = iter.next();
            System.arraycopy(segment.data, 0, data, index, segment.length);
            index += segment.length;
        }

        return data;
    }

    public void clear()
    {
        this.links.clear();
        this.length = 0;
    }

    public int length()
    {
        return this.length;
    }

    public byte[] getBytes()
    {
        return this.compile();
    }

    public record BufferSegment(byte[] data, int length)
    {}
}
