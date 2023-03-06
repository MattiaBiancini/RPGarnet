package me.rpgarnet.recipe.item.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utility extends ItemStack {
	
	public static final Utility destroy = new Utility("§cDestroy", Material.BARRIER);
	public static final Utility cancel = new Utility("§cCancel", Material.RED_TERRACOTTA);
	public static final Utility accept = new Utility("§aAccept", Material.LIME_TERRACOTTA);
	
	private Utility(String string, Material mat) {
		
		super(mat);
		ItemMeta paneCornerM = this.getItemMeta();
		paneCornerM.setDisplayName(string);
		this.setItemMeta(paneCornerM);
		
	}

}
