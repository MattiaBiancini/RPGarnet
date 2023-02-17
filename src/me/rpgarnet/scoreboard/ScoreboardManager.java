package me.rpgarnet.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import me.rpgarnet.RPGarnet;
import me.rpgarnet.data.PlayerData;
import me.rpgarnet.data.attribute.Stats;
import me.rpgarnet.utils.StringUtils;

public class ScoreboardManager {

	private final ScoreboardHandler map = new ScoreboardHandler();

    public void createScoreboard(PlayerData data) {
    	Player player = data.getPlayer();
    	
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(player.getName(), Criteria.DUMMY, StringUtils.yamlString("&4&l" + player.getName(), data));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        map.addPlayerScoreboard(player, scoreboard);

        Score blankLine = objective.getScore("§0");
        blankLine.setScore(15);
        
        for(Stats s : Stats.values()) {
        	if(scoreboard.getTeam(s.getStatsName()) == null)
        		scoreboard.registerNewTeam(s.getStatsName());
        	scoreboard.getTeam(s.getStatsName()).addEntry(s.getStatsName());
        	scoreboard.getTeam(s.getStatsName()).setPrefix("§6§l" + s.getIcon() + "§6 ");
        	scoreboard.getTeam(s.getStatsName()).setSuffix("§e: " + "§7" + data.getStats()[Stats.getIntValue(s)].getAttributeValue());
        	objective.getScore(s.getStatsName()).setScore(15 - Stats.getIntValue(s));
        }       

        map.addPlayerScoreboard(player, scoreboard);
        player.setScoreboard(scoreboard);
    }

    public void updateScoreboards() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            Scoreboard scoreboard = map.getPlayerScoreboard(player);
            PlayerData data = RPGarnet.instance.getViewModel().getPlayerData(player);

            for(Stats s : Stats.values()) {
            	if(scoreboard.getTeam(s.getStatsName()) == null)
            		scoreboard.registerNewTeam(s.getStatsName());
            	scoreboard.getTeam(s.getStatsName()).setPrefix("§6§l" + s.getIcon() + "§6 ");
            	scoreboard.getTeam(s.getStatsName()).setSuffix("§e: " + "§7" + data.getStats()[Stats.getIntValue(s)].getAttributeValue());
            }
        }
    }

    public void start() {
    	
        new BukkitRunnable() {

            @Override
            public void run() {
                updateScoreboards();
            }

        }.runTaskTimer(RPGarnet.instance, 0L, 5L);
    }

}
