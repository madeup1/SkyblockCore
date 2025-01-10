package git.skyblock.util;

import java.util.LinkedList;

public class Lock
{
    private final LinkedList<Runnable> runnables;
    private boolean locked = false;
    public Lock()
    {
        this.runnables = new LinkedList<>();
    }

    public boolean locked()
    {
        return this.locked;
    }

    public void lock()
    {
        this.locked = true;
    }

    public void unlock()
    {
        this.runnables.forEach(Runnable::run);
        this.runnables.clear();

        this.locked = false;
    }

    public void schedule(Runnable runnable)
    {
        if (this.locked)
        {
            this.runnables.addLast(runnable);
        }
        else
        {
            runnable.run();
        }
    }
}
