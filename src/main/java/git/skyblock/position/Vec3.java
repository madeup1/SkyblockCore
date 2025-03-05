package git.skyblock.position;

public record Vec3(double x, double y, double z)
{
    @Override
    public String toString()
    {
        return "{x:" + x + ",y:" + y + ",z:" + z + "}";
    }
}