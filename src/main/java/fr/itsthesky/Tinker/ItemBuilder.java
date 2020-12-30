package fr.itsthesky.Tinker;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
	public static ItemStack buildItem(String node) {
		Material verifItem = Material.getMaterial((String)FileManager.checkFile(node + ".Material"));
		if (verifItem == null) {
			Tinker.sendConsole("§cCannot found the item with ID `§4" + node + "§c`, or the Material used for this item is not valid!");
			return customItem(Material.AIR, null, null);
		}

		Material material = Material.getMaterial((String)FileManager.checkFile(node + ".Material"));
		String name = (String)FileManager.checkFile(node + ".Name");
		List<String> lore = (List<String>)FileManager.checkFile(node + ".Lore");

		ItemStack item = customItem(material, name, lore);

		String[] mods = FileManager.getAllModifiers(node);
		if (mods.length != 0) {

			item = addLore(item, "§8");
			item = addLore(item, "§8§l» §eModifiers:");

			for (String s : mods) {
				Integer level = (Integer)FileManager.checkFile(node + ".Modifiers." + s);
				item = addModifier(item, s.toUpperCase(), level);
			}
		}


		List<String> flags = null;
		flags.add("HideEnchantments");
		flags.add("HideModifiers");
		flags.add("Unbreakable");
		flags.add("HideCanDestroy");
		flags.add("HideCanPlace");
		flags.add("HideOther");
		flags.add("HideTooltip");
		for (String s : flags) {

			s.replace("Hide", "");
			Integer flagsByte = Integer.valueOf(0);
			if (((Boolean)FileManager.checkFile(node + "Flags" + s)).booleanValue()) {
				switch (s) {
					case "HideEnchantments":
						flagsByte = Integer.valueOf(flagsByte.intValue() + 1);
						break;
					case "HideModifiers":
						flagsByte = Integer.valueOf(flagsByte.intValue() + 2);
						break;
					case "Unbreakable":
						flagsByte = Integer.valueOf(flagsByte.intValue() + 4);
						break;
					case "HideCanDestroy":
						flagsByte = Integer.valueOf(flagsByte.intValue() + 8);
						break;
					case "HideCanPlace":
						flagsByte = Integer.valueOf(flagsByte.intValue() + 16);
						break;
					case "HideOther":
						flagsByte = Integer.valueOf(flagsByte.intValue() + 32);
						break;
					case "HideTooltip":
						flagsByte = Integer.valueOf(flagsByte.intValue() + 64);
						break;
				}
				/* NBTItem nbti = new NBTItem(item);
				nbti.setInteger("HideFlags", flagsByte);
				ItemStack fitem = NBTItem.convertNBTtoItem((NBTCompound)nbti); */

				return item;
			}
		}


		return item;
	}

	public static ItemStack addLore(ItemStack item, String line) {
		ItemMeta tempmeta = item.getItemMeta();
		List<String> lores = tempmeta.getLore();
		lores.add(line);
		tempmeta.setLore(lores);
		item.setItemMeta(tempmeta);
		return item;
	}

	public static ItemStack customItem(Material material, String name, List<String> lore) {
		ItemStack item = new ItemStack(material, 1);
		ItemMeta meta = item.getItemMeta();
		if (lore != null) {
			for (int i = 0; i < lore.size(); i++) {
				lore.set(i, ((String)lore.get(i)).replace("&", "§"));
			}
			meta.setLore(lore);
		}
		if (name != null) {
			meta.setDisplayName(name.replace("&", "§"));
		}
		item.setItemMeta(meta);
		return item;
	}


	public static ItemStack addModifier(ItemStack item, String mod, Integer level) {
		String color = null;

		switch (mod) {
			case "LIFESTEAL":
				color = "§4";
				break;
			case "SHARPNESS":
				color = "§c";
				item.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, level.intValue());
				break;
			case "DURABILITY":
				color = "§b";
				item.addUnsafeEnchantment(Enchantment.DURABILITY, level.intValue());
				break;
			case "FIERY":
				color = "§6";
				item.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, level.intValue());
				break;
		}

		mod = mod.toLowerCase();
		String name = mod.substring(0, 1).toUpperCase() + mod.substring(1);

		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		lore.add("  §7▶ " + color + name + " " + Tinker.integerToRoman(level.intValue()));
		meta.setLore(lore);
		item.setItemMeta(meta);

		return item;
	}
}
