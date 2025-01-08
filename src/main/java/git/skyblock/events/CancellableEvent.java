package git.skyblock.events;

public class CancellableEvent implements IEvent
{
    private boolean cancelled = false;

    public boolean cancelled()
    {
        return cancelled;
    }

    public void setCancelled(boolean value)
    {
        this.cancelled = value;
    }
}
