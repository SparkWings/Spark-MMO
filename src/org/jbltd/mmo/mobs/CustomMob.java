package org.jbltd.mmo.mobs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.jbltd.mmo.core.util.F;
import org.jbltd.mmo.core.util.ItemFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class CustomMob {

    private String name;
    private UUID uuid;
    private double health;
    private Material weapon;
    private MobType mobtype;
    private Location spawnLocation;

    public List<LivingEntity> allMobs = new ArrayList<LivingEntity>();

    public CustomMob(final String name, double health, Material weapon, MobType mobtype, Location spawnloc) {
	this.name = name;
	this.health = health;
	this.weapon = weapon;
	this.mobtype = mobtype;
	this.spawnLocation = spawnloc;

	spawn(spawnloc, mobtype);

    }

    public String getName() {
	return name;

    }

    public UUID getUniqueId()
    {
    	return uuid;
    }
    
    public double getHealth() {
	return health;
    }

    public Material getMobWeapon() {
	return weapon;
    }

    public MobType getMobType() {
	return mobtype;
    }

    public Location getMobSpawnLocation() {
	return spawnLocation;
    }

    @SuppressWarnings("deprecation")
    public void spawn(Location location, MobType type) {

	switch (type) {

	case GRUNTZOMBIE:
		Zombie zombie = (Zombie) Bukkit.getWorld("world").spawnEntity(location, EntityType.ZOMBIE);
	    zombie.setMaxHealth(getHealth());
	    zombie.setHealth(zombie.getMaxHealth());
	    zombie.setCustomName(ChatColor.RED + getName());
	    zombie.setCustomNameVisible(true);
	    zombie.getEquipment().setItemInHand(ItemFactory.createItem(getMobWeapon(), "Evil Sword", null, false));
	    uuid = zombie.getUniqueId();
	    break;

	case SKELETONWARRIOR:
	    Skeleton skeleton = (Skeleton) Bukkit.getWorld("world").spawnEntity(location, EntityType.SKELETON);
	    skeleton.setMaxHealth(getHealth());
	    skeleton.setHealth(skeleton.getMaxHealth());
	    skeleton.setCustomName(ChatColor.RED + getName());
	    skeleton.setCustomNameVisible(true);
	    skeleton.getEquipment().setHelmet(ItemFactory.createItem(Material.GOLDEN_HELMET, "", null, false));
	    skeleton.getEquipment().setChestplate(ItemFactory.createItem(Material.GOLDEN_CHESTPLATE, "", null, false));
	    skeleton.getEquipment().setLeggings(ItemFactory.createItem(Material.GOLDEN_LEGGINGS, "", null, false));
	    skeleton.getEquipment().setBoots(ItemFactory.createItem(Material.GOLDEN_BOOTS, "", null, false));
	    skeleton.getEquipment().setItemInHand(ItemFactory.createItem(getMobWeapon(), "Evil Sword", null, false));
	    uuid = skeleton.getUniqueId();
	    break;

	case GHOUL:
	    PigZombie piglin = (PigZombie) Bukkit.getWorld("world").spawnEntity(location, EntityType.ZOMBIFIED_PIGLIN);
	    piglin.setMaxHealth(getHealth());
	    piglin.setHealth(piglin.getMaxHealth());
	    piglin.setCustomName(ChatColor.RED + getName());
	    piglin.setCustomNameVisible(true);
	    piglin.getEquipment().setHelmet(ItemFactory.createItem(Material.IRON_HELMET, "", null, false));
	    piglin.getEquipment().setChestplate(ItemFactory.createItem(Material.IRON_CHESTPLATE, "", null, false));
	    piglin.getEquipment().setLeggings(ItemFactory.createItem(Material.IRON_LEGGINGS, "", null, false));
	    piglin.getEquipment().setBoots(ItemFactory.createItem(Material.IRON_BOOTS, "", null, false));
	    piglin.getEquipment().setItemInHand(ItemFactory.createItem(getMobWeapon(), "Evil Sword", null, false));
	    uuid = piglin.getUniqueId();
	    break;

	case GIANTBOSS:
		Giant giant = (Giant) Bukkit.getWorld("world").spawnEntity(location, EntityType.GIANT);
		giant.setMaxHealth(getHealth());
	    giant.setHealth(giant.getMaxHealth());
	    giant.setCustomName(ChatColor.RED + getName());
	    giant.setCustomNameVisible(true);
	    giant.getEquipment().setItemInHand(ItemFactory.createItem(getMobWeapon(), F.DARK_RED+"Sword of Destruction", null, false));
	    uuid = giant.getUniqueId();
	    
	default:
		break;
	}

    }

}
