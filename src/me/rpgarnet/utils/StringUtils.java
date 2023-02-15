package me.rpgarnet.utils;

public class StringUtils {

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

}