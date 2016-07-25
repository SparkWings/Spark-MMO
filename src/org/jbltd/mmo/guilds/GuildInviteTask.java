package org.jbltd.mmo.guilds;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jbltd.mmo.core.util.F;

public class GuildInviteTask extends Thread
{

	private Player _player;
	private Guild g;
	public volatile boolean respondedToInvite = false;
	public volatile boolean response = false;

	public GuildInviteTask(Player player, Guild g)
	{
		this._player = player;
		this.g = g;
	}

	public void start()
	{
		run();
	}

	@Override
	public void run()
	{

		_player.sendMessage(F.info("Guilds", false, g.getGuildLeader().getName()+" has invited you to join "+g.getGuildName()));
		_player.sendMessage(F.info("Guilds", false, "Type either /accept or /deny to respond to this invite."));
		
		while (!respondedToInvite)
		{
			break;
		}

		try
		{
			_player.sendMessage(F.info("Guilds", false, "You have " + ((response = true) ? "accepted" : "declined") + " the guild invite from " + g.getGuildName()));

			if (response = true)
			{
				g.addMember(_player);

				g.getGuildMembers().forEach(uuid -> Bukkit.getPlayer(uuid).sendMessage(F.info("Guilds", false, _player.getName() + " has joined your guild.")));

				join();
			}

			else
				join();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Player getPlayer()
	{
		return _player;
	}
	
	public boolean getResponse()
	{
		return response;
	}
	
	public boolean hasResponded()
	{
		return respondedToInvite;
	}
	
}
