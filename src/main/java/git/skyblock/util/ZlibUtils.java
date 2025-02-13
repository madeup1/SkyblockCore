package git.skyblock.util;

import git.skyblock.network.buffers.ExpandingBuffer;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ZlibUtils
{
    public static byte[] compress(byte[] data)
    {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[512];
        while (!deflater.finished())
        {
            int count = deflater.deflate(buffer);
            out.write(buffer, 0, count);
        }
        try
        {
            out.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static byte[] decompress(byte[] input)
    {
        try
        {
            Inflater inflater = new Inflater();
            inflater.setInput(input);

            byte[] buffer = new byte[512];
            int decompressedDataLength = 0;
            ExpandingBuffer out = new ExpandingBuffer();

            while (!inflater.finished())
            {
                int count = inflater.inflate(buffer);
                if (count == 0)
                {
                    break;
                }

                byte[] trueData = new byte[count];
                System.arraycopy(buffer, 0, trueData, 0, count);

                out.write(trueData);
            }

            return out.compile();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
