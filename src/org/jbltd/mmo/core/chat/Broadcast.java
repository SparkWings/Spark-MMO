package org.jbltd.mmo.core.chat;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jbltd.mmo.core.util.F;

public class Broadcast implements CommandExecutor {


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player))
            return true;

        if (cmd.getName().equalsIgnoreCase("broadcast")) {

            Player player = (Player) sender;

            if (!player.isOp()) {
                return true;
            }

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]);
            }

            String finals = sb.toString().trim();

            for (Player all : Bukkit.getOnlinePlayers()) {

                all.sendMessage(F.WHITE + F.BOLD + player.getName() + " - " + F.AQUA + finals);
                all.playSound(all.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10F, 1F);

            }
            return false;

        }

        return false;

    }

}
