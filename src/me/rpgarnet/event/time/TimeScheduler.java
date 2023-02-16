package me.rpgarnet.event.time;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import me.rpgarnet.RPGarnet;
import me.rpgarnet.utils.StringUtils;

public class TimeScheduler {

	private BukkitScheduler scheduler;
	private double timeSpeed;
	private boolean bloodMoon;
	private World world = Bukkit.getWorld("world");

	public TimeScheduler(double timeSpeed) {

		this.setTimeSpeed(timeSpeed);
		scheduler = Bukkit.getScheduler();
		world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
		bloodMoon = false;

		scheduler.scheduleSyncRepeatingTask(RPGarnet.instance, new Runnable() {

			public void run() {

				world.setTime(world.getTime() + 250);
				if(world.getTime() >= 12000 && world.getDifficulty() != Difficulty.HARD) {
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', StringUtils.PREFIX + " &7Le tenebre sorgono... Difficoltá impostata a &cDifficile"));
					world.setDifficulty(Difficulty.HARD);
					if(!bloodMoon)
						bloodMoon = (Math.random() <= 0.05);
					if(bloodMoon)
						for(Player p : Bukkit.getOnlinePlayers())
							p.sendTitle(StringUtils.colorFixing("&4&lBLOOD MOON"), StringUtils.colorFixing("&cI mob aggressivi saranno molto piú forti!"), 10, 100, 20);
				}
				else if(world.getTime()>= 0 && world.getTime() < 12000 && world.getDifficulty() != Difficulty.NORMAL) {
					Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', StringUtils.PREFIX + " &7Il sole illumina il tuo viso... Difficoltá impostata a &aNormale"));
					world.setDifficulty(Difficulty.NORMAL);
					bloodMoon = false;
				}

			}

		}, 0L, (long) (250L*timeSpeed));

	}

	public boolean isDay() {
		return (world.getTime() >= 12000) ? false : true;
	}

	public BukkitScheduler getScheduler() {
		return scheduler;
	}
	public void setScheduler(BukkitScheduler scheduler) {
		this.scheduler = scheduler;
	}
	public double getTimeSpeed() {
		return timeSpeed;
	}
	public void setTimeSpeed(double timeSpeed) {
		this.timeSpeed = timeSpeed;
	}
	public boolean isBloodMoon() {
		return bloodMoon;
	}
	public void setBloodMoon(boolean bloodMoon) {
		this.bloodMoon = bloodMoon;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

}
