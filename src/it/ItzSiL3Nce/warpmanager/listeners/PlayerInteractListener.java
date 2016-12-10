package it.ItzSiL3Nce.warpmanager.listeners;

import it.ItzSiL3Nce.warpmanager.Warp;
import it.ItzSiL3Nce.warpmanager.WarpManager;
import it.ItzSiL3Nce.warpmanager.commands.use.WarpTeleport;

import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
				|| e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			if (e.getClickedBlock().getState() instanceof Sign
					&& WarpManager.signEnabled()) {
				Sign s = (Sign) e.getClickedBlock().getState();
				if (s.getLine(0).equals("§1[Warp]") && s.getLine(1) != null
						&& !s.getLine(1).isEmpty()) {
					Player p = e.getPlayer();
					WarpTeleport.to(p, Warp.get(s.getLine(1)));
					if(!p.isSneaking() || !p.isOp() || !p.hasPermission("warpmanager.sign.destroy"))
						e.setCancelled(true);
				}
			}
		}
	}
}
