/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */
package de.ailis.xadrian.interfaces;

import de.ailis.xadrian.listeners.StateListener;

/**
 * This interface is implemented by all components which have a state which can
 * change and which other components may want to watch.
 *
 * @author Klaus Reimer (k@ailis.de)
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
