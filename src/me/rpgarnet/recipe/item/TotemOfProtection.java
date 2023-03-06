package me.rpgarnet.recipe.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TotemOfProtection extends ItemStack {
	
	public static final TotemOfProtection totem = new TotemOfProtection();
	
	private TotemOfProtection() {
		
		super(Material.TOTEM_OF_UNDYING);
		
		ItemMeta meta = this.getItemMeta();
		
		meta.setDisplayName("§eTotem of Protection");
		
		List<String> lore = new ArrayList<>();
		lore.add("§7With this item in §eyour inventory");
		lore.add("§7or §eyour enderchest§7, when you die");
		lore.add("§7you will not lose your §eStats§7!");
		meta.setLore(lore);
		
		meta.setCustomModelData(1);
		
		this.setItemMeta(meta);
	}

}
