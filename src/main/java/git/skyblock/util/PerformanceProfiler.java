package git.skyblock.util;

import java.util.HashMap;

public class PerformanceProfiler
{
    private HashMap<String, Long> profiles = new HashMap<>();
    public PerformanceProfiler()
    {

    }

    public void clear()
    {

    }

    public void start(String section)
    {
        profiles.put(section, System.currentTimeMillis());
    }

    public void end(String section)
    {
        if (profiles.containsKey(section))
        {
            profiles.compute(section, (k, last) -> last == null ? 0 : System.currentTimeMillis() - last);
        }
    }

    public void log()
    {
        System.out.println("--- PROFILER START ---");
        profiles.forEach((c, k) -> System.out.println(c + ": " + k + "ms"));
        System.out.println("---  PROFILER END  ---");
    }
}
