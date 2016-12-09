package it.ItzSiL3Nce.warpmanager.configuration;

import it.ItzSiL3Nce.warpmanager.WarpManager;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public final class Config {

	public static final FileConfiguration get(File f, String s){
		if(f == null || s == null) return null;
		if(!f.exists()){
			try {
				if(WarpManager.instance.getResource(s) != null)
					WarpManager.instance.saveResource(s, true);
				else f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return YamlConfiguration.loadConfiguration(f);
	}
	
	public static final FileConfiguration get(File f){
		if(f == null) return null;
		return get(f, f.getName());
	}
	
	public static final FileConfiguration get(String s){
		if(s == null) return null;
		return get(new File(s), s);
	}
	
	public static final class Messages {
		
		private static final File f = new File("plugins/WarpManager/messages.yml");
		
		public static void init() {
			get(f, "messages.yml");
		}
		
		public static String getMessage(String path, String replacement){
			FileConfiguration fc = get(f);
			if(fc.contains(path))
				return ChatColor.translateAlternateColorCodes('&', fc.getString(path).replace("%s", replacement));
			return null;
		}
		
		public static String getMessage(String path){
			return getMessage(path, "");
		}
	}
}
