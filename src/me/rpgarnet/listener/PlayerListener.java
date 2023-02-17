package me.rpgarnet.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
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

		viewModel.addPlayer(data);
		data.setPlayerAttributes();
		HexColor stringUtils = new HexColor();

		sb.createScoreboard(data);

		player.sendMessage(stringUtils.centeredMessage("&6&l---=[&4&l" + StringUtils.PREFIX + "&6&l]=---"));
		player.sendMessage(StringUtils.voidMessage());
		player.sendMessage(stringUtils.centeredMessage(StringUtils.yamlString(viewModel.getMessage().getString("welcome"), data)));
		player.sendMessage(StringUtils.voidMessage());
		player.sendMessage(stringUtils.centeredMessage("&6&l-----====[]====-----"));



	}

	@EventHandler
	public void onPlayerLeaveEvent(PlayerQuitEvent e) {

		Player player = e.getPlayer();
		PluginViewModel viewModel = RPGarnet.instance.getViewModel();
		PlayerData data = viewModel.removePlayer(player);
		e.setQuitMessage(StringUtils.colorFixing("&8[&c-&8] &7" + player.getName()));
		ScoreboardHandler.removePlayerScoreboard(player);

		if(data == null)
			return;

	}

}
