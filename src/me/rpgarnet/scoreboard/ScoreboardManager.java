package me.rpgarnet.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import me.rpgarnet.PluginViewModel;
import me.rpgarnet.RPGarnet;
import me.rpgarnet.data.PlayerData;
import me.rpgarnet.data.attribute.Stats;
import me.rpgarnet.utils.StringUtils;

public class ScoreboardManager {

	/**
	 * Create a scoreboard for player and adds it to the scoreboard list
	 * @param data - PlayerData to create scoreboard
	 */
    public void createScoreboard(PlayerData data) {
    	Player player = data.getPlayer();
    	
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective(player.getName(), Criteria.DUMMY, StringUtils.colorFixing("&4&l" + player.getName()));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        ScoreboardHandler.addPlayerScoreboard(player, scoreboard);

        Score blankLine = objective.getScore("§0");
        blankLine.setScore(15);
        
        for(Stats s : Stats.values()) {
        	if(scoreboard.getTeam(s.getStatsName()) == null)
        		scoreboard.registerNewTeam(s.getStatsName());
        	if(scoreboard.getTeam("§" + Stats.getIntValue(s)) == null)
        		scoreboard.registerNewTeam("§" + Stats.getIntValue(s));
        	scoreboard.getTeam(s.getStatsName()).addEntry(s.getStatsName());
        	scoreboard.getTeam(s.getStatsName()).setPrefix("§6§l" + s.getIcon() + "§6 ");
        	scoreboard.getTeam(s.getStatsName()).setSuffix("§e: " + "§6" + data.getStats()[Stats.getIntValue(s)].getAttributeValue());
        	objective.getScore(s.getStatsName()).setScore(14 - 2 * Stats.getIntValue(s));
        	scoreboard.getTeam("§" + Stats.getIntValue(s)).addEntry("§" + Stats.getIntValue(s));
        	scoreboard.getTeam("§" + Stats.getIntValue(s)).setSuffix(StringUtils.percentageExp(data, Stats.getIntValue(s)));
        	objective.getScore("§" + Stats.getIntValue(s)).setScore(14 - 2 * Stats.getIntValue(s) - 1);
        }       

        player.setScoreboard(scoreboard);
        ScoreboardHandler.addPlayerScoreboard(player, scoreboard);
        
    }

    public void updateScoreboards() {
    	
    	PluginViewModel viewModel = RPGarnet.instance.getViewModel();
    	
        for (PlayerData data : viewModel.getData()) {

            Scoreboard scoreboard = ScoreboardHandler.getPlayerScoreboard(data.getPlayer());
            if(scoreboard == null) {
            	createScoreboard(data);
            }
            boolean choice = (Math.random() > 0.5);

            for(Stats s : Stats.values()) {
            	if(scoreboard.getTeam(s.getStatsName()) == null)
            		scoreboard.registerNewTeam(s.getStatsName());
            	scoreboard.getTeam(s.getStatsName()).setPrefix("§6§l" + s.getIcon() + "§6 ");
            	scoreboard.getTeam(s.getStatsName()).setSuffix("§e: " + "§6" + data.getStats()[Stats.getIntValue(s)].getAttributeValue());
            	if(choice)
            		scoreboard.getTeam("§" + Stats.getIntValue(s)).setSuffix(StringUtils.percentageExp(data, Stats.getIntValue(s)));
            	else {
            		scoreboard.getTeam("§" + Stats.getIntValue(s)).setSuffix(StringUtils.colorFixing("&fExperience: &e" + 
            	        	data.getStats()[Stats.getIntValue(s)].getExperience() + "&f/&6" + data.getStats()[Stats.getIntValue(s)].getExpToLevel()));
            	}
            }
        }
    }

    public void start() {
    	
        new BukkitRunnable() {

            @Override
            public void run() {
                updateScoreboards();
            }

        }.runTaskTimer(RPGarnet.instance, 0L, 100L);
    }

}
