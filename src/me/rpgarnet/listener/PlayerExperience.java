package me.rpgarnet.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedEnterEvent.BedEnterResult;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

import me.rpgarnet.PluginViewModel;
import me.rpgarnet.RPGarnet;
import me.rpgarnet.data.PlayerData;
import me.rpgarnet.data.attribute.Statistic;
import me.rpgarnet.data.attribute.Stats;
import me.rpgarnet.utils.StringUtils;

public class PlayerExperience implements Listener {

	public static Map<Player, Long> warning = new HashMap<>();
	private static final int COOLDOWN = 60;
	public static List<Player> sleeping = new ArrayList<>();

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
		e.setDropItems(false);
		Location loc = block.getLocation();
		loc.setX(loc.getBlockX() + 0.5);
		loc.setY(loc.getBlockY() + 0.5);
		loc.setZ(loc.getBlockZ() + 0.5);
		for(ItemStack item : e.getBlock().getDrops(player.getInventory().getItemInMainHand(), player)) {
			item.setAmount(item.getAmount() + (data.getStats()[Stats.getIntValue(Stats.LUCK)].getLevel()/10));
			loc.getWorld().dropItem(loc, item);
		}

	}

	@EventHandler
	public void onCropHarvestEvent(PlayerInteractEvent e) {

		Player player = e.getPlayer();

		PluginViewModel viewModel = RPGarnet.instance.getViewModel();
		if(viewModel.isAfk(player)) {
			e.setCancelled(true);
			warningPlayer(player, viewModel);			
		}

		Block block = e.getClickedBlock();
		if(block == null)
			return;

		if(!isCrop(block.getType()))
			return;

		Ageable age = (Ageable) block.getBlockData();
		/*		ItemStack hand = player.getInventory().getItemInMainHand();

		if(hand.getType() == Material.BONE_MEAL) {
			if(age.getAge() != age.getMaximumAge()) {
				age.setAge(age.getMaximumAge());
				block.setBlockData(age);
				if(player.getGameMode() == GameMode.SURVIVAL)
					hand.setAmount(hand.getAmount() - 1);
				return;
			}
		}*/

		if(age.getAge() != age.getMaximumAge())
			return;

		age.setAge(0);
		block.setBlockData(age);

		Location loc = block.getLocation();
		loc.setX(loc.getBlockX() + 0.5);
		loc.setY(loc.getBlockY() + 0.5);
		loc.setZ(loc.getBlockZ() + 0.5);

		PlayerData data = RPGarnet.instance.getViewModel().getPlayerData(player);

		for(ItemStack item : dropCrop(block.getType(), (int) data.getStats()[Stats.getIntValue(Stats.LUCK)].getAttributeValue())) {
			loc.getWorld().dropItem(loc, item);
		}


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

		if(e.getDamager() instanceof Player && e.getEntity() instanceof Player)
			return;

		if(e.getDamager() instanceof Player || (e.getDamager() instanceof AbstractArrow && ((AbstractArrow) e.getDamager()).getShooter() instanceof Player)) {

			Player player;
			if(e.getDamager() instanceof Player)
				player = (Player) e.getDamager();
			else
				player = (Player) ((AbstractArrow) e.getDamager()).getShooter();
			Entity entity = e.getEntity();
			if(isHostile(entity)) {
				PlayerData data = RPGarnet.instance.getViewModel().getPlayerData(player);
				int damage = (int) e.getFinalDamage();
				if(entity instanceof LivingEntity) {
					LivingEntity livingEntity = (LivingEntity) entity;
					if(damage > livingEntity.getHealth()) {
						damage = (int) livingEntity.getHealth();
						if(entity.getCustomName() != null && entity.getCustomName().equalsIgnoreCase(StringUtils.colorFixing("&f" + entity.getName() + " Clone")))
								damage += getHostileValue(entity);
					}
				}
				data.getStats()[Stats.getIntValue(Stats.DAMAGE)].addExperience(damage);
				data.getStats()[Stats.getIntValue(Stats.ATTACK_SPEED)].addExperience(damage);
			}

		}
		else if(e.getEntity() instanceof Player || (e.getEntity() instanceof AbstractArrow && !(((AbstractArrow) e.getEntity()).getShooter() instanceof Player))) {

			Player player; 
			if(e.getEntity() instanceof Player)
				player = (Player) e.getEntity();
			else
				player = (Player) ((AbstractArrow) e.getEntity()).getShooter();
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
	public void onMobSpawn(CreatureSpawnEvent e) {
		if(e.getSpawnReason() == SpawnReason.SPAWNER) {
			e.getEntity().setCustomName(StringUtils.colorFixing(StringUtils.colorFixing("&f" + e.getEntity().getName() + " Clone")));
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

	@EventHandler
	public void onPlayerAfkMove(PlayerMoveEvent e) {

		Player player = e.getPlayer();
		PluginViewModel viewModel = RPGarnet.instance.getViewModel();
		if(viewModel.isAfk(player)) {
			e.setCancelled(true);
			warningPlayer(player, viewModel);			
		}

	}

	@EventHandler
	public void onPlayerSleep(PlayerBedEnterEvent e) {
		if(!(e.getBedEnterResult() == BedEnterResult.OK))
			return;
		if(!RPGarnet.instance.getViewModel().isTimeScheduleActive())
			return;
		if(sleeping.contains(e.getPlayer()))
			return;
		sleeping.add(e.getPlayer());
		if(sleeping.size() * 2 - (Bukkit.getOnlinePlayers().size()) >= 0) {
			Bukkit.getWorld("world").setTime(0);
			Bukkit.getServer().broadcastMessage(StringUtils.yamlString(RPGarnet.instance.getViewModel().getMessage().getString("sleeping-50")));
		}
	}

	@EventHandler
	public void onPlayerSleep(PlayerBedLeaveEvent e) {
		if(!RPGarnet.instance.getViewModel().isTimeScheduleActive())
			return;
		if(sleeping.contains(e.getPlayer()))
			sleeping.remove(e.getPlayer());

	}

	private void warningPlayer(Player player, PluginViewModel viewModel) {

		if(!warning.containsKey(player)) {
			player.sendTitle(StringUtils.colorFixing(viewModel.getMessage().getString("afk.info")), 
					StringUtils.colorFixing(viewModel.getMessage().getString("afk.info-sub")), 20, 100, 40);
			player.sendMessage(StringUtils.yamlString(viewModel.getMessage().getString("afk.cancel")));
			warning.put(player, System.currentTimeMillis() - 2*COOLDOWN);
		}

		long secondsLeft = ((warning.get(player) / 1000) + COOLDOWN) - (System.currentTimeMillis()/1000);

		if(secondsLeft < 0) {

			player.sendTitle(StringUtils.colorFixing(viewModel.getMessage().getString("afk.info")), 
					StringUtils.colorFixing(viewModel.getMessage().getString("afk.info-sub")), 20, 100, 40);
			player.sendMessage(StringUtils.yamlString(viewModel.getMessage().getString("afk.cancel")));
			warning.put(player, System.currentTimeMillis());

		}

	}

	private List<ItemStack> dropCrop(Material mat, int luck) {

		List<ItemStack> drops = new ArrayList<>();

		if(mat == Material.CARROTS) {
			ItemStack item = new ItemStack(Material.CARROT);
			item.setAmount(drop(1, luck));
			drops.add(item);
		}
		else if(mat == Material.POTATOES) {
			ItemStack item = new ItemStack(Material.POTATO);
			item.setAmount(drop(1, luck));
			drops.add(item);
			ItemStack seed = new ItemStack(Material.POISONOUS_POTATO);
			if(Math.random() > 0.85)
				drops.add(seed);
		}
		else if(mat == Material.WHEAT) {
			ItemStack item = new ItemStack(Material.WHEAT);
			item.setAmount(drop(1, luck));
			drops.add(item);
			ItemStack seed = new ItemStack(Material.WHEAT_SEEDS);
			drops.add(seed);
		}
		else if(mat == Material.BEETROOTS) {
			ItemStack item = new ItemStack(Material.BEETROOT);
			item.setAmount(drop(1, luck));
			drops.add(item);
			ItemStack seed = new ItemStack(Material.BEETROOT_SEEDS);
			drops.add(seed);
		}
		else if(mat == Material.NETHER_WART) {
			ItemStack item = new ItemStack(Material.NETHER_WART);
			item.setAmount(drop(1, luck));
			drops.add(item);
		}

		return drops;
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
		if(type == EntityType.PILLAGER)
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
		if(e instanceof Projectile) {
			Projectile proj = (Projectile) e;
			Entity entity = (Entity) proj.getShooter();
			if(entity instanceof Player)
				return false;
			return true;
		}

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
		if(type == EntityType.PILLAGER)
			return 5;
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
		if(e instanceof Projectile) {
			Projectile proj = (Projectile) e;
			Entity entity = (Entity) proj.getShooter();
			if(entity instanceof Player)
				return 0;
			return getHostileValue(entity);
		}

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

		if(mat == Material.SPIDER_EYE)
			return -20;
		if(mat == Material.POISONOUS_POTATO)
			return -15;
		if(mat == Material.PUFFERFISH)
			return -15;
		if(mat == Material.ROTTEN_FLESH)
			return -10;
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
		if(mat == Material.POTATO)
			return -5;
		if(mat == Material.DRIED_KELP)
			return -5;
		if(mat == Material.MELON_SLICE)
			return 1;
		if(mat == Material.BEETROOT)
			return 1;
		if(mat == Material.CAKE)
			return 2;
		if(mat == Material.SWEET_BERRIES)
			return 4;
		if(mat == Material.TROPICAL_FISH)
			return 5;
		if(mat == Material.APPLE)
			return 5;
		if(mat == Material.COOKIE)
			return 5;
		if(mat == Material.GOLDEN_APPLE)
			return 5;
		if(mat == Material.PUMPKIN_PIE)
			return 8;
		if(mat == Material.BREAD)
			return 10;
		if(mat == Material.CARROT)
			return 10;
		if(mat == Material.BEETROOT_SOUP)
			return 10;
		if(mat == Material.BAKED_POTATO)
			return 15;
		if(mat == Material.GLOW_BERRIES)
			return 20;
		if(mat == Material.COOKED_SALMON)
			return 25;
		if(mat == Material.COOKED_CHICKEN)
			return 25;
		if(mat == Material.COOKED_COD)
			return 25;
		if(mat == Material.COOKED_MUTTON)
			return 25;
		if(mat == Material.COOKED_BEEF)
			return 30;
		if(mat == Material.COOKED_PORKCHOP)
			return 35;
		if(mat == Material.COOKED_RABBIT)
			return 40;
		if(mat == Material.MUSHROOM_STEW)
			return 50;
		if(mat == Material.GOLDEN_CARROT)
			return 80;
		if(mat == Material.CHORUS_FRUIT)
			return (int) ((Math.random() + 1) * 10);
		
		return 0;

	}

	private static int drop(int drops, int level) {

		if(level == 0)
			return drops;
		
		double rate = (1.0/(level + 2))+((level + 1)/2.0);

		return (Math.random() + (int)(drops*rate) >= drops * rate) ? (int)(drops*rate) + 1 : (int)(drops*rate);
	}

}
