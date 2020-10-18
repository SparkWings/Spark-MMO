package org.jbltd.mmo.mobs;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.jbltd.mmo.core.Main;
import org.jbltd.mmo.core.util.UpdateEvent;
import org.jbltd.mmo.core.util.UpdateType;
import org.jbltd.mmo.core.util.UtilMath;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomMobHandler implements Listener {

    private File mobConfigFile;
    private FileConfiguration mobConfig;
    public static List<Block> spawners = new ArrayList<>();
    public static List<String> configMaterials = new ArrayList<>();
    public List<UUID> zombie = new ArrayList<>();
    public List<UUID> skele = new ArrayList<>();
    public List<UUID> pig = new ArrayList<>();
    public List<UUID> giant = new ArrayList<>();
    public List<UUID> drowned = new ArrayList<>();
    private Main jPlugin;

    public CustomMobHandler(Main plugin) {
        this.jPlugin = plugin;

        handleConfig(jPlugin);
        handleAddSpawners();
    }

    private void handleAddSpawners() {
        World w = Bukkit.getWorld("world");

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
                    }
                }
            }
        }
    }

    private FileConfiguration getMobConfig() {
        return this.mobConfig;
    }

    private void handleConfig(Main plugin) {
        mobConfigFile = new File(jPlugin.getDataFolder(), "mobs.yml");

        if (!mobConfigFile.exists()) {
            mobConfigFile.getParentFile().mkdirs();
            jPlugin.saveResource("mobs.yml", false);
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

    @EventHandler
    public void spawnMobs(UpdateEvent e) {

        if (e.getType() == UpdateType.MIN_01) {

            for (Block b : spawners) {
                switch (b.getType().toString()) {
                    case "LIME_WOOL":
                        int value = UtilMath.random(1, 6);

                        for (int i = value; i > 0; i--) {
                            if (zombie.size() <= getMobConfig().getInt("zombie.maxNumber")) {
                                i--;
                                CustomZombie z = new CustomZombie(jPlugin, b.getLocation().add(0, 2, 0));
                                zombie.add(z.getUniqueId());
                            }

                        }

                        break;

                    case "YELLOW_WOOL":
                        int value2 = UtilMath.random(1, 3);

                        for (int i = value2; i > 0; i--) {

                            if (skele.size() <= getMobConfig().getInt("skeleton.maxNumber")) {
                                i--;

                                CustomSkeleton s = new CustomSkeleton(jPlugin, b.getLocation().add(0, 2, 0));
                                skele.add(s.getUniqueId());
                            }
                        }
                        break;

                    case "BLUE_WOOL":

                        CustomPiglin p = new CustomPiglin(jPlugin, b.getLocation().add(0, 2, 0));
                        pig.add(p.getUniqueId());

                        break;

                    default:
                        break;

                }

            }
        }
        if (e.getType() == UpdateType.MIN_08) {
            System.out.println("8");
            for (Block b : spawners) {

                switch (b.getType().toString()) {

                    case "RED_WOOL":
                        System.out.println("RED");
                        // GiantBoss bz = new GiantBoss(b.getLocation());
                        // giant.add(bz.getUniqueId());

                        break;

                    default:
                        break;
                }
            }
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent e) {

        Entity en = e.getEntity();

        zombie.remove(en.getUniqueId());
        skele.remove(en.getUniqueId());
        pig.remove(en.getUniqueId());
        giant.remove(en.getUniqueId());
    }

    @EventHandler
    public void addParseSpawners(ChunkLoadEvent e) {

        Chunk c = e.getChunk();
        World w = c.getWorld();

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

                }
            }

        }


    }

}
