package fr.itsthesky.Tinker;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Tinker extends JavaPlugin {

	private static String prefix = "§8[§6Tinker§8] ";

	public void onEnable() {
		sendConsole("§dTinker§5 by §b" + getDescription().getAuthors() + " §5v§b" + getDescription().getVersion() + " §5is now loading ...");

		File file = new File("plugins/Tinker", "Items.yml");
		FileManager.reloadItems();
		if (!file.exists()) {
			sendConsole("§eCannot found the default Item file. Create one for you ...");
			FileManager.writeFile("EXAMPLE_ITEM.Name", "&4Example &cItem");
			FileManager.writeFile("EXAMPLE_ITEM.Material", "DIAMOND_SWORD");
			FileManager.writeFile("EXAMPLE_ITEM.Modifiers.LifeSteal", "3");
			FileManager.writeFile("EXAMPLE_ITEM.Modifiers.Durability", "5");
			FileManager.writeFile("EXAMPLE_ITEM.Modifiers.Sharpness", "2");
			sendConsole("§aThe default Item file is now fully created!");
		}


		getCommand("tinker").setExecutor(new TinkerCommand());

		sendConsole("§dTinker§5 is fully loaded, without any errors found! Thanks for using, and don't forget to report any bugs with that beta state of the plugin :D");
	}


	public static void sendConsole(String message) {
		Bukkit.getConsoleSender().sendMessage(prefix + message);
	}

	public static void sendPlayer(Player player, String message) {
		player.sendMessage(prefix + message);
	}


	public static String integerToRoman(int num) {
		System.out.println("Integer: " + num);
		int[] values = { 1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1 };
		String[] romanLiterals = { "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };

		StringBuilder roman = new StringBuilder();

		for (int i = 0; i < values.length; i++) {
			while (num >= values[i]) {
				num -= values[i];
				roman.append(romanLiterals[i]);
			}
		}
		return roman.toString();
	}
}
