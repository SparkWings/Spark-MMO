package org.jbltd.mmo.core.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jbltd.mmo.core.util.F;

public class ChatClear implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player))
            return true;

        Player player = (Player) sender;


        if (args.length < 2)
            return true;

        if (!player.isOp()) {
            player.sendMessage("You don't have permission to do that!");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("chat") && args[0].equals("clear")) {

            if (args[1].equalsIgnoreCase("-n")) {

                for (int i = 0; i < 150; i++) {
                    for (Player allP : Bukkit.getOnlinePlayers()) {
                        allP.sendMessage("   \n");
                    }

                }

                Bukkit.broadcastMessage(F.info("ChatManager", false, "The chat was cleared by " + player.getName()));

            }

            if (args[1].equalsIgnoreCase("-s")) {

                for (int i = 0; i < 50; i++) {
                    for (Player allP : Bukkit.getOnlinePlayers()) {
                        allP.sendMessage("   \n");
                    }

                }

            }

            if (args[1].equalsIgnoreCase("-a")) {

                for (int i = 0; i < 50; i++) {
                    for (Player allP : Bukkit.getOnlinePlayers()) {
                        allP.sendMessage("   \n");
                    }

                }

                Bukkit.broadcastMessage(F.info("ChatManager", false, "The chat was cleared."));

            }

        }

        return false;
    }

}
