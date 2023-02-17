package me.rpgarnet.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.rpgarnet.PluginViewModel;
import me.rpgarnet.RPGarnet;
import me.rpgarnet.data.PlayerData;
import me.rpgarnet.data.attribute.Statistic;
import me.rpgarnet.data.attribute.Stats;
import me.rpgarnet.utils.StringUtils;

public class PlayerExperience implements Listener {
	
	@EventHandler
	public void onOreBlockBreak(BlockBreakEvent e) {
		
		Block block = e.getBlock();
		Player player = e.getPlayer();
		if(block == null)
			return;
		
		if(!isValueableBlock(block))
			return;
		if(player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH))
			return;
		
		PlayerData data = RPGarnet.instance.getViewModel().getPlayerData(player);
		data.getStats()[Stats.getIntValue(Stats.LUCK)].addExperience(getMaterialValue(block.getType()));
		
	}
	
	@EventHandler
	public void onCropHarvestEvent(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		Block block = e.getClickedBlock();
		if(block == null)
			return;
		
		if(!isCrop(block.getType()))
			return;
		
		Ageable age = (Ageable) block.getBlockData();
		if(age.getAge() != age.getMaximumAge())
			return;
		
		age.setAge(0);
		block.setBlockData(age);
		
		Location loc = block.getLocation();
		loc.setX(loc.getBlockX() + 0.5);
		loc.setY(loc.getBlockY() + 0.5);
		loc.setZ(loc.getBlockZ() + 0.5);
		
		for(ItemStack item : block.getDrops(player.getInventory().getItemInMainHand(), player)) {
			loc.getWorld().dropItem(loc, item);
		}
		
		PlayerData data = RPGarnet.instance.getViewModel().getPlayerData(player);
		data.getStats()[Stats.getIntValue(Stats.LUCK)].addExperience(2);
		
	}
	
	@EventHandler
	public void onShear(PlayerShearEntityEvent e) {
		Player player = e.getPlayer();
		PlayerData data = RPGarnet.instance.getViewModel().getPlayerData(player);
		data.getStats()[Stats.getIntValue(Stats.LUCK)].addExperience(2);
	}
	
	@EventHandler
	public void onFish(PlayerFishEvent e) {
		Player player = e.getPlayer();
		PlayerData data = RPGarnet.instance.getViewModel().getPlayerData(player);
		
		Entity entity = e.getCaught();
		if(entity instanceof Item) {
			ItemStack item = ((Item)entity).getItemStack();
			int value = getMaterialValue(item.getType());
			data.getStats()[Stats.getIntValue(Stats.LUCK)].addExperience(value);
		}
	}
	
	@EventHandler
	public void onDealDamage(EntityDamageByEntityEvent e) {
		
		if(e.getDamager() instanceof Player) {
			
			Player player = (Player) e.getDamager();
			Entity entity = e.getEntity();
			if(isHostile(entity)) {
				PlayerData data = RPGarnet.instance.getViewModel().getPlayerData(player);
				data.getStats()[Stats.getIntValue(Stats.DAMAGE)].addExperience(getHostileValue(entity));
				data.getStats()[Stats.getIntValue(Stats.ATTACK_SPEED)].addExperience(getHostileValue(entity));
			}
			
		}
		else if(e.getEntity() instanceof Player) {
			
			Player player = (Player) e.getEntity();
			Entity entity = e.getDamager();
			
			if(isHostile(entity)) {
				PlayerData data = RPGarnet.instance.getViewModel().getPlayerData(player);
				int health = (int) e.getDamage();
				int armor = (int) e.getFinalDamage();
				data.getStats()[Stats.getIntValue(Stats.HEALTH)].addExperience(health);
				data.getStats()[Stats.getIntValue(Stats.ARMOR)].addExperience(armor);
				data.getStats()[Stats.getIntValue(Stats.KNOCKBACK_RESISTANCE)].addExperience(armor);
			}
			
		}
		
	}
	
	@EventHandler
	public void onPlayerDie(PlayerDeathEvent e) {
		Player player = e.getEntity();
		PluginViewModel viewModel = RPGarnet.instance.getViewModel();
		PlayerData data = viewModel.getPlayerData(player);
		for(Statistic stat : data.getStats()) {
			stat.deathReset();
		}
		player.sendMessage(StringUtils.yamlString(viewModel.getMessage().getString("death-message"), data));
	}
	
	@EventHandler
	public void onPlayerEat(PlayerItemConsumeEvent e) {
		Player player = e.getPlayer();
		ItemStack item = e.getItem();
		if(isFood(item.getType())) {
			PluginViewModel viewModel = RPGarnet.instance.getViewModel();
			PlayerData data = viewModel.getPlayerData(player);
			data.getStats()[Stats.getIntValue(Stats.MOVEMENT_SPEED)].addExperience(getFoodExperience(item.getType()));
			player.sendMessage(StringUtils.yamlString(viewModel.getMessage().getString("feed-information"), getFoodExperience(item.getType())));
		}
	}
	
	private static boolean isValueableBlock(Block block) {
		
		Material mat = block.getType();
		
		if(mat == Material.COAL_ORE)
			return true;
		if(mat == Material.COPPER_ORE)
			return true;
		if(mat == Material.DIAMOND_ORE)
			return true;
		if(mat == Material.EMERALD_ORE)
			return true;
		if(mat == Material.GOLD_ORE)
			return true;
		if(mat == Material.IRON_ORE)
			return true;
		if(mat == Material.LAPIS_ORE)
			return true;
		if(mat == Material.REDSTONE_ORE)
			return true;
		if(mat == Material.DEEPSLATE_COAL_ORE)
			return true;
		if(mat == Material.DEEPSLATE_COPPER_ORE)
			return true;
		if(mat == Material.DEEPSLATE_DIAMOND_ORE)
			return true;
		if(mat == Material.DEEPSLATE_EMERALD_ORE)
			return true;
		if(mat == Material.DEEPSLATE_GOLD_ORE)
			return true;
		if(mat == Material.DEEPSLATE_IRON_ORE)
			return true;
		if(mat == Material.DEEPSLATE_LAPIS_ORE)
			return true;
		if(mat == Material.DEEPSLATE_REDSTONE_ORE)
			return true;
		if(mat == Material.NETHER_QUARTZ_ORE)
			return true;
		if(mat == Material.NETHER_GOLD_ORE)
			return true;
		if(mat == Material.ANCIENT_DEBRIS)
			return true;
		if(mat == Material.PUMPKIN)
			return true;
		if(mat == Material.MELON)
			return true;
		
		return false;
	}
	
	private static boolean isCrop(Material mat) {
		
		if(mat == Material.WHEAT)
			return true;
		if(mat == Material.POTATOES)
			return true;
		if(mat == Material.CARROTS)
			return true;
		if(mat == Material.CHORUS_FLOWER)
			return true;
		if(mat == Material.NETHER_WART)
			return true;
		if(mat == Material.BEETROOTS)
			return true;
		if(mat == Material.SWEET_BERRY_BUSH)
			return true;
		
		return false;
		
	}
	
	private static int getMaterialValue(Material mat) {
		
		if(mat == Material.COAL_ORE)
			return 5;
		if(mat == Material.COPPER_ORE)
			return 6;
		if(mat == Material.DIAMOND_ORE)
			return 50;
		if(mat == Material.EMERALD_ORE)
			return 60;
		if(mat == Material.GOLD_ORE)
			return 40;
		if(mat == Material.IRON_ORE)
			return 20;
		if(mat == Material.LAPIS_ORE)
			return 10;
		if(mat == Material.REDSTONE_ORE)
			return 15;
		if(mat == Material.DEEPSLATE_COAL_ORE)
			return 6;
		if(mat == Material.DEEPSLATE_COPPER_ORE)
			return 7;
		if(mat == Material.DEEPSLATE_DIAMOND_ORE)
			return 55;
		if(mat == Material.DEEPSLATE_EMERALD_ORE)
			return 65;
		if(mat == Material.DEEPSLATE_GOLD_ORE)
			return 45;
		if(mat == Material.DEEPSLATE_IRON_ORE)
			return 25;
		if(mat == Material.DEEPSLATE_LAPIS_ORE)
			return 11;
		if(mat == Material.DEEPSLATE_REDSTONE_ORE)
			return 17;
		if(mat == Material.NETHER_QUARTZ_ORE)
			return 15;
		if(mat == Material.NETHER_GOLD_ORE)
			return 9;
		if(mat == Material.ANCIENT_DEBRIS)
			return 100;
		if(mat == Material.PUMPKIN)
			return 1;
		if(mat == Material.MELON)
			return 1;
		if(mat == Material.PUFFERFISH)
			return 5;
		if(mat == Material.COD)
			return 1;
		if(mat == Material.SALMON)
			return 1;
		if(mat == Material.NAUTILUS_SHELL)
			return 8;
		if(mat == Material.ENCHANTED_BOOK)
			return 10;
		if(mat == Material.TRIPWIRE_HOOK)
			return 3;
		if(mat == Material.LEATHER)
			return 3;
		
		return 0;
		
	}
	
	private static boolean isHostile(Entity e) {
		
		EntityType type = e.getType();
		if(type == EntityType.ZOMBIE)
			return true;
		if(type == EntityType.ZOMBIE_VILLAGER)
			return true;
		if(type == EntityType.SKELETON)
			return true;
		if(type == EntityType.HUSK)
			return true;
		if(type == EntityType.WITHER_SKELETON)
			return true;
		if(type == EntityType.CREEPER)
			return true;
		if(type == EntityType.SPIDER)
			return true;
		if(type == EntityType.CAVE_SPIDER)
			return true;
		if(type == EntityType.ENDERMAN)
			return true;
		if(type == EntityType.ENDER_DRAGON)
			return true;
		if(type == EntityType.ENDERMITE)
			return true;
		if(type == EntityType.GHAST)
			return true;
		if(type == EntityType.BLAZE)
			return true;
		if(type == EntityType.PIGLIN)
			return true;
		if(type == EntityType.PIGLIN_BRUTE)
			return true;
		if(type == EntityType.ZOMBIFIED_PIGLIN)
			return true;
		if(type == EntityType.WITCH)
			return true;
		if(type == EntityType.DROWNED)
			return true;
		if(type == EntityType.ELDER_GUARDIAN)
			return true;
		if(type == EntityType.EVOKER)
			return true;
		if(type == EntityType.GUARDIAN)
			return true;
		if(type == EntityType.ILLUSIONER)
			return true;
		if(type == EntityType.MAGMA_CUBE)
			return true;
		if(type == EntityType.PHANTOM)
			return true;
		if(type == EntityType.SHULKER)
			return true;
		if(type == EntityType.SILVERFISH)
			return true;
		if(type == EntityType.VEX)
			return true;
		if(type == EntityType.VINDICATOR)
			return true;
		if(type == EntityType.WARDEN)
			return true;
		if(type == EntityType.WITHER)
			return true;
		
		return false;
	}
	
	private static int getHostileValue(Entity e) {
		
		EntityType type = e.getType();
		if(type == EntityType.ZOMBIE)
			return 1;
		if(type == EntityType.ZOMBIE_VILLAGER)
			return 1;
		if(type == EntityType.SKELETON)
			return 2;
		if(type == EntityType.HUSK)
			return 1;
		if(type == EntityType.WITHER_SKELETON)
			return 3;
		if(type == EntityType.CREEPER)
			return 1;
		if(type == EntityType.SPIDER)
			return 1;
		if(type == EntityType.CAVE_SPIDER)
			return 2;
		if(type == EntityType.ENDERMAN)
			return 5;
		if(type == EntityType.ENDER_DRAGON)
			return 40;
		if(type == EntityType.ENDERMITE)
			return 1;
		if(type == EntityType.GHAST)
			return 8;
		if(type == EntityType.BLAZE)
			return 7;
		if(type == EntityType.PIGLIN)
			return 1;
		if(type == EntityType.PIGLIN_BRUTE)
			return 3;
		if(type == EntityType.ZOMBIFIED_PIGLIN)
			return 1;
		if(type == EntityType.WITCH)
			return 4;
		if(type == EntityType.DROWNED)
			return 1;
		if(type == EntityType.ELDER_GUARDIAN)
			return 25;
		if(type == EntityType.EVOKER)
			return 10;
		if(type == EntityType.GUARDIAN)
			return 2;
		if(type == EntityType.ILLUSIONER)
			return 2;
		if(type == EntityType.MAGMA_CUBE)
			return 1;
		if(type == EntityType.PHANTOM)
			return 1;
		if(type == EntityType.SHULKER)
			return 2;
		if(type == EntityType.SILVERFISH)
			return 1;
		if(type == EntityType.VEX)
			return 1;
		if(type == EntityType.VINDICATOR)
			return 2;
		if(type == EntityType.WARDEN)
			return 60;
		if(type == EntityType.WITHER)
			return 50;
		
		return 0;
	}
	
	public boolean isFood(Material mat) {
		
		if(mat == Material.ROTTEN_FLESH)
			return true;
		if(mat == Material.SPIDER_EYE)
			return true;
		if(mat == Material.APPLE)
			return true;
		if(mat == Material.COOKED_BEEF)
			return true;
		if(mat == Material.COOKED_CHICKEN)
			return true;
		if(mat == Material.COOKED_COD)
			return true;
		if(mat == Material.COOKED_MUTTON)
			return true;
		if(mat == Material.COOKED_PORKCHOP)
			return true;
		if(mat == Material.COOKED_RABBIT)
			return true;
		if(mat == Material.COOKED_SALMON)
			return true;
		if(mat == Material.BEEF)
			return true;
		if(mat == Material.CHICKEN)
			return true;
		if(mat == Material.COD)
			return true;
		if(mat == Material.MUTTON)
			return true;
		if(mat == Material.PORKCHOP)
			return true;
		if(mat == Material.RABBIT)
			return true;
		if(mat == Material.SALMON)
			return true;
		if(mat == Material.COOKIE)
			return true;
		if(mat == Material.BREAD)
			return true;
		if(mat == Material.POTATO)
			return true;
		if(mat == Material.BAKED_POTATO)
			return true;
		if(mat == Material.CARROT)
			return true;
		if(mat == Material.GOLDEN_APPLE)
			return true;
		if(mat == Material.GOLDEN_CARROT)
			return true;
		if(mat == Material.MUSHROOM_STEW)
			return true;
		if(mat == Material.PUFFERFISH)
			return true;
		if(mat == Material.MELON_SLICE)
			return true;
		if(mat == Material.BEETROOT)
			return true;
		if(mat == Material.BEETROOT_SOUP)
			return true;
		if(mat == Material.CAKE)
			return true;
		if(mat == Material.CHORUS_FRUIT)
			return true;
		if(mat == Material.DRIED_KELP)
			return true;
		if(mat == Material.GLOW_BERRIES)
			return true;
		if(mat == Material.SWEET_BERRIES)
			return true;
		if(mat == Material.TROPICAL_FISH)
			return true;
		
		return false;
		
	}
	
	public int getFoodExperience(Material mat) {
		
		if(mat == Material.ROTTEN_FLESH)
			return -10;
		if(mat == Material.SPIDER_EYE)
			return -20;
		if(mat == Material.APPLE)
			return 5;
		if(mat == Material.COOKED_BEEF)
			return 30;
		if(mat == Material.COOKED_CHICKEN)
			return 25;
		if(mat == Material.COOKED_COD)
			return 25;
		if(mat == Material.COOKED_MUTTON)
			return 25;
		if(mat == Material.COOKED_PORKCHOP)
			return 35;
		if(mat == Material.COOKED_RABBIT)
			return 40;
		if(mat == Material.COOKED_SALMON)
			return 25;
		if(mat == Material.BEEF)
			return -5;
		if(mat == Material.CHICKEN)
			return -5;
		if(mat == Material.COD)
			return -5;
		if(mat == Material.MUTTON)
			return -5;
		if(mat == Material.PORKCHOP)
			return -5;
		if(mat == Material.RABBIT)
			return -5;
		if(mat == Material.SALMON)
			return -5;
		if(mat == Material.COOKIE)
			return 5;
		if(mat == Material.BREAD)
			return 10;
		if(mat == Material.POTATO)
			return -5;
		if(mat == Material.BAKED_POTATO)
			return 15;
		if(mat == Material.CARROT)
			return 10;
		if(mat == Material.GOLDEN_APPLE)
			return 2;
		if(mat == Material.GOLDEN_CARROT)
			return 80;
		if(mat == Material.MUSHROOM_STEW)
			return 50;
		if(mat == Material.PUFFERFISH)
			return -15;
		if(mat == Material.MELON_SLICE)
			return 1;
		if(mat == Material.BEETROOT)
			return 1;
		if(mat == Material.BEETROOT_SOUP)
			return 10;
		if(mat == Material.CAKE)
			return 2;
		if(mat == Material.CHORUS_FRUIT)
			return (int) ((Math.random() + 1) * 10);
		if(mat == Material.DRIED_KELP)
			return -5;
		if(mat == Material.GLOW_BERRIES)
			return 20;
		if(mat == Material.SWEET_BERRIES)
			return 4;
		if(mat == Material.TROPICAL_FISH)
			return 5;
		
		return 0;
		
	}

}
