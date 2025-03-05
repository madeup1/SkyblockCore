package git.skyblock.util;

import git.skyblock.util.annotations.ServerFlag;

import java.lang.reflect.Field;
import java.nio.ByteOrder;

public class Flags
{
    // socket
    @ServerFlag(value = "--socket-buffer-size-receive", type = ServerFlagType.INT)
    public static int SOCKET_BUFFER_SIZE_RECEIVE = 4096;
    @ServerFlag(value = "--socket-buffer-size-send", type = ServerFlagType.INT)
    public static int SOCKET_BUFFER_SIZE_SEND = 4096;
    @ServerFlag(value = "--socket-no-delay", type = ServerFlagType.BOOLEAN)
    public static boolean SOCKET_NO_DELAY = true;
    @ServerFlag(value = "--socket-timeout", type = ServerFlagType.INT)
    public static int SOCKET_TIMEOUT = 5000;
    @ServerFlag(value = "--socket-reuse-address", type = ServerFlagType.BOOLEAN)
    public static boolean SOCKET_REUSE_ADDRESS = true;
    @ServerFlag(value = "--big-endian", type = ServerFlagType.BOOLEAN)
    public static boolean ENDIAN_CONVERSION = ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN;
    @ServerFlag(value = "--port", type = ServerFlagType.INT)
    public static int SERVER_PORT = 25565;
    // threading
    @ServerFlag(value = "--multi-threaded", type = ServerFlagType.BOOLEAN)
    public static boolean MULTI_THREADED = false;

    //compression
    @ServerFlag(value = "--compression-level", type = ServerFlagType.INT)
    public static int COMPRESSION_LEVEL = 256;

    public static void parse(String[] args)
    {
        Field[] flagFields = Flags.class.getFields();

        for (int i = 0; i < args.length; i++)
        {
            for (Field field : flagFields)
            {
                ServerFlag flag = field.getAnnotation(ServerFlag.class);

                if (!flag.value().equals(args[i]) && i++ < args.length)
                    continue;

                i += 1;
                Object localValue;

                switch (flag.type())
                {
                    case INT:
                        localValue = Integer.parseInt(args[i]);
                        break;
                    case FLOAT:
                        localValue = Float.parseFloat(args[i]);
                        break;
                    case STRING:
                        localValue = args[i];
                        break;
                    case BOOLEAN:
                        localValue = Boolean.parseBoolean(args[i]);
                        break;
                    default:
                        System.out.println("Failed to parse Flag!\n  @ServerFlag(value = \"" + flag.value() + "\", type = " + flag.type() + "\n  arg: " + args[i-1]);
                        i -= 1;
                        continue;
                }

                try
                {
                    field.set(null, localValue);
                } catch (IllegalAccessException e)
                {
                    throw new RuntimeException(e);
                }

                System.out.println("set " + field.getName() + " to " + localValue);

                break;
            }
        }
    }

    public enum ServerFlagType
    {
        INT, FLOAT, STRING, BOOLEAN, UNKNOWN
    }
}
