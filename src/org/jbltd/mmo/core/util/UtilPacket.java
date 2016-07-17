package org.jbltd.mmo.core.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;

public class UtilPacket {

    private static JavaPlugin plugin;
    
    public UtilPacket(JavaPlugin pluginn)
    {
	plugin = pluginn;
    }
    
    public static void sendActionBarMessage(Player player, String message) {

	IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + message + "\"}");

	PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte) 2);
	((CraftPlayer) player).getHandle().playerConnection.sendPacket(ppoc);

    }

    public static void sendTitleBarMessage(Player player, String title, String sub) {

	IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + title + "\"}");
	IChatBaseComponent chatSub = ChatSerializer.a("{\"text\": \"" + sub + "\"}");

	PacketPlayOutTitle pt = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
	PacketPlayOutTitle pst = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSub);

	((CraftPlayer) player).getHandle().playerConnection.sendPacket(pt);
	((CraftPlayer) player).getHandle().playerConnection.sendPacket(pst);

    }

    public static void sendHealthMessage(Player player)
    {
	
	Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
	    
	    @Override
	    public void run() {
		
		sendActionBarMessage(player, ChatColor.RED+"♥: "+ player.getHealth());
		
	    }
	}, 0L, 40L);
	
    }
    
}
