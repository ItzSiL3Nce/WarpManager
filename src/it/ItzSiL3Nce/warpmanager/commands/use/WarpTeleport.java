package it.ItzSiL3Nce.warpmanager.commands.use;

import it.ItzSiL3Nce.warpmanager.Warp;
import it.ItzSiL3Nce.warpmanager.configuration.Config.Messages;

import org.bukkit.entity.Player;

public class WarpTeleport {

	private WarpTeleport(){}
	
	public static void to(Player p, Warp w, String n){
		if(w != null && w.getTeleportPosition() != null){
			p.sendMessage(Messages.getMessage("Teleport", n));
			try {
				p.teleport(w.getTeleportPosition());
			} catch(Exception e){
				p.sendMessage(Messages.getMessage("Error on teleportation", n));
				e.printStackTrace();
			}
		} else p.sendMessage(Messages.getMessage("Invalid warp", n));
	}
	
	public static void to(Player p, Warp w){
		to(p, w, w.getWarpName());
	}
}
