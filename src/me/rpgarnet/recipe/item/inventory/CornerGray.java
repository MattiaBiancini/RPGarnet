package me.rpgarnet.recipe.item.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.rpgarnet.utils.StringUtils;

public class CornerGray extends ItemStack {
	
	public static final CornerGray corner = new CornerGray();
	
	private CornerGray() {
		
		super(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta paneCornerM = this.getItemMeta();
		paneCornerM.setDisplayName(StringUtils.colorFixing("&7"));
		this.setItemMeta(paneCornerM);
		
	}
	
}
