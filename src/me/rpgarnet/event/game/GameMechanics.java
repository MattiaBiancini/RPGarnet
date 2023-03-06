package me.rpgarnet.event.game;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderDragon.Phase;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.entity.EnderDragonChangePhaseEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import me.rpgarnet.recipe.item.TotemOfProtection;

public class GameMechanics implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void cobblestoneGen(BlockFormEvent e) {

		if(e.getBlock() == null)
			return;
		
		Block block = e.getBlock();
		if(block.getY() <= 0) {
			if(block.getType() == Material.LAVA && findNearbyFluid(block)) {
				block.setType(Material.COBBLED_DEEPSLATE);
				e.setCancelled(true);
			} 
			else if(block.getType() == Material.WATER && findNearbyFluid(block)) {
				block.setType(Material.DEEPSLATE);
				e.setCancelled(true);
			}
			
		}

	}

	@EventHandler
	public void onBossDeath(EntityDeathEvent e) {

		if(e.getEntityType() == EntityType.WARDEN) {
			Location location = e.getEntity().getLocation();
			ItemStack loot = TotemOfProtection.totem;
			location.getWorld().dropItem(location, loot);
		}

	}
	
	@EventHandler
	public void onDrakeDeath(EnderDragonChangePhaseEvent e) {
		
		if(e.getNewPhase() == Phase.DYING) {
			e.getEntity().getDragonBattle().getEndPortalLocation().add(0, 5, 0).getBlock().setType(Material.DRAGON_EGG);
		}
	}

	private static boolean findNearbyFluid(Block block) {
		int[][] nearby = new int[][]{{-1, 0, 0}, {0, -1, 0}, {0, 0, -1}, {1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
		int x = block.getX(), y = block.getY(), z = block.getZ();
		World world = block.getWorld();
		for (int[] i : nearby)
			if (world.getBlockAt(x + i[0], y + i[1], z + i[2]).getType() == Material.LAVA)
				return true;
		return false;
	}
	
}
