/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import de.ailis.xadrian.data.Factory;
import de.ailis.xadrian.data.Race;
import de.ailis.xadrian.data.factories.FactoryFactory;
import de.ailis.xadrian.data.factories.RaceFactory;


/**
 * FactoryTreeModel
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class FactoryTreeModel implements TreeModel
{
    /** The races */
    private final List<Race> races = new ArrayList<Race>();

    /** The factories */
    private final Map<Race, List<Factory>> factories =
        new TreeMap<Race, List<Factory>>();


    /**
     * Constructor
     */

    public FactoryTreeModel()
    {
        for (final Race race: RaceFactory.getInstance().getRaces())
        {
            final List<Factory> factories =
                FactoryFactory.getInstance().getFactories(race);
            if (factories.size() > 0)
            {
                this.races.add(race);
                this.factories.put(race, factories);
            }
        }
    }


    /**
     * @see TreeModel#getRoot()
     */

    @Override
    public Object getRoot()
    {
        return Boolean.TRUE;
    }


    /**
     * @see TreeModel#getChildCount(Object)
     */

    @Override
    public int getChildCount(final Object parent)
    {
        if (parent instanceof Factory)
            return 0;
        else if (parent instanceof Race)
            return this.factories.get(parent).size();
        else
            return this.races.size();
    }


    /**
     * @see TreeModel#getChild(Object, int)
     */

    @Override
    public Object getChild(final Object parent, final int index)
    {
        if (parent instanceof Race)
            return this.factories.get(parent).get(index);
        else
            return this.races.get(index);
    }

    /**
     * @see TreeModel#getIndexOfChild(Object, Object)
     */

    @Override
    public int getIndexOfChild(final Object parent, final Object child)
    {
        if (parent instanceof Race)
            return this.factories.get(parent).indexOf(child);
        else
            return this.races.indexOf(child);
    }


    /**
     * @see TreeModel#isLeaf(Object)
     */

    @Override
    public boolean isLeaf(final Object node)
    {
        return node instanceof Factory;
    }


    /**
     * @see TreeModel#valueForPathChanged(TreePath, Object)
     */

    @Override
    public void valueForPathChanged(final TreePath path, final Object newValue)
    {
        // Not implemented
    }


    /**
     * @see TreeModel#addTreeModelListener(TreeModelListener)
     */

    @Override
    public void addTreeModelListener(final TreeModelListener l)
    {
        // Not implemented
    }


    /**
     * @see TreeModel#removeTreeModelListener(TreeModelListener)
     */

    @Override
    public void removeTreeModelListener(final TreeModelListener l)
    {
        // Not implemented
    }
}
