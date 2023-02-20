package me.rpgarnet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.rpgarnet.data.PlayerData;
import me.rpgarnet.data.attribute.Statistic;
import me.rpgarnet.data.attribute.Stats;
import me.rpgarnet.event.time.TimeScheduler;
import me.rpgarnet.scoreboard.ScoreboardManager;
import me.rpgarnet.utils.StringUtils;

public class PluginViewModel {

	private RPGarnet instance;

	private File configF;	
	private FileConfiguration config;
	private File messageF;
	private FileConfiguration message;
	private File playerF;
	private FileConfiguration player;
	private File homeF;
	private FileConfiguration home;

	private List<PlayerData> data;
	private List<Player> afks;

	private TimeScheduler timeSchedule;

	private ScoreboardManager scoreboard;

	public PluginViewModel() {

		instance = RPGarnet.instance;
		data = new ArrayList<>();
		afks = new ArrayList<>();
		loadFiles();
		StringUtils.PREFIX = message.getString("prefix");
		timeSchedule = new TimeScheduler(1);
		scoreboard = new ScoreboardManager();

	}

	public void loadFiles() {

		configF = new File(instance.getDataFolder(), "config.yml");
		messageF = new File(instance.getDataFolder(), "message.yml");
		playerF = new File(instance.getDataFolder(), "player.yml");
		homeF = new File(instance.getDataFolder(), "home.yml");

		if(!configF.exists()) {
			configF.getParentFile().mkdirs();
			instance.saveResource("config.yml", false);
		}

		if(!messageF.exists()) {
			messageF.getParentFile().mkdirs();
			instance.saveResource("message.yml", false);
		}

		if(!playerF.exists()) {
			playerF.getParentFile().mkdirs();
			instance.saveResource("player.yml", false);
		}
		
		if(!homeF.exists()) {
			homeF.getParentFile().mkdirs();
			instance.saveResource("home.yml", false);
		}

		config = YamlConfiguration.loadConfiguration(configF);
		message = YamlConfiguration.loadConfiguration(messageF);
		player = YamlConfiguration.loadConfiguration(playerF);
		home = YamlConfiguration.loadConfiguration(homeF);

	}

	/**
	 * Checks if there are player data on player.yml file
	 * @param player - The player to be checked
	 * @return - True if player is found - False if not found
	 */
	public boolean isPlayerRegistered(Player player) {
		return this.player.contains(player.getName());
	}

	/**
	 * Create new player in player.yml, saves his name, UUID and the base stats
	 * @param player
	 */
	public PlayerData registerNewPlayer(Player player) {

		this.player.set(player.getName() + ".UUID", player.getUniqueId().toString());
		try {
			this.player.save(playerF);
		} catch (IOException e) {
			e.printStackTrace();
		}

		PlayerData playerData = new PlayerData(player);
		savePlayerData(playerData);

		return playerData;
	}

