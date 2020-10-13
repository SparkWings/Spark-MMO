package org.jbltd.mmo.guilds;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jbltd.mmo.core.util.F;

public class InviteCommand implements CommandExecutor
{

	protected static HashMap<UUID, GuildInviteTask> pendingInvitees = new HashMap<>();

	private final JavaPlugin plugin;

	public InviteCommand(JavaPlugin plugin)
	{
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{

		if (!(sender instanceof Player))
			return true;

		Player player = (Player) sender;

		if (cmd.getName().equalsIgnoreCase("invite"))
		{

			if (args.length < 1)
			{
				player.sendMessage(F.error("Guilds", "Incorrect syntax! Try using /invite <player>"));
				return true;
			}

			Player target = Bukkit.getPlayer(args[0]);

			if (target == null)
			{
				player.sendMessage(F.error("Guilds", "That player does not exist."));
				return true;
			}
			if (!target.isOnline())
			{
				player.sendMessage(F.error("Guilds", "That player is not online."));
				return true;
			}

			Guild pG = null;

			for (Guild g : Guild.allGuilds)
			{
				if (!g.getGuildMembers().contains(player.getUniqueId()))
					continue;

				else
				{
					pG = g;
					break;
				}

			}

			if (player.getUniqueId() == pG.getGuildLeader().getUniqueId())
			{

				if (pendingInvitees.containsKey(target.getUniqueId()))
				{
					player.sendMessage(F.error("Guilds", "That player already has a pending invite from another guild."));
					return true;
				}

				GuildInviteTask git = new GuildInviteTask(plugin, target, pG);
				pendingInvitees.put(target.getUniqueId(), git);

			} else
			{
				player.sendMessage(F.error("Guilds", "You are not the guild leader."));
			}

		}

		return false;
	}

}
