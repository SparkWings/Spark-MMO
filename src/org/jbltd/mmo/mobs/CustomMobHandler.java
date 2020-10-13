package org.jbltd.mmo.mobs;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jbltd.mmo.core.Main;
import org.jbltd.mmo.core.util.UpdateEvent;
import org.jbltd.mmo.core.util.UpdateType;
import org.jbltd.mmo.core.util.UtilMath;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CustomMobHandler implements Listener {

    public List<UUID> zombie = new ArrayList<>();
    public List<UUID> skele = new ArrayList<>();
    public List<UUID> pig = new ArrayList<>();
    public List<UUID> giant = new ArrayList<>();
    public List<UUID> drowned = new ArrayList<>();
    private Main jPlugin;

    public CustomMobHandler(Main plugin) {
        this.jPlugin = plugin;
    }

    @EventHandler
    public void spawnMobs(UpdateEvent e) {

        if (e.getType() == UpdateType.MIN_01) {

            for (Block b : Main.spawners) {
                switch (b.getType().toString()) {
                    case "LIME_WOOL":
                        int value = UtilMath.random(1, 6);

                        for (int i = value; i > 0; i--) {
                            if (zombie.size() <= jPlugin.getMobConfig().getInt("zombie.maxNumber")) {
                                i--;
                                CustomZombie z = new CustomZombie(jPlugin, b.getLocation().add(0, 2, 0));
                                zombie.add(z.getUniqueId());
                            }

                        }

                        break;

                    case "YELLOW_WOOL":
                        int value2 = UtilMath.random(1, 3);

                        for (int i = value2; i > 0; i--) {

                            if (skele.size() <= jPlugin.getMobConfig().getInt("skeleton.maxNumber")) {
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
            for (Block b : Main.spawners) {

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

}
