package me.rpgarnet.recipe.item.smithingplus;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SmithingEgg extends ItemStack {

	public static final SmithingEgg egg = new SmithingEgg();

	private SmithingEgg() {

		super(Material.ECHO_SHARD);
		ItemMeta smithingM = this.getItemMeta();

		smithingM.setDisplayName("§4Enchanted Smithing Table");
		smithingM.setCustomModelData(1);

		this.setItemMeta(smithingM);

	}

}
