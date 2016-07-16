package org.jbltd.mmo.mobs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.LivingEntity;
import org.jbltd.mmo.core.util.ItemFactory;

public abstract class CustomMob {

    private String name;
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
	    LivingEntity e = Bukkit.getWorld("world").spawnCreature(location, CreatureType.ZOMBIE);
	    e.setMaxHealth(getHealth());
	    e.setHealth(e.getMaxHealth());
	    e.setCustomName(ChatColor.RED + getName());
	    e.setCustomNameVisible(true);
	    e.getEquipment().setItemInHand(ItemFactory.createItem(getMobWeapon(), "Evil Sword", null, false));
	    break;

	case SKELETONWARRIOR:
	    LivingEntity e2 = Bukkit.getWorld("world").spawnCreature(location, CreatureType.SKELETON);
	    e2.setMaxHealth(getHealth());
	    e2.setHealth(e2.getMaxHealth());
	    e2.setCustomName(ChatColor.RED + getName());
	    e2.setCustomNameVisible(true);
	    e2.getEquipment().setHelmet(ItemFactory.createItem(Material.GOLD_HELMET, "", null, false));
	    e2.getEquipment().setChestplate(ItemFactory.createItem(Material.GOLD_CHESTPLATE, "", null, false));
	    e2.getEquipment().setLeggings(ItemFactory.createItem(Material.GOLD_LEGGINGS, "", null, false));
	    e2.getEquipment().setBoots(ItemFactory.createItem(Material.GOLD_BOOTS, "", null, false));
	    e2.getEquipment().setItemInHand(ItemFactory.createItem(getMobWeapon(), "Evil Sword", null, false));
	    break;

	case GHOUL:
	    LivingEntity e3 = Bukkit.getWorld("world").spawnCreature(location, CreatureType.PIG_ZOMBIE);
	    e3.setMaxHealth(getHealth());
	    e3.setHealth(e3.getMaxHealth());
	    e3.setCustomName(ChatColor.RED + getName());
	    e3.setCustomNameVisible(true);
	    e3.getEquipment().setHelmet(ItemFactory.createItem(Material.IRON_HELMET, "", null, false));
	    e3.getEquipment().setChestplate(ItemFactory.createItem(Material.IRON_CHESTPLATE, "", null, false));
	    e3.getEquipment().setLeggings(ItemFactory.createItem(Material.IRON_LEGGINGS, "", null, false));
	    e3.getEquipment().setBoots(ItemFactory.createItem(Material.IRON_BOOTS, "", null, false));
	    e3.getEquipment().setItemInHand(ItemFactory.createItem(getMobWeapon(), "Evil Sword", null, false));
	    
	    break;

	case BOSS:
	    break;

	}

    }

}
