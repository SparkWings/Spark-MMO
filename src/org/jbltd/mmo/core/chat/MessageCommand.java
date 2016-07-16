package org.jbltd.mmo.core.chat;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jbltd.mmo.core.util.F;

public class MessageCommand implements CommandExecutor
{

	private HashMap<UUID, UUID> _lastMessage = new HashMap<>();
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		
		if(!(sender instanceof Player))
			return true;
		

		Player player = (Player) sender;
		
		if(cmd.getName().equalsIgnoreCase("message"))
		{
			
			if(args.length < 2)
			{
				player.sendMessage(F.error("Message", "Not enough arguments!"));
				return true;
			}
			
			Player target = Bukkit.getPlayer(args[0]);
			
			if(target == null)
			{
				return true;
			}
			
			if(!target.isOnline())
			{
				player.sendMessage(F.error("Message", "That player is not online."));
				return true;
			}
			
			if(!target.hasPlayedBefore())
			{
				player.sendMessage(F.error("Message", "That player has never played before!"));
			}
			
			StringBuilder sb = new StringBuilder();
			for(int i = 1; i < args.length; i++)
			{
				sb.append(args[i] + " ");
			}
			
			String message = sb.toString().trim();

			player.sendMessage(F.BLUE+F.BOLD+"YOU > "+target.getName().toUpperCase()+F.WHITE+F.BOLD+" "+message);
			target.sendMessage(F.BLUE+F.BOLD+player.getName().toUpperCase()+" > ME"+F.WHITE+F.BOLD+" "+message);
			
			_lastMessage.put(player.getUniqueId(), target.getUniqueId());
			
			return false;
			
		}
		
		if(cmd.getName().equalsIgnoreCase("reply"))
		{
			if(!_lastMessage.containsKey(player.getUniqueId()))
			{
				player.sendMessage(F.error("Message", "You have not messaged someone recently."));
				return true;
			}
			
			if(args.length < 1)
			{
				
				player.sendMessage(F.error("Message", "You must provide a message."));
				return true;
				
			}
			
			Player target = Bukkit.getPlayer(_lastMessage.get(player.getUniqueId()));
			
			if(!target.isOnline())
			{
				player.sendMessage(F.error("Message", target.getName()+" is no longer online."));
			}
			
			StringBuilder sb = new StringBuilder();
			for(int i = 0; i < args.length; i++)
			{
				sb.append(args[i] + " ");
			}
			
			String message = sb.toString().trim();

			player.sendMessage(F.BLUE+F.BOLD+"YOU > "+target.getName().toUpperCase()+F.WHITE+F.BOLD+" "+message);
			target.sendMessage(F.BLUE+F.BOLD+player.getName().toUpperCase()+" > ME"+F.WHITE+F.BOLD+" "+message);
			return false;
			
		}
		
		
		return false;
		
	}
	
	
}
