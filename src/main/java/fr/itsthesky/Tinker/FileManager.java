package fr.itsthesky.Tinker;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {
	public static FileConfiguration itemcf;
	public static File file;

	public static void reloadItems() {
		file = new File("plugins/Tinker", "Items.yml");
		itemcf = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
		itemcf.options().copyDefaults(true);
		ConfigurationSection configsection = itemcf.getConfigurationSection("");
		Integer size = Integer.valueOf(configsection.getKeys(false).size());
		saveConfig();
		Tinker.sendConsole("§2Reload finished! Loaded a total of §a" + size + " Tinker Item§2!");
	}

	public static String[] getLoadedItems() {
		ConfigurationSection configsection = itemcf.getConfigurationSection("");
		String[] f = (String[])configsection.getKeys(false).toArray((Object[])new String[0]);
		return f;
	}

	public static String[] getAllModifiers(String node) {
		ConfigurationSection configsection = itemcf.getConfigurationSection(node + ".Modifiers");
		String[] f = (String[])configsection.getKeys(false).toArray((Object[])new String[0]);
		return f;
	}

	public static void saveConfig() {
		try {
			itemcf.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Object checkFile(String node) {
		return itemcf.get(node);
	}

	public static void writeFile(String node, Object value) {
		itemcf.set(node, value);
		saveConfig();
	}
}