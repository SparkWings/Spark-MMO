package org.jbltd.mmo.mobs;

import org.bukkit.Location;
import org.jbltd.mmo.core.Main;

public class CustomSkeleton extends CustomMob {

    public CustomSkeleton(Main main, Location spawnLoc) {
        super(main, MobType.SKELETON, spawnLoc);
    }
}
