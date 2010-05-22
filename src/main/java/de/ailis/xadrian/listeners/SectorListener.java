/*
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
