package org.jbltd.mmo.guilds;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jbltd.mmo.core.util.JSONUtil;

public class GuildInitTask implements Runnable
{

	private JavaPlugin plugin;
	
	public GuildInitTask(JavaPlugin plugin)
	{
		this.plugin = plugin;
		
		Bukkit.getScheduler().runTaskAsynchronously(plugin, this);
	}
	
	@Override
	public void run()
	{
		
		JSONUtil.readGuildsFile();
		
	}

}
