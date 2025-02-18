package git.skyblock;

import git.skyblock.crypt.EncryptionManager;
import git.skyblock.events.EventBus;
import git.skyblock.events.impl.PlayerLoginEvent;
import git.skyblock.materials.MaterialRegistry;
import git.skyblock.network.ConnectionManager;
import git.skyblock.network.PlayerConnection;
import git.skyblock.network.SocketListener;
import git.skyblock.protocol.PacketManager;
import git.skyblock.protocol.s2c.login.SDisconnectPacket;
import git.skyblock.util.Logger;
import git.skyblock.util.PerformanceProfiler;

import java.net.SocketException;

public class SkyblockCore
{
    private static final MaterialRegistry materials = new MaterialRegistry();
    private static final PerformanceProfiler profiler = new PerformanceProfiler();
    private static final ConnectionManager connections = new ConnectionManager();
    private static final SocketListener listener = new SocketListener();
    private static final PacketManager packets = new PacketManager();
    private static final EncryptionManager encryption = new EncryptionManager();
    private static final EventBus events  = new EventBus("main");
    private static final Logger logger = new Logger("main");

    private static boolean running = false;
    private static long lastTick = -1L;

    public static void init() throws Exception
    {
        profiler.start("init");
        materials.load("items.json");
        profiler.end("init");

        events().register(PlayerLoginEvent.class, c -> {
            c.connection().sendPacket(new SDisconnectPacket("ur bad this is eventbus gaming"));
            c.connection().disconnect();
        });
    }

    public static void start() throws Exception
    {
        profiler.clear();
        profiler.start("listener");

        listener.start();

        profiler.end("listener");
        profiler.log();

        running = true;

        while (running)
        {
            profiler.clear();
            profiler.start("frame");
            profiler.end("frame");

            if (System.currentTimeMillis() - lastTick >= 50)
            {
                lastTick = System.currentTimeMillis();
                profiler.start("tick");

                tick();

                profiler.end("tick");

                if (profiler.time("tick") >= 50)
                {
                    logger().warn("section [TICK] took " + profiler.time("tick") + "\nTPS will likely be under 20!");
                }
            }

            listener.poll();
            connections().forEach(PlayerConnection::poll);

            if (profiler.time("frame") >= 40)
            {
                logger().warn("section [FRAME] took " + profiler.time("frame"));
            }
        }
    }

    public static void tick()
    {
        // logger().info("Tick!");

        // logger().info("pass");
    }

    public static void stop()
    {
        running = false;
    }

    public static MaterialRegistry materials()
    {
        return materials;
    }

    public static PerformanceProfiler profiler()
    {
        return profiler;
    }

    public static ConnectionManager connections()
    {
        return connections;
    }

    public static PacketManager packets()
    {
        return packets;
    }

    public static EncryptionManager encryption()
    {
        return encryption;
    }

    public static EventBus events()
    {
        return events;
    }

    public static Logger logger()
    {
        return logger;
    }
}
