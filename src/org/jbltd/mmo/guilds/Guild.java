package org.jbltd.mmo.guilds;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Guild {

    public static List<Guild> allGuilds = new ArrayList<>();

    private String guildName, tag;
    private String[] guildDesc;
    private Player leader;
    private List<UUID> members;

    public Guild(String guildName, String[] guildDesc, String tag, Player leader, List<UUID> members) {

	this.guildName = guildName;
	this.guildDesc = guildDesc;
	this.tag = tag;
	this.leader = leader;
	this.members = members;

    }

    public String getGuildName() {
	return guildName;
    }

    public String[] getGuildDescription() {
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

}
