package me.rpgarnet.recipe.item.transposer;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class SpawnerTransposer extends ItemStack {

	public static final SpawnerTransposer transposer = new SpawnerTransposer();

	public SpawnerTransposer() {

		super(Material.GOLDEN_PICKAXE);
		Damageable meta = (Damageable) this.getItemMeta();
		
		meta.setDisplayName("§fSpawner Transporter");
		
		List<String> lore = new ArrayList<>();
		lore.add("§7Spawner§8» §eNONE");
		lore.add("§7");
		lore.add("§7Uses Left§8»§e 8");
		meta.setLore(lore);
		
		meta.setUnbreakable(true);
		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		
		meta.setCustomModelData(1);

		this.setItemMeta(meta);

	}
	
	public static int getUsesLeft(ItemStack item) {
		
		List<String> lore = item.getItemMeta().getLore();
		
		for(String s : lore) {
			if(s.indexOf("Uses Left") != -1)
				return Integer.parseInt(s.substring(s.lastIndexOf(" ") + 1));
		}
		
		return -1;
		
	}
	
	public static int damage(ItemStack item) {
		
		ItemMeta meta = item.getItemMeta();
		List<String> lore = item.getItemMeta().getLore();
		
		for(int i = 0; i < lore.size(); i++) {
			String s = lore.get(i);
			if(s.indexOf("Uses Left") != -1) {
				int usesLeft = getUsesLeft(item) - 1;
				if(usesLeft == 0) {
					item.setAmount(0);
					return 0;
				}
				s = s.substring(0, s.length() - 1) + (getUsesLeft(item) - 1);
				lore.set(i, s);
				meta.setLore(lore);
				item.setItemMeta(meta);
				return usesLeft;
			}
			
		}
		
		return -1;
		
	}
	
	public static void updateSpawner(ItemStack item, String type) {
		
		if(type.equalsIgnoreCase("NONE")) {
			
			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.getLore();
			
			lore.set(0, "§7Spawner§8» §eNONE");
			
			meta.setLore(lore);
			meta.setCustomModelData(1);
			item.setItemMeta(meta);
			damage(item);
		}
		else {
			
			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.getLore();
			
			lore.set(0, lore.get(0).replace("NONE", type));
			
			meta.setLore(lore);
			meta.setCustomModelData(2);
			
			item.setItemMeta(meta);
		}
		
	}
	
	public static String getSpawnerType(ItemStack item) {
		
		return item.getItemMeta().getLore().get(0).substring(item.getItemMeta().getLore().get(0).lastIndexOf("§e") + 2);
		
	}

}
