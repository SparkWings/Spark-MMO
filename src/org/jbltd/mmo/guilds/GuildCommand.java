package org.jbltd.mmo.guilds;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.jbltd.mmo.core.util.JSONUtil;

public class GuildCommand implements CommandExecutor , Listener
{

	private static final String PHASE1_MESSAGE = F.GOLD + F.BOLD + "Please type the name of the guild you wish to create.";
	private static final String PHASE2_MESSAGE = F.GOLD + F.BOLD + "Please type the description of the guild you wish to create (Max 1 Line).";
	private static final String PHASE3_MESSAGE = F.GOLD + F.BOLD + "Please enter the TAG of your guild (This will be your prefix in chat).";
	private static final String ESCAPE_MESSAGE = F.RED + "You have exited guild creation mode.";

	private String name = "";
	private String description = "";
	private String tag = "";

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{

		if (!(sender instanceof Player))
			return true;

		Player p = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("guild"))
		{

			for (Guild g : Guild.allGuilds)
			{
				List<UUID> members = g.getGuildMembers();
				if (members.contains(p.getUniqueId()))
				{
					p.sendMessage(F.error("Guilds", "You are already in a guild."));
					return true;
				} else
					continue;
			}

			Guild.phase1.add(p.getUniqueId());
			System.out.println("put player in list");
			p.sendMessage(F.info("Guilds", false, "You have entered guild creation mode. Follow the instructions you are prompted in the chat to create your new guild."));

			p.sendMessage(PHASE1_MESSAGE);
			return false;

		}

		if (cmd.getName().equalsIgnoreCase("escape"))
		{

			if (Guild.phase1.contains(p.getUniqueId()))
			{
				Guild.phase1.remove(p.getUniqueId());
				p.sendMessage(ESCAPE_MESSAGE);
				return false;

			} else if (Guild.phase2.contains(p.getUniqueId()))
			{
				Guild.phase2.remove(p.getUniqueId());
				p.sendMessage(ESCAPE_MESSAGE);
				return false;
			} else if (Guild.phase3.contains(p.getUniqueId()))
			{
				Guild.phase3.remove(p.getUniqueId());
				p.sendMessage(ESCAPE_MESSAGE);
				return false;
			} else
			{
				p.sendMessage(F.RED + "You are not in guild creation mode.");
				return true;
			}

		}

		return false;

	}

	@EventHandler
	public void guildCreation(AsyncPlayerChatEvent e)
	{

		Player player = e.getPlayer();

		// PHASE 1
		if (Guild.phase1.contains(player.getUniqueId()))
		{
			// Begin

			e.setCancelled(true);
			name = e.getMessage().trim().replace(" ", "");

			for (Guild g : Guild.allGuilds)
			{
				if (g.getGuildName() == name)
				{
					player.sendMessage(F.error("Guilds", "That name is already taken."));
					player.sendMessage(PHASE1_MESSAGE);
					System.err.println("guild name already exists");
				} else
				{
					continue;
				}
			}

			player.sendMessage(F.info("Guilds", false, "You have set your guild name to: " + F.GOLD + name));
			Guild.phase1.remove(player.getUniqueId());
			Guild.phase2.add(player.getUniqueId());
			player.sendMessage(PHASE2_MESSAGE);
			// BEGIN PHASE 2
			System.out.println("Beginning phase 2");

		}

		// PHASE 2
		else if (Guild.phase2.contains(player.getUniqueId()))
		{

			e.setCancelled(true);

			description = e.getMessage();
			player.sendMessage(F.info("Guilds", false, "You have set your clan description to \"" + F.GOLD + description + F.GREEN + "\"."));
			Guild.phase2.remove(player.getUniqueId());
			Guild.phase3.add(player.getUniqueId());
			player.sendMessage(PHASE3_MESSAGE);
			// BEGIN PHASE 3
		}

		else if (Guild.phase3.contains(player.getUniqueId()))
		{

			e.setCancelled(true);

			tag = e.getMessage();

			for (Guild g : Guild.allGuilds)
			{
				String gtag = g.getGuildTag();

				if (gtag == tag)
				{
					player.sendMessage(F.error("Guilds", "That guild tag is already taken."));
				} else
				{
					continue;

				}
			}

			player.sendMessage(F.info("Guilds", false, "You have set your clan tag to: " + F.GOLD+tag));

			Guild newGuild = new Guild(name, description, tag, player, new ArrayList<UUID>());
			newGuild.getGuildMembers().add(player.getUniqueId());
			Guild.allGuilds.add(newGuild);
			JSONUtil.writeToGuildsfile(name, description, tag, player.getUniqueId().toString(), newGuild.getGuildMembers(), true);

			player.sendMessage(F.info("Guilds", false, "You created the Guild [" + F.GOLD + name + F.GREEN + "]"));
			Guild.phase3.remove(player.getUniqueId());
			player.sendMessage(F.info("Guilds", false, "You have exited guild creation mode."));

		} else
		{
			return;
		}

	}

}
