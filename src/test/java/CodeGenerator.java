import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;

public class CodeGenerator
{
    public static void run() throws Exception
    {
        String fileContents = String.join("\n", Files.readAllLines(Paths.get("items.json")));
        JSONArray obj = new JSONArray(fileContents);

        int index = 0;
        while (obj.get(index) != null)
        {
            // Material(int id, String displayName, String registryName, int maxStackSize, int variation)
            JSONObject ref = (JSONObject) obj.get(index);

            int id = ref.getInt("id");
            String displayName = ref.getString("displayName");
            String registryName = displayName.toUpperCase().replace(' ', '_');
            int maxStackSize = ref.getInt("stackSize");

            if (!ref.has("variations"))
            {
                System.out.println("    " + registryName + "(" + id + ", \"" + displayName + "\", \"" + registryName + "\", " + maxStackSize + ", 0),");
            }
            else
            {
                JSONArray newArr = ref.getJSONArray("variations");

                int nIndex = 0;
                while (nIndex < newArr.length())
                {
                    JSONObject refObj = newArr.getJSONObject(nIndex);

                    String ndisplayName = refObj.getString("displayName");
                    String nregistryName = ndisplayName.toUpperCase().replace(' ', '_');
                    int variation = refObj.getInt("metadata");

                    System.out.println("    " + nregistryName + "(" + id + ", \"" + ndisplayName + "\", \"" + nregistryName + "\", " + maxStackSize + ", " + variation + "),");

                    nIndex++;
                }
            }

            index++;
        }
    }
}
