package fr.itsthesky.Tinker;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TinkerCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
            Tinker.sendConsole("§cThis command can only be executed by player!");
            return false;
        }
        Player player = (Player)sender;

        if (args.length < 1) {
            player.sendMessage("§8------------ [§6Tinker§8] ------------");
            player.sendMessage("§6Available Commands:");
            player.sendMessage("		§e/tinker reload §7- §cReload Tinker's item file.");
            player.sendMessage("		§e/tinker give <id> §7- §cGive Tinker's Item with specific ID.");
            player.sendMessage("		§e/tinker list §7- §cReturn all Tinker Items loaded with their ID.");
            player.sendMessage("§8------------ [§6Tinker§8] ------------");
            return true;
        }


        if (args[0].equalsIgnoreCase("reload")) {
            Tinker.sendPlayer(player, "§eReloading §6Tinker§e's items file ...");
            FileManager.reloadItems();
            int size = (FileManager.getLoadedItems()).length;
            Tinker.sendPlayer(player, "§aReload complete! Loaded a total of §2" + size + " Tinker Items§a! See console for any errors!");
            return true;
        }

        if (args[0].equalsIgnoreCase("list")) {
            player.sendMessage("§8------------ [§6Tinker§8] ------------");
            player.sendMessage("§6Loaded Items (" + (FileManager.getLoadedItems()).length + "):");
            for (String s : FileManager.getLoadedItems()) {
                player.sendMessage("  §e" + s);
            }
            player.sendMessage("§8------------ [§6Tinker§8] ------------");
            return true;
        }


        if (args[0].equalsIgnoreCase("give")) {
            if (args.length < 2) {
                Tinker.sendPlayer(player, "§cYou must choose an Item to give! See list of loaded item with §4/tinker list§c !");
                return false;
            }
            ItemStack finalItem = ItemBuilder.buildItem(args[1]);
            if (finalItem.getType() == Material.AIR) {
                Tinker.sendPlayer(player, "§cCannot found this item! Do you have been reloaded Tinker? See list of loaded item with §4/tinker list§c. This error can also be shown if the material used for the item is not §4valid§c!");
                return false;
            }
            player.getInventory().addItem(new ItemStack[] { finalItem });
            Tinker.sendPlayer(player, "§aItem given without any errors!");
            return true;
        }
        return false;
    }
}