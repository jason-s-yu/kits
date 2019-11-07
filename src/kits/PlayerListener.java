package kits;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {

	private static PlayerListener instance = new PlayerListener();

	Main plugin = Main.getMain();
	Messages msg = Messages.getInstance();
	Methods methods = Methods.getInstance();

	public static PlayerListener getInstance() {
		return instance;
	}

	@EventHandler
	public void onSignChange(SignChangeEvent event) {
		Player player = event.getPlayer();
		if (player.hasPermission("kits.admin")) {
			if (event.getLine(0).equalsIgnoreCase("[Kit]")) {
				event.setLine(0, ChatColor.BLUE + event.getLine(0));
				ArrayList<String> kl = new ArrayList<String>();
				kl.addAll(plugin.config.getStringList("kits"));
				if (kl.contains(event.getLine(1))) {
					int cost = plugin.config.getInt("kit." + event.getLine(1)
							+ ".cost");
					event.setLine(2, "" + cost);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block b = e.getClickedBlock();
			if (b.getType() == Material.WALL_SIGN
					|| b.getType() == Material.SIGN_POST) {
				Sign sign = (Sign) b.getState();
				String[] lines = sign.getLines();
				if (ChatColor.stripColor(lines[0]).equalsIgnoreCase("[Kit]")) {
					String kit = lines[1];
					double cost = Double.parseDouble(lines[2]);
					if (plugin.economy.methods.getMoney(player) >= cost) {
						methods.giveKit(player, kit);
						plugin.economy.methods.takeMoney(player, cost);
					}
				}
			}
		}
	}
}