/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.listeners;

import java.util.EventListener;

import de.ailis.xadrian.interfaces.ComplexProvider;


/**
 * Listener interface for complex state changes of a component.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public interface ComplexStateListener extends EventListener
{
    /**
     * Called when the complex state of a component has changed
     * 
     * @param provider
     *            The complex provider which sent this event
     */

    public void complexStateChanged(ComplexProvider provider);
}
