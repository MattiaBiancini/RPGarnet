package me.rpgarnet;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

import me.rpgarnet.commands.PlaySoundCMD;
import me.rpgarnet.listener.PlayerExperience;
import me.rpgarnet.listener.PlayerListener;

public class RPGarnet extends JavaPlugin {
	
	public static RPGarnet instance;
	private PluginViewModel viewModel;
	
	@Override
	public void onEnable() {
		
		instance = this;
		getLogger().log(Level.INFO, "Loading plugin!");
		
		viewModel = new PluginViewModel();
		
		this.getCommand("suono").setExecutor(new PlaySoundCMD());
		loadListeners();
		
		viewModel.getScoreboard().start();
		
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

	public void loadListeners() {
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getServer().getPluginManager().registerEvents(new PlayerExperience(), this);
	}
	
	public PluginViewModel getViewModel() {
		return viewModel;
	}
}
