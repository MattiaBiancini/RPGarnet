package me.rpgarnet.recipe.item.smithingplus;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.entity.Villager.Type;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.Damageable;

import me.rpgarnet.RPGarnet;
import me.rpgarnet.recipe.item.inventory.CornerBlack;
import me.rpgarnet.recipe.item.inventory.PanesGUI;
import me.rpgarnet.recipe.item.inventory.Utility;
import me.rpgarnet.utils.TimeUtils;

public class SmithingPlus implements Listener {
	
	private static Map<Player, Long> cooldown = new HashMap<>();
	private static final int COOLDOWN = 2;

	public static ShapelessRecipe smithingPlusRecipe() {

		ShapelessRecipe smithing = new ShapelessRecipe(new NamespacedKey(RPGarnet.instance, "smithing_plus"), SmithingEgg.egg);
		smithing.addIngredient(Material.SMITHING_TABLE);
		smithing.addIngredient(Material.NETHER_STAR);
		smithing.addIngredient(Material.ANVIL);

		return smithing;
	}

	@EventHandler
	public void onPlayerSummon(PlayerInteractEvent e) {

		Player player = e.getPlayer();

		if(e.getClickedBlock() == null || e.getClickedBlock().getType() == Material.AIR)
			return;

		if(player.getInventory().getItemInMainHand() == null)
			return;

		ItemStack item = player.getInventory().getItemInMainHand();
		if(item.getType() == SmithingEgg.egg.getType() && item.hasItemMeta() 
				&& item.getItemMeta().getDisplayName().equalsIgnoreCase(SmithingEgg.egg.getItemMeta().getDisplayName())) {

			if(!cooldown.containsKey(player)) {
				cooldown.put(player, System.currentTimeMillis());
			}
			else {
				if(!TimeUtils.isInCooldown(cooldown.get(player), COOLDOWN)) {
					e.setCancelled(true);
					return;
				}
				else {
					cooldown.remove(player);
					cooldown.put(player, System.currentTimeMillis());
				}
			}
			
			item.setAmount(item.getAmount() - 1);

			Location location = e.getClickedBlock().getLocation().add(0.5, 1, 0.5);

			Location pLocation = player.getLocation();
			pLocation.setX(location.getX());
			pLocation.setY(location.getY());
			pLocation.setZ(location.getZ());
			pLocation.setPitch(0);
			pLocation.setYaw((180 + pLocation.getYaw()));

			Villager villager = (Villager) player.getWorld().spawnEntity(pLocation, EntityType.VILLAGER);
			villager.setCustomName("§4Enchanted Smithing Table");
			villager.setAdult();
			villager.setCanPickupItems(false);
			villager.setCustomNameVisible(true);
			villager.setInvulnerable(true);
			villager.setVillagerType(Type.PLAINS);
			villager.setProfession(Profession.TOOLSMITH);
			villager.setAI(false);
			villager.setCollidable(false);
			villager.setSilent(true);
			e.setCancelled(true);

		}

	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEntityEvent e) {

		if(!e.getRightClicked().isCustomNameVisible())
			return;

		if(e.getRightClicked().getCustomName().equalsIgnoreCase("§4Enchanted Smithing Table")) {
			e.getPlayer().openInventory((new SmithingGUI()).getInventory());
			e.setCancelled(true);
		}

	}

