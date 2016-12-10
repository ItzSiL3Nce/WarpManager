package it.ItzSiL3Nce.warpmanager;

import it.ItzSiL3Nce.warpmanager.commands.Commands;
import it.ItzSiL3Nce.warpmanager.configuration.Config;
import it.ItzSiL3Nce.warpmanager.configuration.Config.Messages;
import it.ItzSiL3Nce.warpmanager.listeners.PlayerCommandPreprocessListener;
import it.ItzSiL3Nce.warpmanager.listeners.PlayerInteractListener;
import it.ItzSiL3Nce.warpmanager.listeners.SignChangeListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class WarpManager extends JavaPlugin {

	public static WarpManager instance;

	public static final File CONFIG = new File("plugins/WarpManager/config.yml");
	public static final File WARPS = new File("plugins/WarpManager/warps.yml");
	public static final File ESSWARPS = new File("plugins/WarpManager/esswarps.yml");

	private void sendMessage(String s) {
		getServer().getConsoleSender().sendMessage(s);
	}

	@Override
	public void onEnable() {
		sendMessage(ChatColor.BLACK + "[" + ChatColor.AQUA + "WarpManager"
				+ ChatColor.BLACK + "] " + ChatColor.GOLD + "Enabling...");
		instance = this;
		PluginManager pm = getServer().getPluginManager();
		if (!CONFIG.exists())
			saveDefaultConfig();
		if(!ESSWARPS.exists() && new File("plugins/Essentials/warps/").exists())
			convertWarps(getServer().getConsoleSender());
		Messages.init();
		getCommand("warpmanager").setExecutor(Commands.MANAGER);
		getCommand("warp").setExecutor(Commands.WARP);
		getCommand("ewarp").setExecutor(Commands.WARP);
		pm.registerEvents(new PlayerCommandPreprocessListener(), this);
		pm.registerEvents(new PlayerInteractListener(), this);
		pm.registerEvents(new SignChangeListener(), this);
		sendMessage(ChatColor.BLACK + "[" + ChatColor.AQUA + "WarpManager"
				+ ChatColor.BLACK + "] " + ChatColor.GOLD + "Enabled!");
	}

	@Override
	public void onDisable() {
		sendMessage(ChatColor.BLACK + "[" + ChatColor.AQUA + "WarpManager"
				+ ChatColor.BLACK + "] " + ChatColor.GOLD + "Disabled!");
	}

	private static Map<FileConfiguration, Iterator<String>> getConfiguredWarps() {
		Map<FileConfiguration, Iterator<String>> warps = new HashMap<FileConfiguration, Iterator<String>>();
		if (WARPS.exists()) {
			FileConfiguration fc = YamlConfiguration
					.loadConfiguration(WARPS);
			if (fc.contains("Warps") && fc.isConfigurationSection("Warps")) {
				warps.put(fc, fc.getConfigurationSection("Warps")
						.getKeys(false).iterator());
			}
		}
		
		if (ESSWARPS.exists()) {
			FileConfiguration fc = YamlConfiguration
					.loadConfiguration(ESSWARPS);
			if (fc.contains("Warps") && fc.isConfigurationSection("Warps")) {
				warps.put(fc, fc.getConfigurationSection("Warps")
						.getKeys(false).iterator());
			}
		}
		return warps;
	}
	
	public static void listWarps(CommandSender sender) {
		Map<FileConfiguration, Iterator<String>> map_warps = getConfiguredWarps();
		String st = "";
		if (warpClickEnabled()) {
			List<TextComponent> warps = new ArrayList<TextComponent>();
			List<String> unsortedWarps = new ArrayList<String>();
			TextComponent tc;
			String s;
			for(FileConfiguration fc: map_warps.keySet()){
				if (fc.contains("Warps") && fc.isConfigurationSection("Warps")) {
					Iterator<String> i = map_warps.get(fc);
					while (i.hasNext()) {
						s = i.next();
						String cs = ChatColor.translateAlternateColorCodes('&', s);
						if(!hideWarpIfNoPermission() || !permissionRequired() || (
								sender.isOp() || sender instanceof ConsoleCommandSender || 
								sender.hasPermission("warpmanager.go.*") || sender.hasPermission("warpmanager.go." + ChatColor.stripColor(cs))
								|| sender.hasPermission("essentials.warp." + ChatColor.stripColor(cs)))){
						unsortedWarps.add(Messages.getMessage("Warp prefix in list") + cs);
						st += Messages.getMessage("Warp prefix in list") + cs + ChatColor.WHITE + ", ";
						}
					}
				}
			}
			
			if(unsortedWarps.size() <= 0){
				sender.sendMessage(Messages.getMessage("No warps"));
				return;
			}
			
			Collections.sort(unsortedWarps);
			for(String sx: unsortedWarps){
				tc = new TextComponent(Messages.getMessage("Warp prefix in list") + sx);
				tc.setClickEvent(new ClickEvent(
						ClickEvent.Action.RUN_COMMAND,
						"/warpmanager go " + sx));
				warps.add(tc);
				warps.add(new TextComponent("§f, "));
			}
			
			if (sender instanceof Player) {
				sender.sendMessage(Messages.getMessage("Click a warp"));
				((Player) sender).spigot().sendMessage(
						warps.toArray(new TextComponent[warps.size()]));
				sender.sendMessage(Messages.getMessage("Command"));
			} else
				sender.sendMessage(st);
		} else {
			for(FileConfiguration fc: map_warps.keySet()){
				if (fc.contains("Warps") && fc.isConfigurationSection("Warps")) {
					Iterator<String> i = map_warps.get(fc);
					while (i.hasNext()){
						String cs = ChatColor.translateAlternateColorCodes('&', i.next());
						if(!hideWarpIfNoPermission() || !permissionRequired() || (
								sender.isOp() || sender instanceof ConsoleCommandSender || 
								sender.hasPermission("warpmanager.go.*") || sender.hasPermission("warpmanager.go." + ChatColor.stripColor(cs))
								|| sender.hasPermission("essentials.warp." + ChatColor.stripColor(cs))))
						st += Messages.getMessage("Warp prefix in list") + cs + ChatColor.WHITE + ", ";
					}
				}
			}
			if (st.isEmpty()) {
				sender.sendMessage(Messages.getMessage("No warps"));
				return;
			}
			sender.sendMessage(st);
			sender.sendMessage(Messages.getMessage("Command"));
		}
	}

	public static void convertWarps(CommandSender sender) {
		File f = new File("plugins/WarpManager/");
		if (!f.exists())
			f.mkdirs();
		if (ESSWARPS.exists())
			ESSWARPS.delete();
		try {
			ESSWARPS.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileConfiguration wfc = YamlConfiguration.loadConfiguration(WARPS);
		f = new File("plugins/Essentials/warps/");
		List<File> warps = new ArrayList<File>();
		if (f.exists() && f.isDirectory()) {
			File[] files = f.listFiles();
			for (File file : files) {
				if (file.getName().endsWith(".yml")) {
					warps.add(file);
				}
			}
		} else
			sender.sendMessage("§6Folder §bplugins/Essentials/warps/ §6wasn't found! §4§l0 §6warps converted.");
		if (warps != null && warps.size() > 0) {
			Iterator<File> i = warps.iterator();
			while (i.hasNext()) {
				File n = i.next();
				FileConfiguration fc = YamlConfiguration.loadConfiguration(n);
				String s = "Warps." + n.getName().replace(".yml", "") + ".";
				if (fc.contains("world"))
					wfc.set(s + "World", fc.getString("world"));
				if (fc.contains("x"))
					wfc.set(s + "X", fc.getDouble("x"));
				if (fc.contains("y"))
					wfc.set(s + "Y", fc.getDouble("y"));
				if (fc.contains("z"))
					wfc.set(s + "Z", fc.getDouble("z"));
				if (fc.contains("pitch"))
					wfc.set(s + "Pitch", fc.getDouble("pitch"));
				if (fc.contains("yaw"))
					wfc.set(s + "Yaw", fc.getDouble("yaw"));
			}
			try {
				wfc.save(ESSWARPS);
			} catch (IOException e) {
				e.printStackTrace();
			}
			sender.sendMessage("§6Converted §b" + warps.size() + " §6warps!");
		} else
			sender.sendMessage("§6Folder §bplugins/Essentials/warps/ §6was empty! §4§l0 §6warps converted.");
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command,
			String alias, String[] args, Location location) {
		return null;
	}
	
	public static final boolean permissionRequired() {
		if (CONFIG.exists())
			return Config.get(CONFIG).contains(
					"Require permission for each warp")
					&& Config.get(CONFIG).getBoolean(
							"Require permission for each warp");
		return false;
	}

	public static final boolean warpClickEnabled() {
		if (CONFIG.exists())
			return Config.get(CONFIG).contains("Enable warp click")
					&& Config.get(CONFIG).getBoolean("Enable warp click");
		return true;
	}
	
	public static final boolean hideWarpIfNoPermission() {
		if (CONFIG.exists())
			return Config.get(CONFIG).contains("Hide warp if no permission")
					&& Config.get(CONFIG).getBoolean("Hide warp if no permission");
		return true;
	}
	
	public static final boolean signEnabled() {
		if (CONFIG.exists())
			return Config.get(CONFIG).contains("Enable warp sign")
					&& Config.get(CONFIG).getBoolean("Enable warp sign");
		return true;
	}
}
