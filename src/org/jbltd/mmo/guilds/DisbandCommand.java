package org.jbltd.mmo.guilds;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jbltd.mmo.core.util.F;

public class DisbandCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player))
            return true;

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("gdisband")) {
            Guild guild = null;

            for (Guild g : Guild.allGuilds) {
                if (g.getGuildMembers().contains(player.getUniqueId())) {
                    guild = g;
                } else
                    continue;

            }

            if (guild == null) {
                player.sendMessage(F.error("Guilds", "You are not in a guild."));
                return true;
            }

            if (guild.getGuildLeader() != player) {
                player.sendMessage(F.error("Guilds", "You are not the guild leader."));
                return true;
            }

            Guild.allGuilds.remove(guild);
            guild.getGuildMembers().clear();
            Bukkit.broadcastMessage(F.info("Guilds", false, player.getName() + " disbanded the guild " + guild.getGuildName()));
        }

        return false;
    }

}
