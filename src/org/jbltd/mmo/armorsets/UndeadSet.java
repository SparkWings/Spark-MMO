package org.jbltd.mmo.armorsets;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.jbltd.mmo.core.util.F;
import org.jbltd.mmo.core.util.ItemFactory;

public class UndeadSet extends ArmorSet {

    private static final ItemStack HELMET = ItemFactory.createItem(Material.CHAINMAIL_HELMET,
	    F.DARK_PURPLE + "Undead Helmet",
	    Arrays.asList(F.GRAY + "A helmet forged in darkness",
		    F.GRAY + "Some say that whoever wears it will never feel pain",
		    F.GRAY + "of the living world again", "", F.RED + "Undead Set"),
	    true);

    private static final ItemStack CHEST = ItemFactory.createItem(Material.CHAINMAIL_CHESTPLATE,
	    F.DARK_PURPLE + "Undead Chestplate",
	    Arrays.asList(F.GRAY + "A chestplate forged in darkness",
		    F.GRAY + "Some say that whoever wears it will never feel pain",
		    F.GRAY + "of the living world again", "", F.RED + "Undead Set"),
	    true);

    private static final ItemStack PANTS = ItemFactory.createItem(Material.CHAINMAIL_LEGGINGS,
	    F.DARK_PURPLE + "Undead Leggings",
	    Arrays.asList(F.GRAY + "A helmet forged in darkness",
		    F.GRAY + "Some say that whoever wears it will never feel pain",
		    F.GRAY + "of the living world again", "", F.RED + "Undead Set"),
	    true);

    private static final ItemStack BOOTS = ItemFactory.createItem(Material.CHAINMAIL_BOOTS,
	    F.DARK_PURPLE + "Undead Boots",
	    Arrays.asList(F.GRAY + "A helmet forged in darkness",
		    F.GRAY + "Some say that whoever wears it will never feel pain",
		    F.GRAY + "of the living world again", "", F.RED + "Undead Set"),
	    true);

    public UndeadSet() {
	super(HELMET, CHEST, PANTS, BOOTS);
	
    }

    @EventHandler
    public void listen(InventoryClickEvent e) {
	
	Player p = (Player) e.getWhoClicked();
	
	if(e.getSlotType() == InventoryType.SlotType.ARMOR)
	{
	    if(p.getInventory().getHelmet() == HELMET)
	    {
		if(p.getInventory().getChestplate() == CHEST)
		{
		    if(p.getInventory().getLeggings() == PANTS)
		    {
			if(p.getInventory().getBoots() == BOOTS)
			{
			    System.out.println("Set equipped.");
			}
		    }  
		}
	    }
	}
	else return;
	
    }

}
