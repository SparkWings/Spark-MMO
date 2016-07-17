package org.jbltd.mmo.guilds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jbltd.mmo.core.util.F;

public class InviteCommand implements CommandExecutor{

    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
	
	if(!(sender instanceof Player))
	    return true;
	
	Player player = (Player) sender;
	
	if(cmd.getName().equalsIgnoreCase("invite"))
	{
	    
	    for(Guild g : Guild.allGuilds)
	    {
		if(!g.getGuildMembers().contains(player.getUniqueId()))
		    continue;
		
		else
		{
		    if(player.getUniqueId() == g.getGuildLeader().getUniqueId())
		    {
			//Guild Invitation
		    }
		    else 
		    {
			player.sendMessage(F.error("Guilds", "You are not the guild leader."));
		    }
		}
		
	    }
	    
	}
	
	
	return false;
    }
    
    
}
