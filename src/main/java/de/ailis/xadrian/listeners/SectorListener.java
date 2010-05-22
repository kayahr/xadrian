/*
 * $Id: ComplexStateListener.java 789 2009-03-08 14:46:22Z k $
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.listeners;

import java.util.EventListener;

import de.ailis.xadrian.data.Sector;


/**
 * Listener interface for receiving events about sector changes.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 789 $
 */

public interface SectorListener extends EventListener
{
    /**
     * Called when the sector has been changed.
     * 
     * @param sector
     *            The new sector
     */

    public void sectorChanged(Sector sector);
}
