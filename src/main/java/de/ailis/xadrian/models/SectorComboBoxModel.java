/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de> See LICENSE.txt file for
 * licensing information.
 */

package de.ailis.xadrian.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

import de.ailis.xadrian.data.Game;
import de.ailis.xadrian.data.Sector;

/**
 * Model which represents sectors to be listed in a combo box.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public class SectorComboBoxModel implements ComboBoxModel
{
    /** The selected item */
    private Object selectedItem;

    /** The sectors */
    private final List<Sector> sectors;

    /**
     * Constructor
     * 
     * @param game
     *            The game. Must not be null.
     */
    public SectorComboBoxModel(final Game game)
    {
        if (game == null)
            throw new IllegalArgumentException("game must be set");
        this.sectors =
            new ArrayList<Sector>(game.getSectorFactory().getSectors());
        this.sectors.add(0, null);
    }

    /**
     * @see javax.swing.ComboBoxModel#getSelectedItem()
     */

    @Override
    public Object getSelectedItem()
    {
        return this.selectedItem;
    }

    /**
     * @see javax.swing.ComboBoxModel#setSelectedItem(java.lang.Object)
     */

    @Override
    public void setSelectedItem(final Object selectedItem)
    {
        this.selectedItem = selectedItem;
    }

    /**
     * @see javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener)
     */

    @Override
    public void addListDataListener(final ListDataListener l)
    {
        // Not used
    }

    /**
     * @see javax.swing.ListModel#getElementAt(int)
     */

    @Override
    public Object getElementAt(final int index)
    {
        return this.sectors.get(index);
    }

    /**
     * @see javax.swing.ListModel#getSize()
     */

    @Override
    public int getSize()
    {
        return this.sectors.size();
    }

    /**
     * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
     */

    @Override
    public void removeListDataListener(final ListDataListener l)
    {
        // Not used
    }
}
