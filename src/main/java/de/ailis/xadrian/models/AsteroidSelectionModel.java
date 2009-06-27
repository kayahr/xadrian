/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.event.EventListenerList;

import de.ailis.xadrian.data.Asteroid;
import de.ailis.xadrian.data.Sector;
import de.ailis.xadrian.data.Ware;
import de.ailis.xadrian.interfaces.SectorProvider;
import de.ailis.xadrian.listeners.AsteroidSelectionModelListener;


/**
 * AsteroidSelectionModel
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class AsteroidSelectionModel implements Serializable, SectorProvider
{
    /** Serial version UID */
    private static final long serialVersionUID = 7264032309645423896L;

    /** The ware for which asteroids are selected */
    private Ware ware;

    /** The event listener list */
    private final EventListenerList listenerList = new EventListenerList();

    /** The sector */
    private Sector sector;

    /** The selected asteroid */
    private final List<Asteroid> selection = new ArrayList<Asteroid>();

    /** The rest of the yields which could not be mapped to asteroids */
    private final List<Integer> restYields = new ArrayList<Integer>();

    /** The focused asteroid */
    private Asteroid focused;


    /**
     * Sets the sector.
     * 
     * @param sector
     *            The sector to set
     */

    public void setSector(final Sector sector)
    {
        if (sector != this.sector)
        {
            backupYields();
            this.sector = sector;
            this.selection.clear();
            this.focused = null;
            restoreYields();
            fireSectorChanged();
            fireFocusChanged();
            fireSelectionChanged();
        }
    }


    /**
     * Sets the ware for which asteroids are selected.
     * 
     * @param ware
     *            The ware
     */

    public void setWare(final Ware ware)
    {
        if (ware != this.ware)
        {
            this.ware = ware;
        }
    }
    
    
    /**
     * Returns the ware for which asteroids are selected.
     * 
     * @return The ware
     */
    
    public Ware getWare()
    {
        return this.ware;
    }


    /**
     * Backup the selected asteroids into the rest yield list.
     */

    private void backupYields()
    {
        for (final Asteroid asteroid: this.selection)
        {
            final int yield = asteroid.getYield(this.ware);
            this.restYields.add(yield);
        }
    }


    /**
     * Restores selected asteroids from the rest yield list.
     */

    private void restoreYields()
    {
        // Do nothing if no sector is set
        if (this.sector == null) return;

        for (final Integer yield: new ArrayList<Integer>(this.restYields))
        {
            for (final Asteroid asteroid: this.sector.getAsteroids())
            {
                // Ignore this asteroid if already used
                if (isSelected(asteroid)) continue;

                if (yield.equals(asteroid.getYield(this.ware)))
                {
                    this.restYields.remove(yield);
                    select(asteroid);
                    break;
                }
            }
        }
    }


    /**
     * Returns the sector.
     * 
     * @return The sector
     */

    public Sector getSector()
    {
        return this.sector;
    }


    /**
     * Focuses the specified asteroid. Specify null to focus nothing.
     * 
     * @param asteroid
     *            The asteroid to focus. Null means focusing nothing
     */

    public void focus(final Asteroid asteroid)
    {
        if (asteroid != this.focused)
        {
            if (this.sector == null)
                throw new IllegalStateException("No sector set");
            if (asteroid != null && !this.sector.hasAsteroid(asteroid))
                throw new IllegalArgumentException("Asteroid is not in sector");
            this.focused = asteroid;
            fireFocusChanged();
        }
    }


    /**
     * Returns the focused asteroid. Maybe null if no asteroid is focused.
     * 
     * @return The focused asteroid or null if none
     */

    public Asteroid getFocused()
    {
        return this.focused;
    }


    /**
     * Checks if the specified asteroid is selected.
     * 
     * @param asteroid
     *            The asteroid to check
     * @return True if asteroid is selected, false if not
     */

    public boolean isSelected(final Asteroid asteroid)
    {
        return this.selection.contains(asteroid);
    }


    /**
     * Selects the specified asteroid.
     * 
     * @param asteroid
     *            The asteroid to select
     */

    public void select(final Asteroid asteroid)
    {
        if (!isSelected(asteroid))
        {
            this.selection.add(asteroid);
            fireSelectionChanged();
        }
    }


    /**
     * Deselects the specified asteroid.
     * 
     * @param asteroid
     *            The asteroid to deselect
     */

    public void deselect(final Asteroid asteroid)
    {
        if (isSelected(asteroid))
        {
            this.selection.remove(asteroid);
            fireSelectionChanged();
        }
    }


    /**
     * Returns the list with selected asteroids.
     * 
     * @return The list with selected asteroids
     */

    public List<Asteroid> getSelection()
    {
        return Collections.unmodifiableList(this.selection);
    }


    /**
     * Fires the sectorChanged event.
     */

    protected void fireSectorChanged()
    {
        final Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2)
            if (listeners[i] == AsteroidSelectionModelListener.class)
                ((AsteroidSelectionModelListener) listeners[i + 1])
                    .sectorChanged(this);
    }


    /**
     * Fires the selectionChanged event.
     */

    protected void fireSelectionChanged()
    {
        final Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2)
            if (listeners[i] == AsteroidSelectionModelListener.class)
                ((AsteroidSelectionModelListener) listeners[i + 1])
                    .selectionChanged(this);
    }


    /**
     * Fires the selectionChanged event.
     */

    protected void fireFocusChanged()
    {
        final Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2)
            if (listeners[i] == AsteroidSelectionModelListener.class)
                ((AsteroidSelectionModelListener) listeners[i + 1])
                    .focusChanged(this);
    }


    /**
     * Adds a new model listener.
     * 
     * @param listener
     *            The listener to add
     */

    public void addModelListener(final AsteroidSelectionModelListener listener)
    {
        this.listenerList.add(AsteroidSelectionModelListener.class, listener);
    }


    /**
     * Removes a model listener.
     * 
     * @param listener
     *            The listener to remove
     */

    public void removeSectorListener(
        final AsteroidSelectionModelListener listener)
    {
        this.listenerList.remove(AsteroidSelectionModelListener.class,
            listener);
    }


    /**
     * @see de.ailis.xadrian.interfaces.SectorProvider#canChangeSector()
     */

    @Override
    public boolean canChangeSector()
    {
        return true;
    }


    /**
     * Toggles the selection of the currently focused asteroid.
     */

    public void toggleFocusedSelection()
    {
        if (this.focused == null) return;
        toggleSelection(this.focused);
    }


    /**
     * Toggles the selection of the specified asteroid.
     * 
     * @param asteroid
     *            The asteroid to toggle the selection for
     */

    public void toggleSelection(final Asteroid asteroid)
    {
        if (isSelected(asteroid))
            deselect(asteroid);
        else
            select(asteroid);
    }
}
