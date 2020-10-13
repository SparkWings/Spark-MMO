package org.jbltd.mmo.mobs;

import org.bukkit.Location;
import org.bukkit.Material;

public class CustomSkeleton extends CustomMob {

    public CustomSkeleton(Location spawnloc) {
	super("Skeleton Warrior", 20.0D, Material.WOODEN_AXE, MobType.SKELETONWARRIOR, spawnloc);
    }

}
