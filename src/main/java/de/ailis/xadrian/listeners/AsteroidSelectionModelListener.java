/*
 * $Id: ComplexStateListener.java 789 2009-03-08 14:46:22Z k $
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.listeners;

import java.util.EventListener;

import de.ailis.xadrian.models.AsteroidSelectionModel;


/**
 * Listener interface for listening for changes in an AsteroidSelectionModel.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface AsteroidSelectionModelListener extends EventListener
{
    /**
     * Called when the sector has been changed.
     *
     * @param model
     *            The changed model.
     */

    public void sectorChanged(AsteroidSelectionModel model);


    /**
     * Called when the selection has been changed.
     *
     * @param model
     *            The changed model
     */

    public void selectionChanged(AsteroidSelectionModel model);


    /**
     * Called when the focuses asteroid has been changed.
     *
     * @param model
     *            The changed model
     */

    public void focusChanged(AsteroidSelectionModel model);
}
