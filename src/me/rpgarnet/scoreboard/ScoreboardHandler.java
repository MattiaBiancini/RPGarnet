package me.rpgarnet.scoreboard;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardHandler {
	
	public static Map<Player, Scoreboard> playerScoreboards = new HashMap<>();

    public static boolean checkPlayerScoreboard(Player player) {
        if (playerScoreboards.containsKey(player)) {
            return true;
        }
        return false;
    }

    public static void addPlayerScoreboard(Player player, Scoreboard scoreboard) {
        if (checkPlayerScoreboard(player)) {
            playerScoreboards.remove(player);
        }
        playerScoreboards.put(player, scoreboard);

    }

    public static void removePlayerScoreboard(Player player) {
        if (checkPlayerScoreboard(player)) {
            playerScoreboards.remove(player);
        }
    }

    public static Scoreboard getPlayerScoreboard(Player player) {
        if (!checkPlayerScoreboard(player)) {
            Bukkit.getServer().getConsoleSender()
                    .sendMessage("§cThere was an error getting " + player.getName() + "'s scoreboard!");
            return null;
        }
        return playerScoreboards.get(player);
    }

}
