package me.rpgarnet.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.rpgarnet.PluginViewModel;
import me.rpgarnet.RPGarnet;
import me.rpgarnet.data.PlayerData;
import me.rpgarnet.scoreboard.ScoreboardHandler;
import me.rpgarnet.scoreboard.ScoreboardManager;
import me.rpgarnet.utils.HexColor;
import me.rpgarnet.utils.StringUtils;

public class PlayerListener implements Listener {

	private final ScoreboardManager sb = new ScoreboardManager();

	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) {

		Player player = e.getPlayer();

		e.setJoinMessage(StringUtils.colorFixing("&8[&a+&8] &7" + player.getName()));

		PluginViewModel viewModel = RPGarnet.instance.getViewModel();
		PlayerData data;

		if(!viewModel.isPlayerRegistered(player)) {
			data = viewModel.registerNewPlayer(player);
		}
		else {
			data = viewModel.loadPlayerData(player);
		}

		if(data == null) {
			player.kickPlayer(StringUtils.yamlString(viewModel.getMessage().getString("failed-registration")));
			return;
		}
		
		player.setResourcePack("https://www.dropbox.com/s/6l1gjp54yckkg68/RPGarnetTexturePack.zip?dl=1");

		viewModel.addPlayer(data);
		data.setPlayerAttributes();
		
		if(viewModel.isScoreboardActive())
			sb.createScoreboard(data);

		player.sendMessage(HexColor.centeredMessage("&6&l---=[&4&l " + StringUtils.PREFIX + "&6&l]=---"));
		player.sendMessage(StringUtils.voidMessage());
		player.sendMessage(HexColor.centeredMessage(StringUtils.placeholder(viewModel.getMessage().getString("welcome"), data)));
		player.sendMessage(StringUtils.voidMessage());
		player.sendMessage(HexColor.centeredMessage("&6&l-----====[]====-----"));



	}

	@EventHandler
	public void onPlayerLeaveEvent(PlayerQuitEvent e) {

		Player player = e.getPlayer();
		PluginViewModel viewModel = RPGarnet.instance.getViewModel();
		if(viewModel.isScoreboardActive())
			ScoreboardHandler.removePlayerScoreboard(player);
		PlayerData data = viewModel.getPlayerData(player);
		e.setQuitMessage(StringUtils.colorFixing("&8[&c-&8] &7" + player.getName()));

		if(data == null)
			return;
		
		viewModel.savePlayerData(data);

	}
	
	@EventHandler
	public void onPlayerClickInfo(InventoryClickEvent e) {
		
		if(e.getInventory().getHolder() != null)
			return;
		
		Player player = (Player) e.getWhoClicked();
		
		if(e.getView().getTitle().equalsIgnoreCase(StringUtils.colorFixing("&4&l" + player.getName() + "'s Stats"))) {
			e.setCancelled(true);
		}
		
	}
	
}
