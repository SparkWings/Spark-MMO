package org.jbltd.mmo.core.util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.server.v1_16_R2.IChatBaseComponent;
import net.minecraft.server.v1_16_R2.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class UtilPacket {

    private static JavaPlugin plugin;

    public UtilPacket(JavaPlugin pluginn) {
        plugin = pluginn;
    }

    public static void sendActionBarMessage(Player player, String message) {

        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}");

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, (BaseComponent) cbc);


    }

    public static void sendTitleBarMessage(Player player, String title, String sub) {

        IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
        IChatBaseComponent chatSub = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + sub + "\"}");

        PacketPlayOutTitle pt = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        PacketPlayOutTitle pst = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatSub);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(pt);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(pst);

    }

    public static void sendHealthMessage(Player player) {

        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            @Override
            public void run() {

                sendActionBarMessage(player, ChatColor.RED + "♥: " + player.getHealth());

            }
        }, 0L, 40L);

    }

}
