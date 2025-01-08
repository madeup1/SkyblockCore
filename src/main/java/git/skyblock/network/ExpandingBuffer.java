package git.skyblock.network;

import git.skyblock.util.Flags;

import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

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
            value >>= 7;
        }

        this.writeByte((byte) value);
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
        this.writeVarInt(bytes.length);
        this.write(bytes);
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

    public void writeByte(byte value)
    {
        this.write(new byte[]{value});
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

    public byte[] getBytes()
    {
        return this.compile();
    }

    private record BufferSegment(byte[] data, int length)
    {}
}
