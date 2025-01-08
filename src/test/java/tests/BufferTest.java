package tests;

import git.skyblock.network.ExpandingBuffer;
import git.skyblock.network.FixedBuffer;

import java.util.Arrays;
import java.util.UUID;

public class BufferTest
{
    public static void run()
    {
        ExpandingBuffer buffer = new ExpandingBuffer();

        UUID random = UUID.randomUUID();

        buffer.writeInt(11);
        buffer.writeLong(Long.MAX_VALUE);
        buffer.writeByte((byte) 111);
        buffer.writeDouble(Double.MAX_VALUE);
        buffer.writeFloat(Float.MAX_VALUE);
        buffer.writeVarInt(127);
        buffer.writeVarLong(255);
        buffer.writeString("Hello World!");
        buffer.writeUuid(random);

        byte[] data = buffer.compile();

        System.out.println(Arrays.toString(data));

        FixedBuffer fixedBuffer = new FixedBuffer(data);

        System.out.println(fixedBuffer.readInt() + " == " + 11);
        System.out.println(fixedBuffer.readLong() + " == " + Long.MAX_VALUE);
        System.out.println(fixedBuffer.readByte() + " == " + 111);
        System.out.println(fixedBuffer.readDouble() + " == " + Double.MAX_VALUE);
        System.out.println(fixedBuffer.readFloat() + " == " + Float.MAX_VALUE);
        System.out.println(fixedBuffer.readVarInt() + " == " + 127);
        System.out.println(fixedBuffer.readVarLong() + " == " + 255);
        System.out.println(fixedBuffer.readString() + " == 'Hello World!'");
        System.out.println(fixedBuffer.readUuid() + " == " + random);
    }
}
