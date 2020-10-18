package org.jbltd.mmo.core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jbltd.mmo.core.chat.Broadcast;
import org.jbltd.mmo.core.chat.ChatClear;
import org.jbltd.mmo.core.chat.MessageCommand;
import org.jbltd.mmo.core.util.UpdateTask;
import org.jbltd.mmo.core.util.listeners.BasicListener;
import org.jbltd.mmo.guilds.GuildCommand;
import org.jbltd.mmo.guilds.GuildInitTask;
import org.jbltd.mmo.guilds.GuildResponseCommands;
import org.jbltd.mmo.guilds.InviteCommand;
import org.jbltd.mmo.mobs.CustomMobHandler;

import java.io.File;
import java.io.IOException;

/**
 * TODO JSON Guild parsing
 * TODO JSON Mob parsing
 * TODO JSON Armor Sets
 */
public class Main extends JavaPlugin {

    public void onEnable() {


        //Guilds
        File guildsJSON = new File(Bukkit.getServer().getWorldContainer().getAbsolutePath(), "guilds.json");
        if (!guildsJSON.exists()) {
            try {
                guildsJSON.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        new GuildInitTask(this);



        //Listeners
        getServer().getPluginManager().registerEvents(new BasicListener(), this);
        getServer().getPluginManager().registerEvents(new CustomMobHandler(this), this);
        getServer().getPluginManager().registerEvents(new GuildCommand(), this);

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


    }
    public void onDisable() {
    }

}
