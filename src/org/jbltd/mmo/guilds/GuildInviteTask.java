package org.jbltd.mmo.guilds;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jbltd.mmo.core.util.F;

public class GuildInviteTask implements Runnable {

    private static int TIMER_DURATION = 300;
    private static int id;
    private final Player _player;
    private final Guild g;
    private final JavaPlugin plugin;
    public boolean respondedToInvite = false;
    public boolean response = false;

    public GuildInviteTask(JavaPlugin plugin, Player player, Guild g) {
        this._player = player;
        this.g = g;
        this.plugin = plugin;

        id = Bukkit.getScheduler().runTaskTimer(this.plugin, this, 1, 20).getTaskId();

        _player.sendMessage(F.info("Guilds", false, g.getGuildLeader().getName() + " has invited you to join " + g.getGuildName()));
        _player.sendMessage(F.info("Guilds", false, "Type either /accept or /deny to respond to this invite."));

    }

    @Override
    public void run() {

        int i = TIMER_DURATION;

        i--;

        TIMER_DURATION = i;

        if (i == 0) {
            _player.sendMessage(F.error("Guilds", "Your invitation from " + g.getGuildName() + " has timed out."));

            stop();
        }

        if (respondedToInvite == false) {

            return;
        }
        if (respondedToInvite == true) {
            try {
                _player.sendMessage(F.info("Guilds", false, "You have " + ((response = true) ? "accepted" : "declined") + " the guild invite from " + g.getGuildName()));

                if (response = true) {
                    g.addMember(_player);

                    g.getGuildMembers().forEach(uuid -> Bukkit.getPlayer(uuid).sendMessage(F.info("Guilds", false, _player.getName() + " has joined your guild.")));
                    InviteCommand.pendingInvitees.remove(_player.getUniqueId());

                    stop();
                }
            } catch (Exception e) {

            }
        }

    }

    private void stop() {
        Bukkit.getScheduler().cancelTask(id);

    }

    public Player getPlayer() {
        return _player;
    }

    public boolean getResponse() {
        return response;
    }

    public boolean hasResponded() {
        return respondedToInvite;
    }

}
