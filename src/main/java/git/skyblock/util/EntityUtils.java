package git.skyblock.util;

public class EntityUtils
{
    private static int counter = -1;
    public static int getEntityId()
    {
        counter += 1;

        return counter;
    }
}
