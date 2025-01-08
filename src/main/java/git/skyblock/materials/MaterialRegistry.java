package git.skyblock.materials;

import git.skyblock.registry.Registry;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class MaterialRegistry extends Registry<MaterialEntry>
{
    public final HashMap<String, MaterialEntry> idRegistry = new HashMap<>();
    public MaterialRegistry()
    {
        super("materials");
    }

    @Override
    public MaterialEntry find(String namespace)
    {
        return this.registry.get(namespace);
    }

    public MaterialEntry find(int id, int metadata)
    {
        String search = "" + id;
        if (metadata != 0)
            search += ":" + metadata;
        return idRegistry.get(search);
    }

    public MaterialEntry find(int id)
    {
        return idRegistry.get(Integer.toString(id));
    }

    @Override
    public void put(String namespace, MaterialEntry entry)
    {
        this.registry.put(namespace, entry);

        if (entry.metadata() != 0)
        {
            this.idRegistry.put(entry.id() + ":" + entry.metadata(), entry);
        }
        else
        {
            this.idRegistry.put("" + entry.id(), entry);
        }
    }

    @Override
    public void load(String filePath) throws Exception
    {
        String fileContents = String.join("\n", Files.readAllLines(Paths.get("items.json")));
        JSONArray obj = new JSONArray(fileContents);

        int index = 0;
        while (index < obj.length())
        {
            // Material(int id, String displayName, String registryName, int maxStackSize, int variation)
            JSONObject ref = obj.getJSONObject(index);

            int id = ref.getInt("id");
            String displayName = ref.getString("displayName");
            String registryName = ref.getString("name");
            int maxStackSize = ref.getInt("stackSize");

            if (!ref.has("variations"))
            {
                //System.out.println("    " + registryName + "(" + id + ", \"" + displayName + "\", \"" + registryName + "\", " + maxStackSize + ", 0),");
                this.put(registryName, new MaterialEntry(id, 0, registryName, displayName, maxStackSize));
            }
            else
            {
                JSONArray newArr = ref.getJSONArray("variations");

                int nIndex = 0;
                while (nIndex < newArr.length())
                {
                    JSONObject refObj = newArr.getJSONObject(nIndex);

                    String ndisplayName = refObj.getString("displayName");
                    String nregistryName = ndisplayName.toLowerCase().replace(' ', '_');
                    int variation = refObj.getInt("metadata");

                    //System.out.println("    " + nregistryName + "(" + id + ", \"" + ndisplayName + "\", \"" + nregistryName + "\", " + maxStackSize + ", " + variation + "),");
                    this.put(nregistryName, new MaterialEntry(id, variation, nregistryName, ndisplayName, maxStackSize));
                    nIndex++;
                }
            }

            index++;
        }


    }

    public int size()
    {
        return this.idRegistry.size() + registry.size();
    }

    public boolean has(int id, int metadata)
    {
        return this.find(id, metadata) != null;
    }
}
