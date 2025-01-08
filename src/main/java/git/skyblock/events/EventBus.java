package git.skyblock.events;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.function.Consumer;

public class EventBus
{
    private final HashMap<Class<? extends IEvent>, LinkedList<Hook>> events = new HashMap<>();
    private final String owner;
    public EventBus(String owner)
    {
        this.owner = owner;
    }

    public String owner()
    {
        return this.owner;
    }

    public <T extends IEvent> void register(Class<T> clasz, Consumer<T> event)
    {
        if (!events.containsKey(clasz))
        {
            events.put(clasz, new LinkedList<>());
        }

        events.get(clasz).addLast(new Hook<>(event));
    }

    public boolean post(IEvent event)
    {
        if (!events.containsKey(event.getClass()))
            return false;

        events.get(event.getClass()).forEach(hook -> hook.call(event));

        if (event instanceof CancellableEvent cEvent)
            return cEvent.cancelled();

        return false;
    }

    private record Hook<T>(Consumer<T> consumer)
    {
        public void call(T event)
        {
            consumer.accept(event);
        }
    }
}
