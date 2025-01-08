package git.skyblock.registry;

import java.util.HashMap;

public abstract class Registry<T>
{
    public final HashMap<String, T> registry = new HashMap<>();

    private final String holderName;
    public Registry(String holderName)
    {
        this.holderName = holderName;
    }

    public String name()
    {
        return this.holderName;
    }

    public void put(String namespace, T value)
    {
        this.registry.put(namespace, value);
    }

    public abstract T find(String namespace);
    public abstract void load(String filePath) throws Exception;
}
