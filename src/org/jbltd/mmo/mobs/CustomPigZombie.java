package org.jbltd.mmo.mobs;

import org.bukkit.Location;
import org.bukkit.Material;

public class CustomPigZombie extends CustomMob {

    public CustomPigZombie(Location spawnloc) {
	super("Ghoul", 30.0D, Material.IRON_SWORD, MobType.GHOUL, spawnloc);
    }

}
