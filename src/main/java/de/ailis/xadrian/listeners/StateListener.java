/*
 * $Id: ComplexStateListener.java 789 2009-03-08 14:46:22Z k $
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.listeners;

import java.util.EventListener;


/**
 * Listener interface for state changes of component
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface StateListener extends EventListener
{
    /**
     * Called when the state of a component has changed.
     */

    public void stateChanged();
}
