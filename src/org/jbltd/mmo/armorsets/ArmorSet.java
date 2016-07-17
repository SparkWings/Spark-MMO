package org.jbltd.mmo.armorsets;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class ArmorSet implements Listener {

    private ItemStack helmet, chest, pants, boots;

    public ArmorSet(ItemStack helmet, ItemStack chest, ItemStack pants, ItemStack boots) {
	this.helmet = helmet;
	this.chest = chest;
	this.pants = pants;
	this.boots = boots;
	
	
    }

    public ItemStack getSetHelmet() {
	return helmet;
    }

    public ItemStack getSetChestplate() {
	return chest;
    }

    public ItemStack getSetPants() {
	return pants;

    }

    public ItemStack getSetBoots() {
	return boots;
    }

    public abstract void listen(InventoryClickEvent e);

}
