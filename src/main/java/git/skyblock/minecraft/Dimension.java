package git.skyblock.minecraft;

public enum Dimension
{
    Nether(-1),
    Overworld(0),
    End(1);

    private int dimension;
    Dimension(int dimension)
    {
        this.dimension = dimension;
    }

    public int dimension()
    {
        return this.dimension;
    }
}
