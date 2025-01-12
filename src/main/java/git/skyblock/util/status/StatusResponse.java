package git.skyblock.util.status;

import git.skyblock.chat.ChatComponent;

public class StatusResponse
{
    private final StatusVersion statusVersion;
    private final StatusPlayers statusPlayers;
    private final ChatComponent component;

    public StatusResponse(String protocolName, int protocolVersion, int maxPlayers, int onlinePlayers, String description)
    {
        this.statusVersion = new StatusVersion(protocolName, protocolVersion);
        this.statusPlayers = new StatusPlayers(maxPlayers, onlinePlayers);
        this.component = new ChatComponent(description);
    }

    @Override
    public String toString()
    {
        return "{version:" + statusVersion.toString() + ",players:" + statusPlayers.toString() + ",description:" + component.toString() + "}";
    }

    public class StatusVersion
    {
        private final String name;
        private final int protocolVersion;
        public StatusVersion(String name, int protocolVersion)
        {
            this.name = name;
            this.protocolVersion = protocolVersion;
        }

        @Override
        public String toString()
        {
            return "{name:\"" + name + "\",protocol:" + protocolVersion + "}";
        }
    }

    public class StatusPlayers
    {
        private int maxPlayers;
        private int onlinePlayers;

        public StatusPlayers(int max, int online)
        {
            this.maxPlayers = max;
            this.onlinePlayers = online;
        }

        @Override
        public String toString()
        {
            return "{max:" + maxPlayers + ",online:" + onlinePlayers + "}";
        }
    }
}
