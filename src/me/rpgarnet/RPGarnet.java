package me.rpgarnet;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.rpgarnet.commands.*;
import me.rpgarnet.listener.*;
import me.rpgarnet.papi.Placeholder;
import me.rpgarnet.recipe.item.smithingplus.SmithingPlus;
import me.rpgarnet.recipe.item.transposer.STCrafting;
import me.rpgarnet.data.PlayerData;
import me.rpgarnet.event.game.GameMechanics;
import me.rpgarnet.event.time.BloodMoonEvent;

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
		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new Placeholder().register();
      }

		getLogger().log(Level.INFO, "RPGarnet has been loaded successfully!");
	}

	@Override
	public void onDisable() {

		viewModel.saveAllData();
		Bukkit.removeRecipe(new NamespacedKey(this, "smithing_plus"));
		Bukkit.removeRecipe(new NamespacedKey(this, "spawner_transposer"));
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
		loadCrafting();
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
		getServer().getPluginManager().registerEvents(new GameMechanics(), this);
		getServer().getPluginManager().registerEvents(new SmithingPlus(), this);
		getServer().getPluginManager().registerEvents(new STCrafting(), this);
		getServer().getPluginManager().registerEvents(new BloodMoonEvent(), this);
	}

	private void loadCommands() {
		this.getCommand("rpg").setExecutor(new InfoPlayer());
		this.getCommand("rpg").setTabCompleter(new InfoPlayer());
	}
	
	private void loadCrafting() {
		
		Bukkit.addRecipe(SmithingPlus.smithingPlusRecipe());
		Bukkit.addRecipe(STCrafting.stcrafting());
		
	}

	public PluginViewModel getViewModel() {
		return viewModel;
	}
}