	/**
	 * Saves in player.yml the actual player data
	 * @param playerData
	 */
	public void savePlayerData(PlayerData playerData) {

		new BukkitRunnable() {

			@Override
			public void run() {

				for(int i = 0; i < playerData.getStats().length; i++) {
					player.set(playerData.getPlayer().getName() + ".stats." + Stats.getStats(i).toString() + ".level", playerData.getStats()[i].getLevel());
					player.set(playerData.getPlayer().getName() + ".stats." + Stats.getStats(i).toString() + ".experience", playerData.getStats()[i].getExperience());
					try {
						player.save(playerF);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}				

			}

		}.runTaskAsynchronously(RPGarnet.instance);

	}

	/**
	 * Retrives player data from plugin.yml and stores it into PlayerData
	 * @param player
	 * @return PlayerData object
	 */
	public PlayerData getPlayerData(Player player) {

		if(player.isOnline())
			return searchPlayerData(player);

		PlayerData data;
		Statistic[] stats = new Statistic[PlayerData.STATS_NUMBER];
		for(int i = 0; i < PlayerData.STATS_NUMBER; i++) {
			stats[i] = Stats.getStatistic(Stats.getStats(i), player, 
					this.player.getInt(player.getName() + ".stats." + Stats.getStats(i).toString().toLowerCase() + ".experience"), 
					this.player.getInt(player.getName() + ".stats." + Stats.getStats(i).toString().toLowerCase() + ".level"));
		}
		data = new PlayerData(player, stats);

		return data;

	}

	/**
	 * Retrives PlayerData from player.yml file and returns it.
	 * @param player - player to look for data
	 * @return player data if found
	 */
	public PlayerData loadPlayerData(Player player) {

		PlayerData data;
		if(!isPlayerRegistered(player))
			return null;

		Statistic[] stats = new Statistic[PlayerData.STATS_NUMBER];
		for(int i = 0; i < PlayerData.STATS_NUMBER; i++) {
			stats[i] = Stats.getStatistic(Stats.getStats(i), player, 
					this.player.getInt(player.getName() + ".stats." + Stats.getStats(i).toString().toLowerCase() + ".experience"), 
					this.player.getInt(player.getName() + ".stats." + Stats.getStats(i).toString().toLowerCase() + ".level"));
		}
		data = new PlayerData(player, stats);

		return data;

	}

	/**
	 * Saves all PlayerData in player.yml file.
	 * Task is asynchronously
	 */
	public void saveAllData() {

		for(PlayerData d : data) {

			for(int i = 0; i < d.getStats().length; i++) {
				player.set(d.getPlayer().getName() + ".stats." + Stats.getStats(i).toString() + ".level", d.getStats()[i].getLevel());
				player.set(d.getPlayer().getName() + ".stats." + Stats.getStats(i).toString() + ".experience", d.getStats()[i].getExperience());
				try {
					player.save(playerF);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}

	}

	/**
	 * Adds player to the list of online players if not already exists
	 * @param data - Player data to be added
	 * @return True if is added - False otherwise
	 */
	public boolean addPlayer(PlayerData data) {
		if(this.data.contains(data))
			return false;
		return this.data.add(data);
	}

	/**
	 * Remove player from the list of online players if exists
	 * @param player - Player to remove from list
	 * @return PlayerData
	 */
	public PlayerData removePlayer(Player player) {
		for(PlayerData d : data) {
			if(player.getName().equals(d.getPlayer().getName()))
				data.remove(d);
			return d;
		}
		return null;
	}

	/**
	 * Retrives PlayerData from server rams
	 * @param player - Player to look for data
	 * @return PlayerData if found - null otherwise
	 */
	public PlayerData searchPlayerData(Player player) {
		for(PlayerData d : data)
			if(d.getPlayer().equals(player))
				return d;
		return null;
	}

	/**
	 * Add player to the list of AFK players
	 * @param player - Player to add
	 * @return true if it has been added, false if already in afk list
	 */
	public boolean addAfkPlayer(Player player) {
		if(isAfk(player))
			return false;
		afks.add(player);
		return true;
	}

	/**
	 * Remove player from list of AFK players
	 * @param player - Player to remove
	 * @return true if it has been removed, false if not found
	 */
	public boolean removeAfkPlayer(Player player) {
		if(!isAfk(player))
			return false;
		afks.remove(player);
		return true;
	}

	/**
	 * Adds player in AFK list if not in it, remove if player is in
	 * @param player - Player to add/remove
	 * @return true if it has added, false if it has removed
	 */
	public boolean switchAfk(Player player) {
		if(isAfk(player)) {
			afks.remove(player);
			return false;
		}
		afks.add(player);
		return true;
	}

	/**
	 * Checks if player is AFK
	 * @param player
	 * @return true if is afk
	 */
	public boolean isAfk(Player player) {
		return afks.contains(player);
	}
	
	public void addHome(Player player, Location location) {
		
		home.set(player.getName() + ".world", location.getWorld().getName());
		home.set(player.getName() + ".X", location.getBlockX());
		home.set(player.getName() + ".Y", location.getBlockY());
		home.set(player.getName() + ".Z", location.getBlockZ());
		home.set(player.getName() + ".yaw", location.getYaw());
		home.set(player.getName() + ".pitch", location.getPitch());
		try {
			home.save(homeF);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public Location getHome(Player player) {
		if(home.getConfigurationSection(player.getName()) == null)
			return null;
		ConfigurationSection loc = home.getConfigurationSection(player.getName());
		World world = Bukkit.getWorld(loc.getString("world"));
		int x = loc.getInt("X");
		int y = loc.getInt("Y");
		int z = loc.getInt("Z");
		float yaw = (float) loc.getDouble("yaw");
		float pitch = (float) loc.getDouble("pitch");
		
		return new Location(world, x + 0.5, y, z + 0.5, yaw, pitch);
	}

	public FileConfiguration getConfig() {
		return config;
	}
	public FileConfiguration getMessage() {
		return message;
	}
	public FileConfiguration getPlayer() {
		return player;
	}
	public FileConfiguration getHome() {
		return home;
	}

	public TimeScheduler getTimeSchedule() {
		return timeSchedule;
	}

	public ScoreboardManager getScoreboard() {
		return scoreboard;
	}

	public List<Player> getAfks() {
		return afks;
	}

	public List<PlayerData> getData() {
		return data;
	}

}
