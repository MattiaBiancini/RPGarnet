package me.rpgarnet.recipe.item.transposer;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.TileState;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import me.rpgarnet.RPGarnet;
import me.rpgarnet.utils.TimeUtils;

public class STCrafting implements Listener {
	
	private static Map<Player, Long> cooldown = new HashMap<>();
	private static final int COOLDOWN = 2;
	
	public static ShapedRecipe stcrafting() {

		ShapedRecipe transposer = new ShapedRecipe(new NamespacedKey(RPGarnet.instance, "spawner_transposer"), new SpawnerTransposer());
		transposer.shape("SAS","SES","AOA");
		transposer.setIngredient('S', Material.STICK);
		transposer.setIngredient('E', Material.DRAGON_EGG);
		transposer.setIngredient('A', Material.AIR);
		transposer.setIngredient('O', Material.OBSIDIAN);

		return transposer;
	}
	
	@EventHandler
	public void onSpawnerClick(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		
		if(e.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;

		if(e.getClickedBlock() == null || e.getClickedBlock().getType() == Material.AIR)
			return;

		if(player.getInventory().getItemInMainHand() == null)
			return;

		ItemStack item = player.getInventory().getItemInMainHand();
		
		Block block = e.getClickedBlock();
		
		if(item.getType() == SpawnerTransposer.transposer.getType() && item.hasItemMeta() 
				&& item.getItemMeta().getDisplayName().equalsIgnoreCase(SpawnerTransposer.transposer.getItemMeta().getDisplayName())) {
			
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
			
			if(item.getItemMeta().getCustomModelData() == 1) {
				
				if(!(block.getType() == Material.SPAWNER))
					return;
				
				e.setCancelled(true);
				
				SpawnerTransposer.updateSpawner(item, ((CreatureSpawner) block.getState()).getSpawnedType().toString());
				
				block.setType(Material.AIR);
				
				return;
			
			}
			
			else {
				
				if(canPlaceBlock(block, e.getBlockFace(), e.getPlayer().isSneaking())) {
					
					e.setCancelled(true);
					
					Block spawner = block.getLocation().getWorld().getBlockAt(getLocationToPlaceBlock(block, e.getBlockFace()));
					
					spawner.setType(Material.SPAWNER);
					
					spawner = block.getLocation().getWorld().getBlockAt(getLocationToPlaceBlock(block, e.getBlockFace()));
					
					CreatureSpawner state = (CreatureSpawner) spawner.getState();
					
					state.setSpawnedType(EntityType.valueOf(SpawnerTransposer.getSpawnerType(item)));
					state.update();
					
					SpawnerTransposer.updateSpawner(item, "NONE");
					
				}
				
			}
			
			
		}
		
	}
	
	private boolean canPlaceBlock(Block block, BlockFace face, boolean sneak) {
		
		boolean can = !(block.getState() instanceof TileState);
		
		if(sneak)
			can = true;
		
		Location loc = block.getLocation();
		
		switch(face) {
			case UP:
				loc = loc.add(0, 1, 0);
				can = can && block.getWorld().getBlockAt(loc).getType() == Material.AIR;
				break;
			case DOWN:
				loc = loc.add(0, -1, 0);
				can = can && block.getWorld().getBlockAt(loc).getType() == Material.AIR;
				break;
			case EAST:
				loc = loc.add(1, 0, 0);
				can = can && block.getWorld().getBlockAt(loc).getType() == Material.AIR;
				break;
			case WEST:
				loc =loc.add(-1, 0, 0);
				can = can && block.getWorld().getBlockAt(loc).getType() == Material.AIR;
				break;
			case NORTH:
				loc = loc.add(0, 0, -1);
				can = can && block.getWorld().getBlockAt(loc).getType() == Material.AIR;
				break;
			case SOUTH:
				loc = loc.add(0, 0, 1);
				can = can && block.getWorld().getBlockAt(loc).getType() == Material.AIR;
				break;
			default:
				break;
		}
		
		return can;
		
	}
	
	private Location getLocationToPlaceBlock(Block block, BlockFace face) {
		
		switch(face) {
			case UP:
				return block.getLocation().add(0, 1, 0);
			case DOWN:
				return block.getLocation().add(0, -1, 0);
			case EAST:
				return block.getLocation().add(1, 0, 0);
			case WEST:
				return block.getLocation().add(-1, 0, 0);
			case NORTH:
				return block.getLocation().add(0, 0, -1);
			case SOUTH:
				return block.getLocation().add(0, 0, 1);
			default:
				break;
		}
		
		return block.getLocation();
		
	}
	
}
