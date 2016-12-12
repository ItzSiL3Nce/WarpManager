package it.ItzSiL3Nce.warpmanager.events;

import it.ItzSiL3Nce.warpmanager.Warp;

import org.bukkit.event.Cancellable;

public class WarpSetEvent extends WarpEvent implements Cancellable {

	private final String creator;
	private boolean cancelled = false;
	
	public WarpSetEvent(Warp warp, String creator){
		super(warp);
		this.creator = creator;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	public String getCreator() {
		return creator;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}
}
