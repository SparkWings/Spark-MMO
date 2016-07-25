package org.jbltd.mmo.guilds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jbltd.mmo.core.util.F;

public class GuildResponseCommands implements CommandExecutor
{
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		
		if(!(sender instanceof Player))
			return true;
		
		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("accept"))
		{
			System.out.println("CALLED");
			if(!InviteCommand.pendingInvitees.containsKey(player.getUniqueId()))
			{
				player.sendMessage(F.error("Guilds", "You do not have any pending invites."));
				return true;
			}
			
			GuildInviteTask git = InviteCommand.pendingInvitees.get(player.getUniqueId());
			git.response = true;
			git.respondedToInvite = true;
			return false;
			
		}
		
		if(cmd.getName().equalsIgnoreCase("deny"))
		{
			
			System.out.println("CALLLLLLEEEDDDD");
			
			if(!InviteCommand.pendingInvitees.containsKey(player.getUniqueId()))
			{
				player.sendMessage(F.error("Guilds", "You do not have any pending invites."));
				return true;
			}
			
			GuildInviteTask git = InviteCommand.pendingInvitees.get(player.getUniqueId());
			git.response = false;
			git.respondedToInvite = false;
			return false;
			
			
		}
		
		return false;
		
	}
	

}
