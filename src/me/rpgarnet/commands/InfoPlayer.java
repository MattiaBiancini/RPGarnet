package me.rpgarnet.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.rpgarnet.PluginViewModel;
import me.rpgarnet.RPGarnet;
import me.rpgarnet.data.PlayerData;
import me.rpgarnet.data.attribute.CustomStatistic;
import me.rpgarnet.data.attribute.Stats;
import me.rpgarnet.utils.HexColor;
import me.rpgarnet.utils.StringUtils;

public class InfoPlayer implements CommandExecutor, TabCompleter {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(StringUtils.colorFixing("&4&LERROR&C: This command is executable only by &4&lPlayers&c!"));
			return true;
		}
		
		Player player = (Player) sender;
		PluginViewModel viewModel = RPGarnet.instance.getViewModel();
		
		if(args.length == 0) {
			
			player.sendMessage("§7===================[§6§lRPGarnet§e§lInfo§7]===================");
			player.sendMessage("§e/rpg §8§l» §7Shows this page");
			player.sendMessage("§e/rpg all §8§l» §7Shows this page");
			player.sendMessage("§e/rpg [stats] §8§l» §7Shows info on that stats");
			player.sendMessage("§7=====================================================");
			return true;
			
		}
		
		PlayerData data = viewModel.getPlayerData(player);
		if(data == null) {
			sender.sendMessage(StringUtils.yamlString(viewModel.getMessage().getString("stats.data-not-found")));
			return true;
		}
		
		if(args[0].equalsIgnoreCase("all")) {
			openGUI(data);
			return true;
		}
		
		Stats stat = Stats.getStats(args[0]);
		if(stat == null) {
			sender.sendMessage(StringUtils.yamlString(viewModel.getMessage().getString("stats.not-found")));
			return true;
		}
		
		player.sendMessage(HexColor.centeredMessage(StringUtils.colorFixing("&7--=[&6&l" + stat.getStatsName() + "&7]=--")));
		player.sendMessage(StringUtils.colorFixing("&7"));
		player.sendMessage(StringUtils.colorFixing("&7Attribute Value &8» &e" + data.getStats()[Stats.getIntValue(stat)].getAttributeValue()));
		player.sendMessage(StringUtils.colorFixing("&7Level &8» &e" + data.getStats()[Stats.getIntValue(stat)].getLevel()));
		player.sendMessage(StringUtils.colorFixing("&7Experience &8» &e" + data.getStats()[Stats.getIntValue(stat)].getExperience() + "&7/&6" + data.getStats()[Stats.getIntValue(stat)].getExpToLevel()));
		player.sendMessage(StringUtils.percentageExp(data, Stats.getIntValue(stat)));
		player.sendMessage(HexColor.centeredMessage(StringUtils.colorFixing("&7")));
		player.sendMessage(HexColor.centeredMessage(StringUtils.colorFixing("&7 --=[]=--")));
		
		return true;
		
	}
	
	@Override
	public List<String>	onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		
		List<String> stats = new ArrayList<>();
		
		if(args.length == 1) {
			if(args[0].length() == 0) {
				for(int i = 0; i < 7; i++)
					stats.add(Stats.getStats(i).getStatsName().toLowerCase());
				stats.add("all");
			}
			else {
				for(int i = 0; i < 7; i++)
					if(Stats.getStats(i).getStatsName().toLowerCase().indexOf(args[0]) == 0)
					stats.add(Stats.getStats(i).getStatsName().toLowerCase());
				if("all".indexOf(args[0]) == 0)
					stats.add("all");
			}
		}
		
		return stats;
		
	}
	
	private void openGUI(PlayerData data) {
		
		Player player = data.getPlayer();
		Inventory inv = Bukkit.createInventory(null, 45, StringUtils.colorFixing("&4&l" + player.getName() + "'s Stats"));
		
		ItemStack paneCorner = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta paneCornerM = paneCorner.getItemMeta();
		paneCornerM.setDisplayName(StringUtils.colorFixing("&7"));
		paneCorner.setItemMeta(paneCornerM);
		
		ItemStack paneBack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta paneBackM = paneBack.getItemMeta();
		paneBackM.setDisplayName(StringUtils.colorFixing("&7"));
		paneBack.setItemMeta(paneBackM);
		
		for(int i = 0; i < inv.getSize(); i++) {
			
			if(i <= 8 || i >= 36 || i%9 == 0 || (i + 1)%9 == 0)
				inv.setItem(i, paneCorner);
			else
				inv.setItem(i, paneBack);
			
		}
		
		for(int i = 0; i < data.getStats().length; i++) {
			CustomStatistic statistic = data.getStats()[i];
			ItemStack attribute = new ItemStack(getStatisticItem(i));
			ItemMeta attributeM = attribute.getItemMeta();
			attributeM.setDisplayName(StringUtils.colorFixing("&4" + Stats.getStats(i).getStatsName()));
			attributeM.addEnchant(Enchantment.CHANNELING, 1, false);
			attributeM.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			List<String> lore = new ArrayList<>();
			lore.add(StringUtils.colorFixing("&7Level&8» &e" + statistic.getLevel()+ "&7/&6" + statistic.getMaxLevel()));
			lore.add(StringUtils.colorFixing("&7Experience&8» &e" + statistic.getExperience() + "&7/&6" + statistic.getExpToLevel()));
			lore.add(StringUtils.colorFixing("&7Value&8» &6" + statistic.getAttributeValue()));
			lore.add(StringUtils.colorFixing("&7"));
			for(String string : CustomStatistic.getDescription(statistic))
				lore.add(string);
			attributeM.setLore(lore);
			attribute.setItemMeta(attributeM);
			inv.setItem(getStatisticSlot(i), attribute);
		}
		
		data.getPlayer().openInventory(inv);		
	}
	
	private int getStatisticSlot(int i) {
		switch(i) {
			case 0:
				return 10;
			case 1:
				return 12;
			case 2:
				return 14;
			case 3:
				return 16;
			case 4:
				return 28;
			case 5:
				return 30;
			case 6:
				return 32;
			case 7:
				return 34;
		}
		return 0;
	}
	
	private Material getStatisticItem(int i) {
		switch(i) {
			case 0:
				return Material.DIAMOND;
			case 1:
				return Material.REDSTONE;
			case 2:
				return Material.BLAZE_POWDER;
			case 3:
				return Material.PHANTOM_MEMBRANE;
			case 4:
				return Material.GHAST_TEAR;
			case 5:
				return Material.NETHERITE_INGOT;
			case 6:
				return Material.LAPIS_LAZULI;
			case 7:
				return Material.FEATHER;
		}
	return Material.AIR;
	}

}
