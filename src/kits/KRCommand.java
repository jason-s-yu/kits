package kits;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KRCommand implements CommandExecutor {

	Main plugin = Main.getMain();

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if (sender.hasPermission("kits.reload")) {
			plugin.getServer().getPluginManager().disablePlugin(plugin);
			plugin.getServer().getPluginManager().enablePlugin(plugin);
			sender.sendMessage(ChatColor.GREEN + "KITS RELOADED");
		}
		return false;
	}

}
