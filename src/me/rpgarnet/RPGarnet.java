package me.rpgarnet;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import me.rpgarnet.data.PlayerData;

public class RPGarnet extends JavaPlugin {
	
	public static RPGarnet instance;
	private PluginViewModel viewModel;
	
	private List<PlayerData> player; 
	
	@Override
	public void onEnable() {
		
		instance = this;
		getLogger().log(Level.INFO, "Loading plugin!");
		
		viewModel = new PluginViewModel();
		player = new ArrayList<>();
		
		
		getLogger().log(Level.INFO, "RPGarnet has been loaded successfully!");
	}
	
	@Override
	public void onDisable() {
		
		getLogger().log(Level.INFO, "RPGarnet has been unloaded successfully!");
		
	}
	
	public void onReload() {
		
		onDisable();
		onEnable();
		
	}

	
	public PluginViewModel getViewModel() {
		return viewModel;
	}
	public List<PlayerData> getPlayer() {
		return player;
	}
}
