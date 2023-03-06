package me.rpgarnet.utils;

public class TimeUtils {
	
	public static boolean isInCooldown(Long time, int cooldown) {
		return getSecondsLeft(time, cooldown) < 0;
	}
	
	public static long getSecondsLeft(Long time, int cooldown) {
		return (time / 1000) + cooldown - (System.currentTimeMillis()/1000);
	}
	
}
