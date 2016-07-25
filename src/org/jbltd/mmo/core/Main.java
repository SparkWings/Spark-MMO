package org.jbltd.mmo.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;
import org.jbltd.mmo.core.chat.Broadcast;
import org.jbltd.mmo.core.chat.ChatClear;
import org.jbltd.mmo.core.chat.MessageCommand;
import org.jbltd.mmo.core.util.JSONUtil;
import org.jbltd.mmo.core.util.UpdateTask;
import org.jbltd.mmo.core.util.UtilPacket;
import org.jbltd.mmo.core.util.listeners.BasicListener;
import org.jbltd.mmo.guilds.Guild;
import org.jbltd.mmo.guilds.GuildCommand;
import org.jbltd.mmo.guilds.GuildInitTask;
import org.jbltd.mmo.guilds.GuildResponseCommands;
import org.jbltd.mmo.guilds.InviteCommand;
import org.jbltd.mmo.mobs.CustomMobHandler;

import net.minecraft.server.v1_8_R3.MinecraftServer;

public class Main extends JavaPlugin
{

	/**
	 * TODO
	 * Mob Limits
	 * Guild Kicking
	 * 
	 */

	public static List<Block> spawners = new ArrayList<>();

	public void onEnable()
	{

		//Guilds
		File guildsJSON = new File(Bukkit.getServer().getWorldContainer().getAbsolutePath(), ".guilds.json");
		if (!guildsJSON.exists())
		{
			try
			{
				guildsJSON.createNewFile();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		new GuildInitTask(this);
		
		new UtilPacket(this);

		//Listeners
		getServer().getPluginManager().registerEvents(new BasicListener(), this);
		getServer().getPluginManager().registerEvents(new CustomMobHandler(), this);
		getServer().getPluginManager().registerEvents(new GuildCommand(), this);

		// getServer().getPluginManager().registerEvents(new UndeadSet(), this);

		//Commands
		getCommand("broadcast").setExecutor(new Broadcast());
		getCommand("chat").setExecutor(new ChatClear());
		getCommand("message").setExecutor(new MessageCommand());
		getCommand("reply").setExecutor(new MessageCommand());
		getCommand("guild").setExecutor(new GuildCommand());
		getCommand("invite").setExecutor(new InviteCommand(this));
		getCommand("escape").setExecutor(new GuildCommand());
		getCommand("accept").setExecutor(new GuildResponseCommands());
		getCommand("deny").setExecutor(new GuildResponseCommands());

		//UpdateEvent Runnable
		new UpdateTask(this);

		//Spawners
		World w = Bukkit.getWorld("world");

		for (Chunk c : w.getLoadedChunks())
		{

			int cx = c.getX() << 4;
			int cz = c.getZ() << 4;
			for (int x = cx; x < cx + 16; x++)
			{
				for (int z = cz; z < cz + 16; z++)
				{
					for (int y = 0; y < 128; y++)
					{
						if (w.getBlockAt(x, y, z).getType() == Material.WOOL)
						{

							spawners.add(w.getBlockAt(x, y, z));

						}
					}
				}

			}

		}

		MinecraftServer.getServer().getPropertyManager().setProperty("debug", true);
		
	}

	public void onDisable()
	{
	}

}
