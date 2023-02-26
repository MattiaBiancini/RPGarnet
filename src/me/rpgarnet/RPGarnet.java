package me.rpgarnet;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.rpgarnet.commands.*;
import me.rpgarnet.listener.*;
import me.rpgarnet.data.PlayerData;

public class RPGarnet extends JavaPlugin {
	
	public static RPGarnet instance;
	private PluginViewModel viewModel;
	//plugman unload RPGarnet
	//plugman load RPGarnet
	//plugman restart RPGarnet
	
	@Override
	public void onEnable() {
		
		instance = this;
		getLogger().log(Level.INFO, "Loading RPGarnet plugin!");
		
		inizialise();
		
		getLogger().log(Level.INFO, "RPGarnet has been loaded successfully!");
	}
	
	@Override
	public void onDisable() {
		
		viewModel.saveAllData();
		getLogger().log(Level.INFO, "RPGarnet has been unloaded successfully!");
		
	}
	
	public void onReload() {
		onDisable();
		for(Player player : Bukkit.getOnlinePlayers()) {
			player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
		inizialise();
	}
	
	public void inizialise() {
		
		loadCommands();
		loadListeners();
		viewModel = new PluginViewModel();
		for(Player player : Bukkit.getOnlinePlayers()) {
			PlayerData data = viewModel.loadPlayerData(player);
			if(data == null) {
				data = viewModel.registerNewPlayer(player);
			}
			viewModel.addPlayer(data);
			viewModel.getScoreboard().createScoreboard(data);
		}
		
		viewModel.getScoreboard().start();
		
	}

	private void loadListeners() {
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerExperience(), this);
		getServer().getPluginManager().registerEvents(new ChatEmoji(), this);
		getServer().getPluginManager().registerEvents(new ChatTag(), this);
	}
	
	private void loadCommands() {
		this.getCommand("rpg").setExecutor(new InfoPlayer());
		this.getCommand("rpg").setTabCompleter(new InfoPlayer());
	}
	
	public PluginViewModel getViewModel() {
		return viewModel;
	}
}
