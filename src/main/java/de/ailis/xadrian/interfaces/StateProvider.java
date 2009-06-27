/*
 * $Id: ClipboardProvider.java 788 2009-03-08 14:14:51Z k $
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.interfaces;

import de.ailis.xadrian.listeners.StateListener;


/**
 * This interface is implemented by all components which have a state which can
 * change and which other components may want to watch.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 788 $
 */

public interface StateProvider
{
    /**
     * Adds a new state listener.
     * 
     * @param listener
     *            The listener to add
     */

    public void addStateListener(StateListener listener);

    
    /**
     * Removes a state listener.
     * 
     * @param listener
     *            The listener to remove
     */
    
    public void removeStateListener(StateListener listener);
}
