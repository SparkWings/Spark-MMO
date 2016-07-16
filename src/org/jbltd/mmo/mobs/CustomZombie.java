package org.jbltd.mmo.mobs;

import org.bukkit.Location;
import org.bukkit.Material;

public class CustomZombie extends CustomMob {

    public CustomZombie(Location spawnloc) {
	super("Zombie Grunt", 10.0D, Material.WOOD_AXE, MobType.GRUNTZOMBIE, spawnloc);
    }

}
