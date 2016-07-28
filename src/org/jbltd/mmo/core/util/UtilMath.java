package org.jbltd.mmo.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import com.google.common.collect.MapMaker;

public class UtilMath
{

	public static HashMap<Block, Double> getBlocksInRadius(Location loc, double dR, double heightLimit)
	{
		HashMap<Block, Double> blocks = new HashMap<Block, Double>();
		int iR = (int) dR + 1;
		for (int x = -iR; x <= iR; x++)
		{
			for (int z = -iR; z <= iR; z++)
			{
				for (int y = -iR; y <= iR; y++)
				{
					if (Math.abs(y) <= heightLimit)
					{
						Block block = loc.getWorld().getBlockAt((int) (loc.getX() + x), (int) (loc.getY() + y), (int) (loc.getZ() + z));

						double offset = UtilMath.offset(loc, block.getLocation().add(0.5D, 0.5D, 0.5D));
						if (offset <= dR)
						{
							blocks.put(block, Double.valueOf(1.0D - offset / dR));
						}
					}
				}
			}
		}
		return blocks;
	}

	public static ConcurrentMap<Player, Location> lastPlaced = new MapMaker().weakKeys().weakValues().makeMap();

	public static Player findNearestPlayerPlacedBlock(Location location, int maximumDistance)
	{
		Player best = null;
		double bestDistance = Double.MAX_VALUE;

		for (Player player : location.getWorld().getPlayers())
		{
			Location lastPlacedBlock = lastPlaced.get(player);

			if (lastPlacedBlock != null)
			{
				double distance = location.distanceSquared(lastPlacedBlock);

				if (distance < bestDistance && distance < maximumDistance)
				{
					best = player;
					distance = bestDistance;
				}
			}
		}
		return best;
	}

	public void regen(final List<BlockState> blocks, final boolean effect, final int speed, JavaPlugin plugin)
	{

		new BukkitRunnable()
		{
			int i = -1;

			@SuppressWarnings("deprecation")
			public void run()
			{
				if (i != blocks.size() - 1)
				{
					i++;
					BlockState bs = blocks.get(i);
					bs.getBlock().setType(bs.getType());
					bs.getBlock().setData(bs.getBlock().getData());
					if (effect)
						bs.getBlock().getWorld().playEffect(bs.getLocation(), Effect.STEP_SOUND, bs.getBlock().getType());
				} else
				{
					for (BlockState bs : blocks)
					{
						bs.getBlock().setType(bs.getType());
						bs.getBlock().setData(bs.getBlock().getData());
					}
					blocks.clear();
					this.cancel();
				}
			}
		}.runTaskTimer(plugin, speed, speed);
	}

	public static double offset(Location a, Location b)
	{
		return offset(a.toVector(), b.toVector());
	}

	public static double offset(Vector a, Vector b)
	{
		return a.subtract(b).length();
	}

	public static List<UUID> getPlayersNearBlock(Block b)
	{

		List<UUID> players = new ArrayList<>();

		Location loc = b.getLocation();

		int iR = (int) 16;
		for (int x = -iR; x <= iR; x++)
		{
			for (int z = -iR; z <= iR; z++)
			{
				for (int y = -iR; y <= iR; y++)
				{

					Block block = loc.getWorld().getBlockAt((int) (loc.getX() + x), (int) (loc.getY() + y), (int) (loc.getZ() + z));

					Location loca = block.getLocation();

					for (Player player : Bukkit.getOnlinePlayers())
					{
						if (player.getLocation() == loca)
						{
							players.add(player.getUniqueId());
						}
					}

				}
			}

		}

		return players;

	}

	public static int random(int lowrange, int highrange)
	{

		int r = new Random().nextInt((highrange - lowrange)) + lowrange;

		return r;
	}

	private static Set<Location> makeHollow(Set<Location> blocks, boolean sphere)
	{
		Set<Location> edge = new HashSet<Location>();
		if (!sphere)
		{
			for (Location l : blocks)
			{
				World w = l.getWorld();
				int X = l.getBlockX();
				int Y = l.getBlockY();
				int Z = l.getBlockZ();
				Location front = new Location(w, X + 1, Y, Z);
				Location back = new Location(w, X - 1, Y, Z);
				Location left = new Location(w, X, Y, Z + 1);
				Location right = new Location(w, X, Y, Z - 1);
				if (!(blocks.contains(front) && blocks.contains(back) && blocks.contains(left) && blocks.contains(right)))
				{
					edge.add(l);
				}
			}
			return edge;
		} else
		{
			for (Location l : blocks)
			{
				World w = l.getWorld();
				int X = l.getBlockX();
				int Y = l.getBlockY();
				int Z = l.getBlockZ();
				Location front = new Location(w, X + 1, Y, Z);
				Location back = new Location(w, X - 1, Y, Z);
				Location left = new Location(w, X, Y, Z + 1);
				Location right = new Location(w, X, Y, Z - 1);
				Location top = new Location(w, X, Y + 1, Z);
				Location bottom = new Location(w, X, Y - 1, Z);
				if (!(blocks.contains(front) && blocks.contains(back) && blocks.contains(left) && blocks.contains(right) && blocks.contains(top) && blocks.contains(bottom)))
				{
					edge.add(l);
				}
			}
			return edge;
		}
	}

	public static Set<Location> circle(Location location, int radius, boolean hollow)
	{
		Set<Location> blocks = new HashSet<Location>();
		World world = location.getWorld();
		int X = location.getBlockX();
		int Y = location.getBlockY();
		int Z = location.getBlockZ();
		int radiusSquared = radius * radius;

		if (hollow)
		{
			for (int x = X - radius; x <= X + radius; x++)
			{
				for (int z = Z - radius; z <= Z + radius; z++)
				{
					if ((X - x) * (X - x) + (Z - z) * (Z - z) <= radiusSquared)
					{
						Location block = new Location(world, x, Y, z);
						blocks.add(block);
					}
				}
			}
			return makeHollow(blocks, false);
		} else
		{
			for (int x = X - radius; x <= X + radius; x++)
			{
				for (int z = Z - radius; z <= Z + radius; z++)
				{
					if ((X - x) * (X - x) + (Z - z) * (Z - z) <= radiusSquared)
					{
						Location block = new Location(world, x, Y, z);
						blocks.add(block);
					}
				}
			}
			return blocks;
		}
	}

}
