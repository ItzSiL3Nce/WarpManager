package it.ItzSiL3Nce.warpmanager.events;

import it.ItzSiL3Nce.warpmanager.Warp;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class PlayerWarpEvent extends WarpEvent implements Cancellable {

	private final Player p;
	private boolean cancelled = false;
	

	public PlayerWarpEvent(Player p, Warp to) {
		super(to);
		this.p = p;
	}
	
	public Warp getWarp() {
		return w;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}
	
	public Player getPlayer() {
		return p;
	}
	
}
