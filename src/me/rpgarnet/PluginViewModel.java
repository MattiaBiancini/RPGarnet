package me.rpgarnet;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.rpgarnet.data.PlayerData;

public class PluginViewModel {

	private RPGarnet instance;

	private File configF;	
	private FileConfiguration config;

	private File messageF;
	private FileConfiguration message;

	private File playerF;
	private FileConfiguration player;

	public PluginViewModel() {

		instance = RPGarnet.instance;
		loadFiles();

	}

	public void loadFiles() {

		configF = new File(instance.getDataFolder(), "config.yml");
		messageF = new File(instance.getDataFolder(), "message.yml");
		playerF = new File(instance.getDataFolder(), "player.yml");

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

		config = YamlConfiguration.loadConfiguration(configF);
		message = YamlConfiguration.loadConfiguration(messageF);
		player = YamlConfiguration.loadConfiguration(playerF);

	}

	public boolean isPlayerRegistered(Player player) {
		return this.player.contains(player.getName());
	}

	public void registerNewPlayer(Player player) {

		this.player.set(player.getName() + ".UUID", player.getUniqueId().toString());

		PlayerData playerData = new PlayerData(player);
		savePlayerData(playerData);

	}

	public void savePlayerData(PlayerData playerData) {

		this.player.set(player.getName() + ".armor", playerData.getArmor());
		this.player.set(player.getName() + ".damage", playerData.getDamage());
		this.player.set(player.getName() + ".knockback", playerData.getKnockback());
		this.player.set(player.getName() + ".attackSpeed", playerData.getAttackSpeed());
		this.player.set(player.getName() + ".knockbackResistance", playerData.getKnockbackResistance());
		this.player.set(player.getName() + ".health", playerData.getHealth());
		this.player.set(player.getName() + ".movementSpeed", playerData.getMovementSpeed());
		this.player.set(player.getName() + ".luck", playerData.getLuck());

	}

	public PlayerData getPlayerData(Player player) {

		PlayerData playerData = new PlayerData(
				this.player.getDouble(player.getName() + ".armor"),
				this.player.getDouble(player.getName() + ".damage"),
				this.player.getDouble(player.getName() + ".knockback"),
				this.player.getDouble(player.getName() + ".attackSpeed"),
				this.player.getDouble(player.getName() + ".knockbackResistance"),
				this.player.getDouble(player.getName() + ".health"),
				this.player.getDouble(player.getName() + ".movementSpeed"),
				this.player.getDouble(player.getName() + ".luck"),
				player
				);
		
		return playerData;

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

}
