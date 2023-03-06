package me.rpgarnet.scoreboard;

import java.text.DecimalFormat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import me.rpgarnet.PluginViewModel;
import me.rpgarnet.RPGarnet;
import me.rpgarnet.data.PlayerData;
import me.rpgarnet.data.attribute.Stats;
import me.rpgarnet.utils.StringUtils;

public class ScoreboardManager {
	
	private static final DecimalFormat DF = new DecimalFormat("0.000");

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

        setNewScoreboard(data, scoreboard, objective);
        
        player.setScoreboard(scoreboard);
        ScoreboardHandler.addPlayerScoreboard(player, scoreboard);
        
    }

    public void updateScoreboards() {
    	
    	PluginViewModel viewModel = RPGarnet.instance.getViewModel();
    	
        for (PlayerData data : viewModel.getData()) {
        	if(data.getPlayer() == null)
        		continue;
        	
            Scoreboard scoreboard = ScoreboardHandler.getPlayerScoreboard(data.getPlayer());
            if(scoreboard == null) {
            	createScoreboard(data);
            }

            updateScoreboard(data, scoreboard);
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
    
    private void setNewScoreboard(PlayerData data, Scoreboard scoreboard, Objective objective) {
    	
    	for(Stats s : Stats.values()) {
    		setEntry(data, scoreboard, s);
        	objective.getScore(s.getStatsName()).setScore(14 - Stats.getIntValue(s));
        }
    	
    }
    
    private void updateScoreboard(PlayerData data, Scoreboard scoreboard) {
    	
    	for(Stats s : Stats.values()) {
    		setEntry(data, scoreboard, s);
        }
    	
    }
    
    private void setEntry(PlayerData data, Scoreboard scoreboard, Stats s) {
    	
    	if(scoreboard.getTeam(s.getStatsName()) == null)
    		scoreboard.registerNewTeam(s.getStatsName());
    	scoreboard.getTeam(s.getStatsName()).addEntry(s.getStatsName());
    	scoreboard.getTeam(s.getStatsName()).setPrefix("§6§l" + s.getIcon() + "§6 ");
    	scoreboard.getTeam(s.getStatsName()).setSuffix("§e: " + "§6" + DF.format(data.getStats()[Stats.getIntValue(s)].getAttributeValue()));
    	
    }

}
