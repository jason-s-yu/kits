package kits;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {

	Main plugin = Main.getMain();
	Messages msg = Messages.getInstance();
	Methods methods = Methods.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2,
			String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (args.length == 0) {
				methods.listKits(player);
			}
			if (args.length == 1) {
				List<String> kitlist = methods.getKits();
				String kit = args[0].toLowerCase();
				if (kitlist.contains(kit)) {
					if (player.hasPermission("kit." + kit.toLowerCase())
							|| player.hasPermission("kit.*")) {
						double cost = plugin.config.getDouble("kit."
								+ kit.toLowerCase() + ".cost");
						if (plugin.economy.methods.getMoney(player) >= cost) {
							methods.giveKit(player, kit.toLowerCase());
							plugin.economy.methods.takeMoney(player, cost);
						}
					} else {
						msg.msg(player, "noperm");
					}
				} else {
					msg.msg(player, "null");
				}
			} else if (args.length > 1) {
				if (player.hasPermission("kits.admin")) {
					List<String> kitlist = methods.getKits();
					String kit = args[0].toLowerCase();
					Player target = Bukkit.getPlayer(args[1]);
					if (kitlist.contains(kit)) {
						if (player.hasPermission("kit." + kit.toLowerCase())
								|| player.hasPermission("kit.*")) {
							double cost = plugin.config.getDouble("kit."
									+ kit.toLowerCase() + ".cost");
							if (plugin.economy.methods.getMoney(player) >= cost) {
								methods.giveKit(target, kit.toLowerCase());
								plugin.economy.methods.takeMoney(player, cost);
							}
						} else {
							msg.msg(player, "noperm");
						}
					} else {
						msg.msg(player, "null");
					}
				}
			}
		} else {

		}
		return false;
	}

}
