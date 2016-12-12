package it.ItzSiL3Nce.warpmanager.configuration;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;

public final class OldConfigUpdater {

	public static final void update() {
		boolean mod = false;
		File f = new File("plugins/WarpManager/config.yml");
		FileConfiguration fc = Config.get(f, "config.yml");
		if(!fc.contains("Enable warp sign")){
			fc.set("Enable warp sign", true);
			mod = true;
		}
		if(!fc.contains("Sign first line")){
			fc.set("Sign first line", "&1[Warp]");
			mod = true;
		}
		if(mod){
			try {
				fc.save(f);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
