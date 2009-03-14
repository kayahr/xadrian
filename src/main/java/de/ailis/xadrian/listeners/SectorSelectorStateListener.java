/*
 * $Id: EditorStateListener.java 735 2009-02-26 21:14:43Z k $
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.listeners;

import java.util.EventListener;

import de.ailis.xadrian.components.SectorSelector;


/**
 * Listener interface for sector selector changes.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 735 $
 */

public interface SectorSelectorStateListener extends EventListener
{
    /**
     * Called when the sector selector state has changed
     * 
     * @param sender
     *            The sender
     */

    public void sectorSelectorChanged(SectorSelector sender);
}
