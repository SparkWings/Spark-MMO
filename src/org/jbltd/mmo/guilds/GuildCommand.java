package org.jbltd.mmo.guilds;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.jbltd.mmo.core.util.F;

public class GuildCommand implements CommandExecutor, Listener {

    private List<UUID> phase1, phase2, phase3 = new ArrayList<UUID>();

    private static final String PHASE1_MESSAGE = "Please type the name of the guild you wish to create.";
    private static final String PHASE2_MESSAGE = "Please type the description of the guild you wish to create (Max 1 Line).";
    private static final String PHASE3_MESSAGE = "Please enter the TAG of your guild (This will be your prefix in chat).";
    private static final String ESCAPE_MESSAGE = F.RED + "You have exited guild creation mode.";

  

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

	if (!(sender instanceof Player))
	    return true;

	Player p = (Player) sender;

	if (cmd.getName().equalsIgnoreCase("guild")) {

	    for (Guild g : Guild.allGuilds) {
		List<UUID> members = g.getGuildMembers();
		if (members.contains(p.getUniqueId())) {
		    p.sendMessage(F.error("Guilds", "You are already in a guild."));
		    return true;
		} else
		    continue;
	    }

	    phase1.add(p.getUniqueId());
	    p.sendMessage(F.info("Guilds", false,
		    "You have entered guild creation mode. Follow the instructions you are prompted in the chat to create your new guild."));

	    // PHASE 1
	    p.sendMessage(PHASE1_MESSAGE);

	    
	    
	}

	if (cmd.getName().equalsIgnoreCase("escape")) {

	    if (phase1.contains(p.getUniqueId())) {
		phase1.remove(p.getUniqueId());
		p.sendMessage(ESCAPE_MESSAGE);
		return false;

	    } else if (phase2.contains(p.getUniqueId())) {
		phase2.remove(p.getUniqueId());
		p.sendMessage(ESCAPE_MESSAGE);
		return false;
	    } else if (phase3.contains(p.getUniqueId())) {
		phase3.remove(p.getUniqueId());
		p.sendMessage(ESCAPE_MESSAGE);
		return false;
	    } else {
		p.sendMessage(F.RED + "You are not in guild creation mode.");
		return true;
	    }

	}

	return false;

    }

    @EventHandler
    public void guildCreation(AsyncPlayerChatEvent e) {

	Player player = e.getPlayer();

	String name = "";
	String description = "";
	String tag = "";

	// PHASE 1
	if (phase1.contains(player.getUniqueId())) {
	    // Begin
	    System.out.println("Beginning phase1");
	    
	    
	    e.setCancelled(true);
	    name = e.getMessage().trim().replace(" ", "");

	    for (Guild g : Guild.allGuilds) {
		if (g.getGuildName() == name) {
		    player.sendMessage(F.error("Guilds", "That name is already taken."));
		    player.sendMessage(PHASE1_MESSAGE);
		    System.err.println("guild name already exists");
		} else {
		    player.sendMessage(F.info("Guilds", false, "You have set your clan name to: " + F.GOLD + name));
		    phase1.remove(player.getUniqueId());
		    phase2.add(player.getUniqueId());
		    player.sendMessage(PHASE2_MESSAGE);
		    // BEGIN PHASE 2
		    System.out.println("BEginning phase 2");
		}
	    }

	}

	// PHASE 2
	if (phase2.contains(player.getUniqueId())) {

	    e.setCancelled(true);

	    description = e.getMessage();
	    player.sendMessage(
		    F.info("Guilds", false, "You have set your clan description to \"" + F.GOLD + description + "\"."));
	    phase2.remove(player.getUniqueId());
	    phase3.add(player.getUniqueId());
	    player.sendMessage(PHASE3_MESSAGE);
	    // BEGIN PHASE 3
	}

	if (phase3.contains(player.getUniqueId())) {

	    e.setCancelled(true);

	    tag = e.getMessage();

	    for (Guild g : Guild.allGuilds) {
		String gtag = g.getGuildTag();

		if (gtag == tag) {
		    player.sendMessage(F.error("Guilds", "That guild tag is already taken."));
		} else {
		    player.sendMessage(F.info("Guilds", false, "You have set your clan tag to: " + tag));

		    Guild newGuild = new Guild(name, new String[] {
			    description
		    }, tag, player, new ArrayList<UUID>());
		    newGuild.getGuildMembers().add(player.getUniqueId());
		    Guild.allGuilds.add(newGuild);

		    player.sendMessage(F.info("Guilds", false,
			    "You created the Guild [" + F.GOLD + newGuild.getGuildName() + F.GREEN + "]"));
		    phase3.remove(player.getUniqueId());
		    player.sendMessage(F.info("Guilds", false, "You have exited guild creation mode."));

		}
	    }

	}

    }

}