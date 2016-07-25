package org.jbltd.mmo.mobs;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.DyeColor;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jbltd.mmo.core.Main;
import org.jbltd.mmo.core.util.UpdateEvent;
import org.jbltd.mmo.core.util.UpdateType;
import org.jbltd.mmo.core.util.UtilMath;

public class CustomMobHandler implements Listener {

    public List<UUID> zombie = new ArrayList<>();
    public List<UUID> skele = new ArrayList<>();
    public List<UUID> pig = new ArrayList<>();

    @EventHandler
    public void spawnMobs(UpdateEvent e) {

	if (e.getType() != UpdateType.MIN_01)
	    return;


	for (Block b : Main.spawners) {

	    DyeColor d = DyeColor.getByData(b.getData());

	    switch (d) {
	    case BLUE:

		int value = UtilMath.random(1, 6);

		while (value >= 1) {

		    value--;
		    new CustomZombie(b.getLocation().add(0, 2, 0));

		}

		break;

	    case LIME:
		// Skele

		int value2 = UtilMath.random(1, 3);

		while (value2 >= 1) {

		    value2--;

		    new CustomSkeleton(b.getLocation().add(0, 2, 0));

		}
		break;

	    case YELLOW:

		
		    new CustomPigZombie(b.getLocation().add(0, 2, 0));

		    break;
		
	    case RED:
		// boss

		break;

	    default:
		break;

	    }

	}

    }
}
