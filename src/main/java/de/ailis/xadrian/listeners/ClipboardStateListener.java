/*
 * $Id$
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.listeners;

import java.util.EventListener;

import de.ailis.xadrian.interfaces.ClipboardProvider;


/**
 * Listener interface for clipboard state changes of a component.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public interface ClipboardStateListener extends EventListener
{
    /**
     * Called when the clipboard state of a component has changed
     * 
     * @param provider
     *            The clipboard provider which sent this event
     */

    public void clipboardStateChanged(ClipboardProvider provider);
}
