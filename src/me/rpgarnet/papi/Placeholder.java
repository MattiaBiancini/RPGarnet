package me.rpgarnet.papi;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.rpgarnet.RPGarnet;
import me.rpgarnet.data.PlayerData;
import me.rpgarnet.data.attribute.Statistic;
import me.rpgarnet.data.attribute.Stats;
import me.rpgarnet.utils.StringUtils;

public class Placeholder extends PlaceholderExpansion {
	
	@Override
	public String getAuthor() {
		return "Garnet14";
	}

	@Override
	public String getIdentifier() {
		// TODO Auto-generated method stub
		return "rpgarnet";
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return getClass().getPackage().getImplementationVersion();
	}
	
	@Override
	public boolean canRegister(){
		return true;
	}

	@Override
    public String onRequest(OfflinePlayer player, String params) {
		String string = params;
        Player onlinePlayer = Bukkit.getPlayer(player.getName());
        PlayerData data;
		if(onlinePlayer != null) {
			data = RPGarnet.instance.getViewModel().getPlayerData(onlinePlayer);
		}
		else {
			data = RPGarnet.instance.getViewModel().loadOfflinePlayerData(player);
		}
		if(data == null)
			return string;
		
		if(params.indexOf("_") == -1)
			return string;
		
		Stats stats = Stats.getStats(params.substring(0, params.indexOf("_")));
		if(stats == null)
			return string;
		
		Statistic statistic = data.getStats()[stats.getIntValue()];
		
		if(params.equalsIgnoreCase(stats.toString() + "_level"))
			return statistic.getLevel() + "";
		if(params.equalsIgnoreCase(stats.toString() + "_max_level"))
			return statistic.getMaxLevel() + "";
		if(params.equalsIgnoreCase(stats.toString() + "_experience"))
			return statistic.getExperience() + "";
		if(params.equalsIgnoreCase(stats.toString() + "_exp_to_level"))
			return statistic.getExpToLevel() + "";
		if(params.equalsIgnoreCase(stats.toString() + "_attribute"))
			return statistic.getAttributeValue() + "";
		if(params.equalsIgnoreCase(stats.toString() + "_rate"))
			return statistic.getRate() + "";
		if(params.equalsIgnoreCase(stats.toString() + "_exp_bar")) {
			String identifier = "";
			double percentage = statistic.getPercentage();
			if(percentage <= 0.05)
				identifier =  StringUtils.colorFixing("&c███████████");
			else if(percentage <= 0.15)
				identifier =  StringUtils.colorFixing("&a█&c██████████");
			else if(percentage <= 0.25)
				identifier =  StringUtils.colorFixing("&a██&c█████████");
			else if(percentage <= 0.35)
				identifier =  StringUtils.colorFixing("&a███&c████████");
			else if(percentage <= 0.45)
				identifier =  StringUtils.colorFixing("&a████&c███████");
			else if(percentage <= 0.55)
				identifier =  StringUtils.colorFixing("&a█████&c██████");
			else if(percentage <= 0.65)
				identifier =  StringUtils.colorFixing("&a██████&c█████");
			else if(percentage <= 0.75)
				identifier =  StringUtils.colorFixing("&a███████&c████");
			else if(percentage <= 0.85)
				identifier =  StringUtils.colorFixing("&a█████████&c██");
			else if(percentage <= 0.95)
				identifier =  StringUtils.colorFixing("&a██████████&c█");
			else 
				identifier =  StringUtils.colorFixing("&a███████████");
			
			return identifier;
		}
        
        return string;
    }

}
