package me.rpgarnet.utils;

import me.rpgarnet.data.PlayerData;
import me.rpgarnet.data.attribute.Stats;

public class StringUtils {
	
	public static String PREFIX;

	public static String colorFixing(String msg) {
		return msg.replace('&', '§');
	}
	
	public static String capital(String msg) {

		String capital = "";

		while(msg.indexOf(" ") != -1) {

			capital = msg.substring(0, 1).toUpperCase() + msg.substring(1, msg.indexOf(" ") + 1);
			msg = msg.substring(msg.indexOf(" ") + 1);

		}

		capital = msg.substring(0, 1).toUpperCase() + msg.substring(1).toLowerCase();

		return capital;

	}
	
	public static String placeholder(String message, PlayerData data) {
		
		message = message.replaceAll("%PLAYER%", data.getPlayer().getName());
		
		return colorFixing(message);
	}
	
	public static String placeholder(String message, PlayerData data, Stats stats) {
		
		message = message.replaceAll("%PLAYER%", data.getPlayer().getName());
		message = message.replaceAll("%LEVEL%", data.getStats()[stats.getIntValue()].getLevel() + "");
		message = message.replaceAll("%SKILL%", capital(stats.toString()));
		
		return colorFixing(message);
	}
	
	public static String yamlString(String message) {
		return StringUtils.colorFixing(PREFIX + message);
	}
	
	public static String yamlString(String message, PlayerData data) {
		return StringUtils.colorFixing(PREFIX + placeholder(message, data));
	}
	
	public static String voidMessage() {
		return colorFixing("&7");
	}

	public static String yamlString(String string, PlayerData playerData, Stats stats) {
		string = string.replaceAll("%LEVEL%", playerData.getStats()[stats.getIntValue()].getLevel() + "");
		return yamlString(string.replaceAll("%SKILL%", capital(stats.toString())), playerData);
	}

	public static String yamlString(String string, int foodExperience) {
		String food = foodExperience + "";
		
		if(foodExperience > 0)
			string = string.replaceAll("%FOODPOWER%", "§a" + food);
		else if(foodExperience < 0)
			string = string.replaceAll("%FOODPOWER%", "§c" + food);
		else
			string = string.replaceAll("%FOODPOWER%", "§e" + food);
		return yamlString(string);
	}

	public static String percentageExp(PlayerData data, int intValue) {
		
		String identifier = "";
		double percentage = data.getStats()[intValue].getPercentage();
		if(percentage == 0.0)
			identifier =  StringUtils.colorFixing("&c███████████");
		else if(percentage <= 0.1)
			identifier =  StringUtils.colorFixing("&a█&c██████████");
		else if(percentage <= 0.2)
			identifier =  StringUtils.colorFixing("&a██&c█████████");
		else if(percentage <= 0.3)
			identifier =  StringUtils.colorFixing("&a███&c████████");
		else if(percentage <= 0.4)
			identifier =  StringUtils.colorFixing("&a████&c███████");
		else if(percentage <= 0.5)
			identifier =  StringUtils.colorFixing("&a█████&c██████");
		else if(percentage <= 0.6)
			identifier =  StringUtils.colorFixing("&a██████&c█████");
		else if(percentage <= 0.7)
			identifier =  StringUtils.colorFixing("&a███████&c████");
		else if(percentage <= 0.8)
			identifier =  StringUtils.colorFixing("&a█████████&c██");
		else if(percentage <= 0.9)
			identifier =  StringUtils.colorFixing("&a██████████&c█");
		else 
			identifier =  StringUtils.colorFixing("&a███████████");
		
		return identifier;
	}

}