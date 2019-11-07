package kits;

import java.util.ArrayList;
import java.util.List;
import com.shampaggon.crackshot.CSUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Methods {

	private static Methods instance = new Methods();

	public static Methods getInstance() {
		return instance;
	}

	Main plugin = Main.getMain();
	Messages msg = Messages.getInstance();
	CSUtility csu;

	public void listKits(Player player) {
		ArrayList<String> kits = new ArrayList<String>();
		kits.addAll(plugin.config.getStringList("kits"));
		String list = "Availible Kits: ";
		for (String s : kits) {
			list = list + s + ", ";
		}
		msg.msg(player, list);
	}

	public List<String> getKits() {
		return plugin.config.getStringList("kits");
	}

	public void giveKit(final Player player, final String kit) {
		if (plugin.config.getInt("kit." + kit + ".delay") < 0) {
			if (plugin.pdata.getStringList(player.getUniqueId().toString() + ".used").contains(kit)) {
				// TODO ERROR ONE TIME USE
				return;
			}
		}
		if (plugin.delays.contains(player.getName() + "," + kit)) {
			// TODO ERROR COOLDOWN
			return;
		}
		if (plugin.config.getBoolean("kit." + kit + ".crackshot")) {
			List<String> items = plugin.config.getStringList("kit." + kit + ".items");
			for (String s : items) {
				csu.giveWeapon(player, s.split(",")[0], Integer.parseInt(s.split(",")[1]));
			}
			int delay = plugin.config.getInt("kit." + kit + ".delay");
			plugin.delays.add(player.getName() + "," + kit);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					plugin.delays.remove(player.getName() + "," + kit);
				}
			}, delay * 20 * 60);
		} else {
			List<String> items = plugin.config.getStringList("kit." + kit + ".items");
			for (String s : items) {
				ItemStack is = new ItemStack(Material.getMaterial(s.split(",")[0]));
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(ChatColor.translateAlternateColorCodes('&', s.split(",")[2]).replace("_", " "));
				is.setAmount(Integer.parseInt(s.split(",")[1]));
				ArrayList<String> l = new ArrayList<String>();
				String ls = s.split(",")[3];
				String[] lore = ls.split("-");
				for (String str : lore) {
					l.add(ChatColor.translateAlternateColorCodes('&', str.replace("_", " ")));
				}
				im.setLore(l);
				String enchants = s.split(",")[4];
				ArrayList<String> enchs = new ArrayList<String>();
				int length = enchants.split("=").length;
				for (int i = 0; i < length; ++i) {
					enchs.add(enchants.split("=")[i]);
				}
				for (String str : enchs) {
					im.addEnchant(Enchantment.getByName(str.split("-")[0]), Integer.parseInt(str.split("-")[1]), true);
				}
				is.setItemMeta(im);
				player.getInventory().addItem(is);
			}
			int delay = plugin.config.getInt("kit." + kit + ".delay");
			plugin.delays.add(player.getName() + "," + kit);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					plugin.delays.remove(player.getName() + "," + kit);
				}
			}, delay * 20 * 60);
		}
	}
}
