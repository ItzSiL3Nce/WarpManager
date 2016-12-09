package it.ItzSiL3Nce.warpmanager;

import it.ItzSiL3Nce.warpmanager.exceptions.InvalidWarpException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Warp {

	private Location location = null;
	private String warpname = "";

	private Warp() {}

	public static final class WarpInfo {
		
		protected final Location location;
		protected final String warpname;
		protected final String creator;
		
		public WarpInfo(String name, Location location){
			this(name, location, "");
		}
		
		public WarpInfo(String name, Location location, String creator){
			if(name == null || name.trim().isEmpty() || location == null)
				throw new InvalidWarpException("Warp must have a non-null name and location.");
			this.location = location;
			this.warpname = name;
			if(creator == null || creator.trim().isEmpty())
				this.creator = null;
			else this.creator = creator;
		}
		
		public WarpInfo(String name, Location location, Player creator){
			this(name, location, creator == null ? "" : creator.getName());
		}
		
		public WarpInfo(String name, Player creator){
			this(name, creator == null ? null : creator.getLocation(), creator);
		}
	}
	
	public Location getTeleportPosition() {
		return location;
	}

	public String getWarpName() {
		return warpname;
	}
	
	public static boolean exists(String warpname){
		return get(warpname) != null;
	}

	private static boolean generalDel(File f, String warpname){
		if(f.exists()){
			FileConfiguration fc = YamlConfiguration.loadConfiguration(f);
			if(fc.contains("Warps." + warpname.toLowerCase())){
				fc.set("Warps." + warpname.toLowerCase(), null);
				try {
					fc.save(f);
					return true;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		} return false;
	}
	
	public static boolean delete(String warpname) {
		return generalDel(WarpManager.WARPS, warpname) || generalDel(WarpManager.ESSWARPS, warpname);
	}

	private static Warp generalGet(File f, String warpname) {
		Warp w = null;
		if (f.exists()) {
			FileConfiguration fc = YamlConfiguration
					.loadConfiguration(f);
			if (fc.contains("Warps." + warpname.toLowerCase())) {
				World world = null;
				double x = 0d;
				double y = 0d;
				double z = 0d;
				float pitch = 0f;
				float yaw = 0f;
				String p = "Warps." + warpname.toLowerCase() + ".";
				if (fc.contains(p + "World"))
					world = WarpManager.instance.getServer().getWorld(
							fc.getString(p + "World"));
				if (fc.contains(p + "X"))
					x = fc.getDouble(p + "X");
				if (fc.contains(p + "Y"))
					y = fc.getDouble(p + "Y");
				if (fc.contains(p + "Z"))
					z = fc.getDouble(p + "Z");
				if (fc.contains(p + "Pitch"))
					pitch = (float) fc.getDouble(p + "Pitch");
				if (fc.contains(p + "Yaw"))
					yaw = (float) fc.getDouble(p + "Yaw");
				w = new Warp();
				w.location = new Location(world, x, y, z);
				w.location.setPitch(pitch);
				w.location.setYaw(yaw);
				w.warpname = warpname;
			}
		}
		return w;
	}
	
	public static Warp get(String warpname) {
		Warp w = generalGet(WarpManager.WARPS, warpname);
		if(w == null)
			w = generalGet(WarpManager.ESSWARPS, warpname);
		return w;
	}

	@SafeVarargs
	public static boolean set(String warpname, Location loc, Map<String, Object>...params) {
		if (warpname != null && !warpname.trim().isEmpty() && loc != null) {
			File f = new File("plugins/WarpManager/");
			if(!f.exists()) f.mkdirs();
			if (!WarpManager.WARPS.exists()) {
				try {
					WarpManager.WARPS.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			FileConfiguration fc = YamlConfiguration
					.loadConfiguration(WarpManager.WARPS);
			String p = "Warps." + warpname.toLowerCase() + ".";
			fc.set(p + "World", loc.getWorld().getName());
			fc.set(p + "X", loc.getX());
			fc.set(p + "Y", loc.getY());
			fc.set(p + "Z", loc.getZ());
			fc.set(p + "Pitch", loc.getPitch());
			fc.set(p + "Yaw", loc.getYaw());
			if(params != null && params.length > 0){
				for(Map<String, Object> m: params){
					for(String k: m.keySet()){
						if(k != null && m.get(k) != null)
							fc.set(p + k, m.get(k));
					}
				}
			}
			try {
				fc.save(WarpManager.WARPS);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	public static boolean set(WarpInfo warp){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("Creator", warp.creator);
		return set(warp.warpname, warp.location, params);
	}
	
	public static WarpInfo getInfo(Warp warp){
		return new WarpInfo(warp.warpname, warp.location);
	}
}
