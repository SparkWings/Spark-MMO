package org.jbltd.mmo.armorsets;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.jbltd.mmo.core.util.F;
import org.jbltd.mmo.core.util.ItemFactory;
import org.jbltd.mmo.core.util.UpdateEvent;
import org.jbltd.mmo.core.util.UpdateType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class UndeadSet extends ArmorSet {

    private static final ItemStack HELMET = ItemFactory.createItem(Material.CHAINMAIL_HELMET, F.DARK_PURPLE + "Undead Helmet", Arrays.asList(F.GRAY + "A helmet forged in darkness",
            F.GRAY + "Some say that whoever wears it will never feel pain", F.GRAY + "of the living world again", "", F.RED + "Undead Set"), true);

    private static final ItemStack CHEST = ItemFactory.createItem(Material.CHAINMAIL_CHESTPLATE, F.DARK_PURPLE + "Undead Chestplate",
            Arrays.asList(F.GRAY + "A chestplate forged in darkness", F.GRAY + "Some say that whoever wears it will never feel pain", F.GRAY + "of the living world again", "",
                    F.RED + "Undead Set"),
            true);

    private static final ItemStack PANTS = ItemFactory.createItem(Material.CHAINMAIL_LEGGINGS, F.DARK_PURPLE + "Undead Leggings",
            Arrays.asList(F.GRAY + "A helmet forged in darkness", F.GRAY + "Some say that whoever wears it will never feel pain", F.GRAY + "of the living world again", "",
                    F.RED + "Undead Set"),
            true);

    private static final ItemStack BOOTS = ItemFactory.createItem(Material.CHAINMAIL_BOOTS, F.DARK_PURPLE + "Undead Boots", Arrays.asList(F.GRAY + "A helmet forged in darkness",
            F.GRAY + "Some say that whoever wears it will never feel pain", F.GRAY + "of the living world again", "", F.RED + "Undead Set"), true);

    private final List<UUID> players = new ArrayList<UUID>();

    public UndeadSet() {
        super(HELMET, CHEST, PANTS, BOOTS);

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

        e.getPlayer().getInventory().addItem(HELMET);
        e.getPlayer().getInventory().addItem(CHEST);
        e.getPlayer().getInventory().addItem(PANTS);
        e.getPlayer().getInventory().addItem(BOOTS);

    }

    @EventHandler
    public void listen(UpdateEvent e) {

        if (e.getType() != UpdateType.FASTEST)
            return;

        for (Player p : Bukkit.getOnlinePlayers()) {

            if (p.getInventory().getHelmet() == null) {
                p.setMaxHealth(20.0D);
                return;
            }
            if (p.getInventory().getChestplate() == null) {
                p.setMaxHealth(20.0D);
                return;
            }
            if (p.getInventory().getLeggings() == null) {
                p.setMaxHealth(20.0D);
                return;
            }
            if (p.getInventory().getBoots() == null) {
                p.setMaxHealth(20.0D);
                return;
            }

            if (p.getInventory().getHelmet().getType() == Material.CHAINMAIL_HELMET) {
                if (p.getInventory().getChestplate().getType() == Material.CHAINMAIL_CHESTPLATE) {
                    if (p.getInventory().getLeggings().getType() == Material.CHAINMAIL_LEGGINGS) {
                        if (p.getInventory().getBoots().getType() == Material.CHAINMAIL_BOOTS) {
                            p.setMaxHealth(30.0D);

                            if (!players.contains(p.getUniqueId())) {
                                players.add(p.getUniqueId());
                                p.sendMessage(F.info("Armor", false, "You equipped the " + F.GOLD + "Undead Set"));
                            }

                        }
                    }
                }
            } else {
                p.setMaxHealth(20.0D);

                if (players.contains(p.getUniqueId())) {
                    players.remove(p.getUniqueId());
                    p.sendMessage(F.info("Armor", false, "You took off the " + F.GOLD + "Undead Set"));
                }

            }

        }

    }

}
