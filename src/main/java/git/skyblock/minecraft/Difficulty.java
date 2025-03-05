package git.skyblock.minecraft;

public enum Difficulty
{
    Peaceful(0),
    Easy(1),
    Normal(2),
    Hard(3);

    private int value;
    Difficulty(int value)
    {
        this.value = value;
    }

    public int value()
    {
        return this.value;
    }
}
