package org.jbltd.mmo.mobs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jbltd.mmo.core.Main;
import org.jbltd.mmo.core.util.ItemFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class CustomMob implements Listener {

    public List<LivingEntity> allMobs = new ArrayList<LivingEntity>();
    private Main mainClazz;
    private String name;
    private UUID uuid;
    private double health;
    private Material weapon;
    private MobType mobtype;
    private Location spawnLocation;


    public CustomMob(Main main, MobType mobType, Location spawnLoc) {
        this.mainClazz = main;
        this.mobtype = mobType;

        String mobTypeS = this.mobtype.toString().toLowerCase();
        this.name = getMainClazz().getMobConfig().getString(mobTypeS + ".name");
        this.health = getMainClazz().getMobConfig().getDouble(mobTypeS + ".maxHealth");
        this.weapon = Material.getMaterial(getMainClazz().getMobConfig().getString(mobTypeS + ".weapon"));
        this.spawnLocation = spawnLoc;

        spawn(spawnLoc, mobType);
    }

    public Main getMainClazz() {
        return mainClazz;
    }

    public String getName() {
        return name;
    }

    public UUID getUniqueId() {
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

    //TODO Drop chances: 0.0 for never, 1.0 for always
    @SuppressWarnings("deprecation")
    public void spawn(Location location, MobType type) {
        switch (type) {
            case ZOMBIE:
                Zombie zombie = (Zombie) Bukkit.getWorld("world").spawnEntity(location, EntityType.ZOMBIE);
                zombie.setMaxHealth(getHealth());
                zombie.setHealth(zombie.getMaxHealth());
                zombie.setCustomName(ChatColor.RED + getName());
                zombie.setCustomNameVisible(true);
                zombie.getEquipment().setItemInHand(ItemFactory.createItem(getMobWeapon(), "Evil Sword", null, false));
                zombie.getEquipment().setHelmet(ItemFactory.createItem(
                        Material.getMaterial(getMainClazz().getMobConfig().getString("zombie.armorHelmet")), "Undead Helmet", null, false));
                zombie.getEquipment().setChestplate(ItemFactory.createItem(
                        Material.getMaterial(getMainClazz().getMobConfig().getString("zombie.armorChestplate")), "Undead Chestplate", null, false));
                zombie.getEquipment().setLeggings(ItemFactory.createItem(
                        Material.getMaterial(getMainClazz().getMobConfig().getString("zombie.armorLeggings")), "Undead Leggings", null, false));
                zombie.getEquipment().setBoots(ItemFactory.createItem(
                        Material.getMaterial(getMainClazz().getMobConfig().getString("zombie.armorBoots")), "Undead Boots", null, false));
                uuid = zombie.getUniqueId();
                break;

            case SKELETON:
                Skeleton skeleton = (Skeleton) Bukkit.getWorld("world").spawnEntity(location, EntityType.SKELETON);
                skeleton.setMaxHealth(getHealth());
                skeleton.setHealth(skeleton.getMaxHealth());
                skeleton.setCustomName(ChatColor.RED + getName());
                skeleton.setCustomNameVisible(true);
                skeleton.getEquipment().setHelmet(ItemFactory.createItem(
                        Material.getMaterial(getMainClazz().getMobConfig().getString("skeleton.armorHelmet")), "Skeletal Helmet", null, false));
                skeleton.getEquipment().setChestplate(ItemFactory.createItem(
                        Material.getMaterial(getMainClazz().getMobConfig().getString("skeleton.armorChestplate")), "Skeletal Chestplate", null, false));
                skeleton.getEquipment().setLeggings(ItemFactory.createItem(
                        Material.getMaterial(getMainClazz().getMobConfig().getString("skeleton.armorLeggings")), "Skeletal Leggings", null, false));
                skeleton.getEquipment().setBoots(ItemFactory.createItem(
                        Material.getMaterial(getMainClazz().getMobConfig().getString("skeleton.armorBoots")), "Skeletal Boots", null, false));
                uuid = skeleton.getUniqueId();
                break;

            case PIGLIN:
                PigZombie piglin = (PigZombie) Bukkit.getWorld("world").spawnEntity(location, EntityType.ZOMBIFIED_PIGLIN);
                piglin.setMaxHealth(getHealth());
                piglin.setHealth(piglin.getMaxHealth());
                piglin.setCustomName(ChatColor.RED + getName());
                piglin.setCustomNameVisible(true);
                piglin.getEquipment().setHelmet(ItemFactory.createItem(
                        Material.getMaterial(getMainClazz().getMobConfig().getString("piglin.armorHelmet")), "Ghoul Helmet", null, false));
                piglin.getEquipment().setChestplate(ItemFactory.createItem(
                        Material.getMaterial(getMainClazz().getMobConfig().getString("piglin.armorChestplate")), "Ghoul Chestplate", null, false));
                piglin.getEquipment().setLeggings(ItemFactory.createItem(
                        Material.getMaterial(getMainClazz().getMobConfig().getString("piglin.armorLeggings")), "Ghoul Leggings", null, false));
                piglin.getEquipment().setBoots(ItemFactory.createItem(
                        Material.getMaterial(getMainClazz().getMobConfig().getString("piglin.armorBoots")), "Ghoul Boots", null, false));
                piglin.getEquipment().setItemInHand(ItemFactory.createItem(getMobWeapon(), "Evil Sword", null, false));
                uuid = piglin.getUniqueId();
                break;

            default:
                break;
        }

    }

    @EventHandler
    public void onMobDeath(EntityDeathEvent e) {
    }

}
