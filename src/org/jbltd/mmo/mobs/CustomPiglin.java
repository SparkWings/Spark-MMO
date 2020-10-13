package org.jbltd.mmo.mobs;

import org.bukkit.Location;
import org.jbltd.mmo.core.Main;

public class CustomPiglin extends CustomMob {

    public CustomPiglin(Main main, Location spawnLoc) {
        super(main, MobType.PIGLIN, spawnLoc);
    }
}
