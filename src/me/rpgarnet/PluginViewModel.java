package me.rpgarnet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.rpgarnet.data.PlayerData;
import me.rpgarnet.data.attribute.CustomStatistic;
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
	private File playerDataFolder;

	private List<PlayerData> data;
	private List<Player> afks;
	
	private TimeScheduler timeSchedule;
	private ScoreboardManager scoreboard;

	public PluginViewModel() {

		instance = RPGarnet.instance;
		data = new ArrayList<>();
		afks = new ArrayList<>();
		loadFiles();
		checkUpdates();
		StringUtils.PREFIX = message.getString("prefix");
		if(config.getBoolean("time-scheduler"))
			timeSchedule = new TimeScheduler(1);
		if(config.getBoolean("scoreboard"))
			scoreboard = new ScoreboardManager();

	}

	public void loadFiles() {

		playerDataFolder = new File(instance.getDataFolder(), "PlayerData");
		if (!playerDataFolder.exists()) {
			playerDataFolder.mkdir();
		}

		configF = new File(instance.getDataFolder(), "config.yml");
		messageF = new File(instance.getDataFolder(), "message.yml");

		if(!configF.exists()) {
			configF.getParentFile().mkdirs();
			instance.saveResource("config.yml", false);
		}

		if(!messageF.exists()) {
			messageF.getParentFile().mkdirs();
			instance.saveResource("message.yml", false);
		}

		config = YamlConfiguration.loadConfiguration(configF);
		message = YamlConfiguration.loadConfiguration(messageF);

	}
	
	private void checkUpdates() {
		
		if(config.getString("version") == null || !config.getString("version").equalsIgnoreCase("1.0.0") ) {
			config.set("version", RPGarnet.instance.getDescription().getVersion());
			try {
				config.save(configF);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			message.addDefault("dodge", "&7You dodged the attack, good job!");
			
			try {
				message.save(messageF);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			for(PlayerData playerData : data) {
				File playerFile = new File(playerDataFolder, playerData.getPlayer().getName() + ".yml");
	            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

	                playerConfig.set("stats.evasion.level", 0);
	                playerConfig.set("stats.evasion.experience", 0);
	                
	            try {
	                playerConfig.save(playerFile);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
			}

			
		}
		
	}

	/**
	 * Checks if there are player data on player.yml file
	 * @param player - The player to be checked
	 * @return - True if player is found - False if not found
	 */
	public boolean isPlayerRegistered(Player player) {
		File playerFile = new File(playerDataFolder, player.getName() + ".yml");
		return playerFile.exists();
	}

	/**
	 * Create new player in player.yml, saves his name, UUID and the base stats
	 * @param player
	 */
	public PlayerData registerNewPlayer(Player player) {

		File playerFile = new File(playerDataFolder, player.getName() + ".yml");
		if(!playerFile.exists())
			try {
				playerFile.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

		playerConfig.set("UUID", player.getUniqueId().toString());
		try {
			playerConfig.save(playerFile);
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

				File playerFile = new File(playerDataFolder, playerData.getPlayer().getName() + ".yml");
	            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

	            for (int i = 0; i < playerData.getStats().length; i++) {
	                playerConfig.set("stats." + Stats.getStats(i).toString() + ".level", playerData.getStats()[i].getLevel());
	                playerConfig.set("stats." + Stats.getStats(i).toString() + ".experience", playerData.getStats()[i].getExperience());
	            }
	            try {
	                playerConfig.save(playerFile);
	            } catch (IOException e) {
	                e.printStackTrace();
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

		PlayerData data = null;
		
		if(player.isOnline())
			data = searchPlayerData(player);
		
		if(data != null)
			return data;

		File playerFile = new File(playerDataFolder, player.getName() + ".yml");
	    if (!playerFile.exists()) {
	        return null;
	    }
	    FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
		
		
		CustomStatistic[] stats = new CustomStatistic[PlayerData.STATS_NUMBER];
		for(int i = 0; i < PlayerData.STATS_NUMBER; i++) {
			stats[i] = Stats.getStatistic(Stats.getStats(i), player, 
					playerConfig.getInt("stats." + Stats.getStats(i).toString().toLowerCase() + ".experience"), 
					playerConfig.getInt("stats." + Stats.getStats(i).toString().toLowerCase() + ".level"));
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

		if(!isPlayerRegistered(player))
			return null;

		PlayerData data;
		File playerFile = new File(playerDataFolder, player.getName() + ".yml");
	    if (!playerFile.exists()) {
	        return null;
	    }
	    FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);
		
		
	    CustomStatistic[] stats = new CustomStatistic[PlayerData.STATS_NUMBER];
		for(int i = 0; i < PlayerData.STATS_NUMBER; i++) {
			stats[i] = Stats.getStatistic(Stats.getStats(i), player, 
					playerConfig.getInt("stats." + Stats.getStats(i).toString().toLowerCase() + ".experience"), 
					playerConfig.getInt("stats." + Stats.getStats(i).toString().toLowerCase() + ".level"));
		}
		data = new PlayerData(player, stats);
		
		return data;

	}
	
	public PlayerData loadOfflinePlayerData(OfflinePlayer player) {

		return loadPlayerData(Bukkit.getPlayerExact(player.getName()));

	}

	/**
	 * Saves all PlayerData in player.yml file.
	 * Task is asynchronously
	 */
	public void saveAllData() {

		for(PlayerData playerData : data) {
			File playerFile = new File(playerDataFolder, playerData.getPlayer().getName() + ".yml");
            FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

            for (int i = 0; i < playerData.getStats().length; i++) {
                playerConfig.set("stats." + Stats.getStats(i).toString() + ".level", playerData.getStats()[i].getLevel());
                playerConfig.set("stats." + Stats.getStats(i).toString() + ".experience", playerData.getStats()[i].getExperience());
            }
            try {
                playerConfig.save(playerFile);
            } catch (IOException e) {
                e.printStackTrace();
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
	
	public boolean isTimeScheduleActive() {
		if(timeSchedule == null)
			return false;
		return true;
	}
	
	public boolean isScoreboardActive() {
		if(scoreboard == null)
			return false;
		return true;
	}

	public FileConfiguration getConfig() {
		return config;
	}
	public FileConfiguration getMessage() {
		return message;
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
