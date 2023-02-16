package me.rpgarnet.utils;

import me.rpgarnet.data.PlayerData;

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
		
		message = message.replaceAll("{PLAYER}", data.getPlayer().getName());
		
		return message;
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

}