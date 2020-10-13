package org.jbltd.mmo.core.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UtilEffect {

    public static void blind(Player player, int seconds) {

        PotionEffect pe = new PotionEffect(PotionEffectType.BLINDNESS, seconds, 1);
        player.addPotionEffect(pe);

    }

    public static void unblind(Player player) {

        PotionEffect pe = new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 1);

        if (player.hasPotionEffect(PotionEffectType.BLINDNESS)) {

            player.getActivePotionEffects().remove(pe);

        } else {
            throw new NullPointerException("Player does not have the specified potion effect.");
        }

    }

    public static void cloak(Player player) {

        for (Player all : Bukkit.getOnlinePlayers()) {

            all.hidePlayer(player);

        }

    }

    public static void uncloak(Player player) {

        for (Player all : Bukkit.getOnlinePlayers()) {

            all.showPlayer(player);

        }

    }

}
