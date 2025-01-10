package git.skyblock;

import git.skyblock.materials.MaterialRegistry;
import git.skyblock.network.ConnectionManager;
import git.skyblock.network.SocketListener;
import git.skyblock.util.Logger;
import git.skyblock.util.PerformanceProfiler;

import java.net.SocketException;

public class SkyblockCore
{
    private static final MaterialRegistry materials = new MaterialRegistry();
    private static final PerformanceProfiler profiler = new PerformanceProfiler();
    private static final ConnectionManager connections = new ConnectionManager();
    private static final SocketListener listener = new SocketListener();
    private static final Logger logger = new Logger("main");

    private static boolean running = false;
    private static long lastTick = -1L;

    public static void init() throws Exception
    {
        profiler().start("init");
        materials.load("items.json");
        profiler().end("init");
    }

    public static void start() throws SocketException
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

            if (profiler.time("frame") >= 40)
            {
                logger().warn("section [FRAME] took " + profiler.time("frame"));
            }
        }
    }

    public static void tick()
    {
        logger().info("Tick!");
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

    public static Logger logger()
    {
        return logger;
    }
}
