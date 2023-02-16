package me.rpgarnet;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

public class RPGarnet extends JavaPlugin {
	
	public static RPGarnet instance;
	private PluginViewModel viewModel;
	
	@Override
	public void onEnable() {
		
		instance = this;
		getLogger().log(Level.INFO, "Loading plugin!");
		
		viewModel = new PluginViewModel();
		
		
		
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
}
