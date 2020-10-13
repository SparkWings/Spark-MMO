package org.jbltd.mmo.mobs.bosses;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.jbltd.mmo.core.Main;
import org.jbltd.mmo.core.util.F;
import org.jbltd.mmo.core.util.UtilMath;
import org.jbltd.mmo.mobs.CustomMob;
import org.jbltd.mmo.mobs.MobType;

public abstract class CustomBoss extends CustomMob {


    public CustomBoss(Main main, MobType mobType, Location spawnLoc) {
        super(main, mobType, spawnLoc);
    }

    @Override
    public void spawn(Location location, MobType type) {
        super.spawn(location, type);

        //UtilMath.circle(getMobSpawnLocation(), 4, true).forEach(value -> new CustomPigZombie(value));

        Bukkit.broadcastMessage(F.info("Boss", false, F.RED + "A boss has spawned at (" + F.AQUA + getMobSpawnLocation().getBlockX() + F.RED + "," + F.AQUA
                + getMobSpawnLocation().getBlockY() + F.RED + "," + F.AQUA + getMobSpawnLocation().getBlockZ() + F.RED + ")"));

    }

}
