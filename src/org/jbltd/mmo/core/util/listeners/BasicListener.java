package org.jbltd.mmo.core.util.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.jbltd.mmo.core.util.F;
import org.jbltd.mmo.core.util.UpdateEvent;
import org.jbltd.mmo.core.util.UpdateType;
import org.jbltd.mmo.guilds.Guild;

public class BasicListener implements Listener {

    @EventHandler
    public void sendAnnouncement(UpdateEvent e) {
        if (e.getType() != UpdateType.MIN_08)
            return;

        Bukkit.broadcastMessage(F.info("Announcement", false, "Enjoying the game? Tell your friends!"));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage(null);
        Bukkit.getWorld("world").getSpawnLocation();
        e.getPlayer().teleport(Bukkit.getWorld("world").getSpawnLocation());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }

    @EventHandler
    public void handleChat(AsyncPlayerChatEvent e) {

        boolean guild = false;
        String tag = "";

        for (Guild g : Guild.allGuilds) {
            if (g.getGuildMembers().contains(e.getPlayer().getUniqueId())) {
                guild = true;
                tag = g.getGuildTag();
            } else {
                guild = false;
            }
        }

        if (guild == true) {
            e.setFormat(ChatColor.AQUA + tag + ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.GRAY
                    + ": " + ChatColor.WHITE + e.getMessage());
            return;
        }

        e.setFormat(
                ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.GRAY + ": " + ChatColor.WHITE + e.getMessage());
    }

    @EventHandler
    public void cancelWeather(WeatherChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void handleMobSpawn(CreatureSpawnEvent e) {

        if (e.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
            e.setCancelled(true);
            return;
        }

    }

    @EventHandler
    public void setMobNames(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof Player) {
            return;
        }

        LivingEntity en = (LivingEntity) e.getEntity();
        double perc = en.getHealth() / en.getMaxHealth();

        if (perc >= .80) {
            e.getEntity().setCustomName(ChatColor.GREEN + "▌▌▌▌▌");
        }
        if (perc < .80) {

            e.getEntity().setCustomName(ChatColor.GREEN + "▌▌▌▌" + ChatColor.RED + "▌");
        }
        if (perc < .60) {

            e.getEntity().setCustomName(ChatColor.GREEN + "▌▌▌" + ChatColor.RED + "▌▌");
        }
        if (perc < .40) {

            e.getEntity().setCustomName(ChatColor.GREEN + "▌▌" + ChatColor.RED + "▌▌▌");
        }
        if (perc < .20) {

            e.getEntity().setCustomName(ChatColor.GREEN + "▌" + ChatColor.RED + "▌▌▌▌");
        }

    }

    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {
        if (event.getBlock().getType() == Material.DIRT && event.getEntity() instanceof Creature)
            event.setCancelled(true);
    }

}
