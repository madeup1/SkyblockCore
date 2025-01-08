import git.skyblock.SkyblockCore;
import git.skyblock.materials.MaterialEntry;
import git.skyblock.util.Flags;
import tests.BufferTest;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Flags.parse(args);

        //System.out.println("--- Buffer Test ---");
        //BufferTest.run();
        //CodeGenerator.run();
        SkyblockCore.init();
        SkyblockCore.start();

        SkyblockCore.profiler().log();

        System.out.println("Material test - SIZE: " + SkyblockCore.materials().size());
        MaterialEntry entry = SkyblockCore.materials().find("wooden_sword");

        System.out.println("Material is " + entry.namespace());

        int x = 31 << 16 | 11;

        System.out.println(Integer.toBinaryString(x));

        int tId = x >> 16;
        int tMeta = x - (tId << 16);

        System.out.println("id is " + tId + " meta is " + tMeta);
    }
}
