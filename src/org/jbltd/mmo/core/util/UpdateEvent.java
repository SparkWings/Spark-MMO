package org.jbltd.mmo.core.util;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UpdateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final UpdateType _type;

    public UpdateEvent(UpdateType example) {
        _type = example;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public UpdateType getType() {
        return _type;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}