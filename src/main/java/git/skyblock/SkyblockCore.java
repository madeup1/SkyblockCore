package git.skyblock;

import git.skyblock.materials.MaterialRegistry;
import git.skyblock.util.PerformanceProfiler;

import java.nio.ByteBuffer;

public class SkyblockCore
{
    private static final MaterialRegistry materials = new MaterialRegistry();
    private static final PerformanceProfiler profiler = new PerformanceProfiler();

    public static void init() throws Exception
    {
        profiler().start("init");
        materials.load("items.json");
        profiler().end("init");
    }

    public static void start()
    {

    }

    public static MaterialRegistry materials()
    {
        return materials;
    }

    public static PerformanceProfiler profiler()
    {
        return profiler;
    }
}
