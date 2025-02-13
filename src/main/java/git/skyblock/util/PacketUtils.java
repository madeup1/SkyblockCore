package git.skyblock.util;

import java.io.IOException;
import java.io.InputStream;

public class PacketUtils
{
    public static int getVarIntLength(int value)
    {
        int len = 1;
        while ((value & -128) != 0)
        {
            value >>= 7;
            len++;
        }

        return len;
    }

    public static int readVarInt(InputStream stream) throws IOException
    {
        int value = 0;
        int pos = 0;
        byte[] buf = new byte[1];

        while (true)
        {
            stream.read(buf);
            byte b = buf[0];
            value |= (b & 127) << pos++ * 7;

            if (pos > 5)
                throw new RuntimeException("Varint bigash");

            if ((b & 128) != 128)
                break;
        }

        return value;
    }
}
