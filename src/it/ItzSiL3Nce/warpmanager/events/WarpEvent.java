package it.ItzSiL3Nce.warpmanager.events;

import it.ItzSiL3Nce.warpmanager.Warp;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WarpEvent extends Event {

	protected Warp w;
	
	public WarpEvent(Warp warp) {
		this.w = warp;
	}
	
	public Warp getWarp() {
		return w;
	}

	private static HandlerList handlers = new HandlerList();
	
	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
		return handlers;
	}
}
