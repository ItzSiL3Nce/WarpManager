package it.ItzSiL3Nce.warpmanager;

import it.ItzSiL3Nce.warpmanager.Warp.WarpInfo;
import it.ItzSiL3Nce.warpmanager.configuration.Config.Messages;
import it.ItzSiL3Nce.warpmanager.events.PlayerWarpEvent;

import org.bukkit.entity.Player;

public class WarpTeleport {

	private WarpTeleport(){}
	
	public static void to(Player p, Warp w, String n){
		if(w != null && w.getTeleportPosition() != null){
			p.sendMessage(Messages.getMessage("Teleport", n));
			try {
				PlayerWarpEvent e = new PlayerWarpEvent(p, w);
				WarpManager.pm.callEvent(e);
				if(!e.isCancelled())
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
	
	public static void to(Player p, WarpInfo wi){
		to(p, new Warp(wi.location, wi.warpname));
	}
}
