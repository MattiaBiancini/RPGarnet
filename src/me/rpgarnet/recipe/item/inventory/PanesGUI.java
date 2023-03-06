package me.rpgarnet.recipe.item.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PanesGUI extends ItemStack {
	
	public static final PanesGUI ingredient = new PanesGUI("§eINGREDIENT", Material.YELLOW_STAINED_GLASS_PANE);
	public static final PanesGUI result = new PanesGUI("§aRESULT", Material.LIME_STAINED_GLASS_PANE);
	public static final PanesGUI modifier = new PanesGUI("§5MODIFIER", Material.MAGENTA_STAINED_GLASS_PANE);
	
	private PanesGUI(String string, Material mat) {
		
		super(mat);
		ItemMeta paneCornerM = this.getItemMeta();
		paneCornerM.setDisplayName(string);
		this.setItemMeta(paneCornerM);
		
	}

}
