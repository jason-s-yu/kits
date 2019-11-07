package kits;

import java.io.File;
import java.util.ArrayList;

import oreeconomy.OreEconomy;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main main;

	// Files
	File configurationConfig;
	public FileConfiguration config;
	File playerData;
	public FileConfiguration pdata;

	// Strings
	String prefix = "";

	// Lists
	ArrayList<String> delays = new ArrayList<String>();

	OreEconomy economy;

	public static Main getMain() {
		return main;
	}

	public void onEnable() {
		main = this;
		configurationConfig = new File(getDataFolder(), "config.yml");
		config = YamlConfiguration.loadConfiguration(configurationConfig);
		playerData = new File(getDataFolder(), "playerData.yml");
		pdata = YamlConfiguration.loadConfiguration(playerData);
		loadConfig();
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
		getCommand("kit").setExecutor(new KitCommand());
		getCommand("kits").setExecutor(new KitsCommand());
		getCommand("kitsreload").setExecutor(new KRCommand());
		economy = OreEconomy.getMain();
	}

	public void savec() {
		try {
			config.save(configurationConfig);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void savepd() {
		try {
			pdata.save(playerData);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadConfig() {
		config.addDefault("kit.example.crackshot", false);
		config.addDefault("kit.example.cost", 10);
		config.addDefault("kit.example.delay", 2);
		ArrayList<String> i = new ArrayList<String>();
		i.add("STICK,5,Example_Item,this_is_the_lore-this_is_line_number_2,DAMAGE_ALL-2=");
		config.addDefault("kit.example.items", i);
		ArrayList<String> l = new ArrayList<String>();
		l.add("example");
		config.addDefault("kits", l);
		config.addDefault("messages.noperm",
				"&4You don't have permissions for this.");
		config.addDefault("messages.onlyplayer",
				"&4You have to be a player to do this.");
		config.addDefault("prefix", "");
		config.options().copyDefaults(true);
		pdata.options().copyDefaults(true);
		savec();
		savepd();
		prefix = config.getString("prefix");
		;
	}

}
