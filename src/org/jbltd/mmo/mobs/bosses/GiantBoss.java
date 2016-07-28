package org.jbltd.mmo.mobs.bosses;

import org.bukkit.Location;
import org.bukkit.Material;
import org.jbltd.mmo.core.util.F;
import org.jbltd.mmo.mobs.MobType;

public class GiantBoss extends CustomBoss
{

	public GiantBoss(Location spawnLoc)
	{
		super(F.RED + "Zheal the Destroyer", 1000.0D, Material.DIAMOND_SWORD, MobType.GIANTBOSS, spawnLoc);
		// TODO Auto-generated constructor stub
	}

}
