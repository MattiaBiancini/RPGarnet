package me.rpgarnet.recipe.item.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.rpgarnet.utils.StringUtils;

public class CornerBlack extends ItemStack {
	
	public static final CornerBlack corner = new CornerBlack();
	
	private CornerBlack() {
		
		super(Material.BLACK_STAINED_GLASS_PANE);
		ItemMeta paneCornerM = this.getItemMeta();
		paneCornerM.setDisplayName(StringUtils.colorFixing("&7"));
		this.setItemMeta(paneCornerM);
		
	}

}
