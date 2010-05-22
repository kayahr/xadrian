/*
 * $Id$
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.listeners;

import java.util.EventListener;

import de.ailis.xadrian.frames.MainFrame;


/**
 * Listener interface for main frame state changes.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public interface MainStateListener extends EventListener
{
    /**
     * Called when the state of the main frame changed.
     * 
     * @param sender
     *            The sender
     */

    public void mainStateChanged(MainFrame sender);
}