	@EventHandler
	public void onPlayerClickInventory(InventoryClickEvent e) {

		if(e.getView().getTitle().equalsIgnoreCase("§4Enchanted Smithing Table")) {

			e.setCancelled(true);

			Player player = (Player) e.getWhoClicked();
			Inventory inv = e.getInventory();
			int rawSlot = e.getRawSlot();
			ItemStack item = e.getCurrentItem();
			SmithingGUI gui = new SmithingGUI(inv);
			
			if(item == null)
				return;
			
			if(rawSlot >= 45) {
				
				if(!gui.isSetIngredient1()) {
					
					if(isAccetable(item)) {
						inv.setItem(20, item);
						player.getInventory().remove(item);
					}
				}

				else {

					if(isAccetable(item, inv.getItem(20))) {
						
						player.getInventory().remove(item);
						
						ItemStack result = new ItemStack(item.getType());
						ItemStack ing1 = inv.getItem(20);
						result.addEnchantments(ing1.getEnchantments());

						inv.setItem(22, item);
						inv.setItem(24, result);
						inv.setItem(38, Utility.cancel);
						inv.setItem(42, Utility.accept);
					}

				}

			}

			else {

				int slot = e.getSlot();

				if(slot == 20) {

					if(gui.isSetIngredient1()) {

						player.getInventory().addItem(item);
						ItemStack ingredient2 = inv.getItem(22);

						if(gui.isSetIngredient2()) {
							player.getInventory().addItem(ingredient2);
							inv.setItem(22, PanesGUI.ingredient);
						}

						inv.setItem(20, PanesGUI.ingredient);

						inv.setItem(38, CornerBlack.corner);
						inv.setItem(42, CornerBlack.corner);
						inv.setItem(24, PanesGUI.result);
						
					}

				}

				else if(slot == 22) {

					if(gui.isSetIngredient2()) {

						player.getInventory().addItem(item);
						inv.setItem(22, PanesGUI.ingredient);

						inv.setItem(38, CornerBlack.corner);
						inv.setItem(42, CornerBlack.corner);
						inv.setItem(24, PanesGUI.result);

					}

				}

				else if(slot == 42) {

					if(item.getType().equals(Utility.accept.getType())) {

						ItemStack ingredient1 = inv.getItem(20);
						for(Enchantment enchant : ingredient1.getEnchantments().keySet())
							ingredient1.removeEnchantment(enchant);

						ItemStack result = inv.getItem(24);

						player.getInventory().addItem(ingredient1);
						player.getInventory().addItem(result);

						inv.setItem(20, PanesGUI.ingredient);
						inv.setItem(22, PanesGUI.ingredient);
						inv.setItem(24, PanesGUI.result);
						inv.setItem(38, CornerBlack.corner);
						inv.setItem(42, CornerBlack.corner);

					}

				}

				else if(slot == 38) {

					if(item.getType().equals(Utility.cancel.getType())) {
					
						ItemStack ingredient1 = inv.getItem(20);
						ItemStack ingredient2 = inv.getItem(22);

						player.getInventory().addItem(ingredient1);
						player.getInventory().addItem(ingredient2);

						inv.setItem(20, PanesGUI.ingredient);
						inv.setItem(22, PanesGUI.ingredient);
						inv.setItem(24, PanesGUI.result);
						inv.setItem(38, CornerBlack.corner);
						inv.setItem(42, CornerBlack.corner);
					
					}

				}

				else if(slot == 44) {

					ItemStack ingredient1 = inv.getItem(20);
					ItemStack ingredient2 = inv.getItem(22);

					if(gui.isSetIngredient1())
						player.getInventory().addItem(ingredient1);
					if(gui.isSetIngredient1())
						player.getInventory().addItem(ingredient2);

					player.closeInventory();
					
					int range = 10;
					boolean found = false;
					do {
						for(Entity entity : player.getNearbyEntities(range, range, range))
							if(entity.getCustomName() != null && entity.getCustomName().equalsIgnoreCase("§4Enchanted Smithing Table")) {
								found = true;
								if(entity instanceof LivingEntity) {
									LivingEntity living = (LivingEntity) entity;
									living.setInvulnerable(false);
									living.damage(100);
									player.getInventory().addItem(SmithingEgg.egg);
									return;
								}
							}

						range += 5;

					} while(!found && range <= 100);

				}

			}

		}


	}


	private boolean isAccetable(ItemStack item) {

		if(item.getType().toString().toUpperCase().indexOf("WOODEN") != -1 || 
				item.getType().toString().toUpperCase().indexOf("STONE") != -1 ||
				item.getType().toString().toUpperCase().indexOf("IRON") != -1 ||
				item.getType().toString().toUpperCase().indexOf("GOLD") != -1 ||
				item.getType().toString().toUpperCase().indexOf("LEATHER") != -1 ||
				item.getType().toString().toUpperCase().indexOf("CHAINMAIL") != -1)
			if((item.hasItemMeta() && item.getItemMeta() instanceof Damageable) || !item.getEnchantments().isEmpty())
				if(item.getItemMeta().hasEnchants())
					return true;

		return false;
	}

	private boolean isExtendedAccetable(ItemStack item) {

		if(item.getType().toString().toUpperCase().indexOf("WOODEN") != -1 || 
				item.getType().toString().toUpperCase().indexOf("STONE") != -1 ||
				item.getType().toString().toUpperCase().indexOf("IRON") != -1 ||
				item.getType().toString().toUpperCase().indexOf("GOLD") != -1 ||
				item.getType().toString().toUpperCase().indexOf("LEATHER") != -1 ||
				item.getType().toString().toUpperCase().indexOf("CHAINMAIL") != -1 ||
				item.getType().toString().toUpperCase().indexOf("DIAMOND") != -1)
			if((item.hasItemMeta() && item.getItemMeta() instanceof Damageable) || item.getEnchantments().isEmpty())
				if(!item.getItemMeta().hasEnchants())
					return true;

		return false;
	}

	private boolean isAccetable(ItemStack item, ItemStack ingredient1) {

		if(isExtendedAccetable(item)) {
			String ingredient = ingredient1.getType().toString().substring(0, ingredient1.getType().toString().indexOf("_")).toUpperCase();
			String mat = item.getType().toString().substring(0, item.getType().toString().indexOf("_")).toUpperCase();
			
			if(ingredient1.getType().toString().substring(ingredient1.getType().toString().indexOf("_") + 1).equalsIgnoreCase(
					item.getType().toString().substring(item.getType().toString().indexOf("_") + 1)))
				if(ingredient.equalsIgnoreCase("WOODEN") && mat.equalsIgnoreCase("STONE") ||
						ingredient.equalsIgnoreCase("STONE") && mat.equalsIgnoreCase("IRON") ||
						ingredient.equalsIgnoreCase("STONE") && mat.equalsIgnoreCase("GOLDEN") ||
						ingredient.equalsIgnoreCase("GOLDEN") && mat.equalsIgnoreCase("IRON") ||
						ingredient.equalsIgnoreCase("IRON") && mat.equalsIgnoreCase("DIAMOND") ||
						ingredient.equalsIgnoreCase("LEATHER") && mat.equalsIgnoreCase("GOLDEN") ||
						ingredient.equalsIgnoreCase("CHAINMAIL") && mat.equalsIgnoreCase("GOLDEN"))
					return true;
		}

		return false;
	}

}
