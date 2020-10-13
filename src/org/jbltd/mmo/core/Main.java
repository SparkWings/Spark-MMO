package org.jbltd.mmo.core;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jbltd.mmo.armorsets.UndeadSet;
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
import java.util.ArrayList;
import java.util.List;


public class Main extends JavaPlugin {

    public static List<Block> spawners = new ArrayList<>();
    /**
     * TODO Guild Kicking
     */

    private List<String> configMaterials = new ArrayList<>();
    private File mobConfigFile;
    private FileConfiguration mobConfig;


    public void onEnable() {

        createConfigFiles();

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

        //new UtilPacket(this);

        //Listeners
        getServer().getPluginManager().registerEvents(new BasicListener(), this);
        getServer().getPluginManager().registerEvents(new CustomMobHandler(this), this);
        getServer().getPluginManager().registerEvents(new GuildCommand(), this);

        //Armor Sets
        getServer().getPluginManager().registerEvents(new UndeadSet(), this);

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

        //Looping through all loaded chunks and adding spawners based on material type as defined in config
        for (Chunk c : w.getLoadedChunks()) {
            int cx = c.getX() << 4;
            int cz = c.getZ() << 4;

            for (int x = cx; x < cx + 16; x++) {
                for (int z = cz; z < cz + 16; z++) {
                    for (int y = 0; y < 128; y++) {
                        Block b = w.getBlockAt(x, y, z);
                        Material m = b.getType();

                        for (String s : configMaterials) {
                            if (m.toString().equalsIgnoreCase(s)) {
                                spawners.add(b);
                                System.out.println("added " + s);
                            } else continue;
                        }
//
//                        if (w.getBlockAt(x, y, z).getType().toString().endsWith("WOOL")) {
//                            System.out.println("FOUND!!");
//                            spawners.add(w.getBlockAt(x, y, z));
//
//                        }
                    }
                }

            }

        }
        spawners.forEach(value -> System.out.println(value));

    }

    public FileConfiguration getMobConfig() {
        return this.mobConfig;
    }

    private void createConfigFiles() {
        mobConfigFile = new File(getDataFolder(), "mobs.yml");

        if (!mobConfigFile.exists()) {
            mobConfigFile.getParentFile().mkdirs();
            saveResource("mobs.yml", false);
        } else {
            mobConfig = new YamlConfiguration();
            try {
                mobConfig.load(mobConfigFile);

                //Add spawn materials
                configMaterials.add(getMobConfig().getString("zombie.spawnsOn"));
                configMaterials.add(getMobConfig().getString("skeleton.spawnsOn"));
                configMaterials.add(getMobConfig().getString("piglin.spawnsOn"));


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void onDisable() {
    }

}
