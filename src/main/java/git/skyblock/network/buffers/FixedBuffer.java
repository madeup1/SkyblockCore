package git.skyblock.network.buffers;

import git.skyblock.util.Flags;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

public class FixedBuffer
{
    private byte[] data;
    private int index = 0;
    public FixedBuffer(byte[] data)
    {
        this.data = data;
    }

    public void setData(byte[] data)
    {
        this.data = data;
        this.index = 0;
    }

    public byte[] remainder()
    {
        return read(this.data.length - index);
    }

    public int readInt()
    {
        byte[] data = this.read(4);

        if (data.length == 0)
            return 0;

        int value = 0;

        for (int i = 0; i < 4; i++)
        {
            value |= (data[i] & 0xFF) << (24-i*8);
        }

        if (Flags.ENDIAN_CONVERSION)
            value = Integer.reverseBytes(value);

        return value;
    }

    public long readLong()
    {
        byte[] data = this.read(8);

        if (data.length == 0)
            return 0;

        long value = 0;

        for (int i = 0; i < 8; i++)
            value |= (long) (data[i] & 0xFF) << (56 - i * 8);

        if (Flags.ENDIAN_CONVERSION)
            value = Long.reverseBytes(value);

        return value;
    }

    public UUID readUuid()
    {
        long l1 = this.readLong();
        long l2 = this.readLong();

        return new UUID(l2, l1);
    }

    public String readString()
    {
        int length = this.readVarInt();

        return new String(this.read(length), StandardCharsets.UTF_8);
    }

    public float readFloat()
    {
        return Float.intBitsToFloat(this.readInt());
    }

    public double readDouble()
    {
        return Double.longBitsToDouble(this.readLong());
    }

    public short readShort()
    {
        byte[] data = this.read(2);

        if (data.length == 0)
            return 0;

        short value = 0;

        for (int i = 0; i < 2; i++)
            value |= (short) ((data[i] & 0xFF) << (8 - i * 8));

        if (Flags.ENDIAN_CONVERSION)
            value = Short.reverseBytes(value);

        return value;
    }

    public int readVarInt()
    {
        int value = 0;
        int pos = 0;

        while (true)
        {
            byte b = this.readByte();
            value |= (b & 127) << pos++ * 7;

            if (pos > 5)
                throw new RuntimeException("Varint bigash");

            if ((b & 128) != 128)
                break;
        }

        return value;
    }

    public long readVarLong()
    {
        long value = 0;
        int pos = 0;

        while (true)
        {
            byte b = this.readByte();
            value |= (b & 127) << pos++ * 7;

            if (pos > 10)
                throw new RuntimeException("Varlong bigash");

            if ((b & 128) != 128)
                break;
        }

        return value;
    }

    public boolean readBoolean()
    {
        return this.readByte() == 0x01;
    }

    public byte readByte()
    {
        return this.read(1)[0];
    }

    public byte[] read(int length)
    {
        if (!this.ensure(length))
            return new byte[0];

        byte[] data = new byte[length];
        System.arraycopy(this.data, index, data, 0, length);

        this.index += length;

        return data;
    }

    public boolean ensure(int length)
    {
        return index + length <= data.length;
    }

    public int length()
    {
        return this.data.length;
    }

    public byte[] getBytes()
    {
        return this.data;
    }

    public void print()
    {
        System.out.println(Arrays.toString(this.data));
    }
}
