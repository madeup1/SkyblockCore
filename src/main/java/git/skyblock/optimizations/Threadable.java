package git.skyblock.optimizations;

import git.skyblock.util.Flags;

public class Threadable
{
    private final Runnable runnable;
    public Threadable(Runnable runnable)
    {
        this.runnable = runnable;
    }

    public void start()
    {
        if (Flags.MULTI_THREADED)
        {
            new Thread(runnable).start();
        }
        else
        {
            Thread.startVirtualThread(runnable);
        }
    }
}
