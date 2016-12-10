package it.ItzSiL3Nce.warpmanager.listeners;

import it.ItzSiL3Nce.warpmanager.Warp;
import it.ItzSiL3Nce.warpmanager.WarpManager;
import it.ItzSiL3Nce.warpmanager.configuration.Config.Messages;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class SignChangeListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onSignChange(SignChangeEvent e){
		Player p = e.getPlayer();
		if((p.isOp() || p.hasPermission("warpmanager.sign.create")) && WarpManager.signEnabled()){
			if(ChatColor.stripColor(e.getLine(0)).equalsIgnoreCase("[Warp]")){
				if(Warp.exists(e.getLine(1)))
					e.setLine(0, "§1[Warp]");
				else {
					e.setLine(0, "§4[Warp]");
					p.sendMessage(Messages.getMessage("Invalid warp", e.getLine(1)));
				}
			}
		}
	}
}
