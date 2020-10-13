package org.jbltd.mmo.guilds;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Guild {

    public static List<Guild> allGuilds = new ArrayList<>();

    public static List<UUID> phase1 = new ArrayList<UUID>(), phase2 = new ArrayList<UUID>(), phase3 = new ArrayList<UUID>();

    private final String guildName;
    private final String tag;
    private final String guildDesc;
    private final Player leader;
    private final List<UUID> members;

    public Guild(String guildName, String guildDesc, String tag, Player leader, List<UUID> members) {

        this.guildName = guildName;
        this.guildDesc = guildDesc;
        this.tag = tag;
        this.leader = leader;
        this.members = members;

    }

    public String getGuildName() {
        return guildName;
    }

    public String getGuildDescription() {
        return guildDesc;
    }

    public String getGuildTag() {
        return tag;

    }

    public Player getGuildLeader() {
        return leader;
    }

    public List<UUID> getGuildMembers() {
        return members;

    }

    public void addMember(Player player) {
        getGuildMembers().add(player.getUniqueId());
    }

}
