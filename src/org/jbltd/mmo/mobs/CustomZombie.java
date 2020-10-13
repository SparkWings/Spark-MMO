package org.jbltd.mmo.mobs;

import org.bukkit.Location;
import org.bukkit.Material;
import org.jbltd.mmo.core.Main;

public class CustomZombie extends CustomMob {

    public CustomZombie(Main main, Location spawnLoc) {
        super(main, MobType.ZOMBIE, spawnLoc);
    }
}
