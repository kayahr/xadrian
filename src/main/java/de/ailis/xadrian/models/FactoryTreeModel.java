/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import de.ailis.xadrian.data.Factory;
import de.ailis.xadrian.data.FactorySize;
import de.ailis.xadrian.data.Race;
import de.ailis.xadrian.data.Ware;
import de.ailis.xadrian.data.factories.FactoryFactory;
import de.ailis.xadrian.data.factories.RaceFactory;
import de.ailis.xadrian.data.factories.WareFactory;
import de.ailis.xadrian.support.I18N;


/**
 * FactoryTreeModel
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class FactoryTreeModel implements TreeModel
{
    /** The top level entries */
    private final List<Object> topLevel = new ArrayList<Object>();

    /** The wares */
    private final List<Object> wares = new ArrayList<Object>();

    /** The cheapest factories for wares */
    private final List<Factory> cheapest = new ArrayList<Factory>();
    
    /** The title for the cheapest entry */
    private final String cheapestEntry = I18N.getString("addFactory.cheapest");

    /** The title for the By-Ware entry */
    private final String byWareEntry = I18N.getString("addFactory.byWare");

    /** The factories by races */
    private final Map<Race, List<Factory>> factories = new TreeMap<Race, List<Factory>>();

    /** The factories by wares */
    private final Map<Ware, List<Factory>> byWareFactories = new TreeMap<Ware, List<Factory>>();


    /**
     * Constructor
     */

    public FactoryTreeModel()
    {
        final FactoryFactory factoryFactory = FactoryFactory.getInstance();

        // Build the list with cheapest factories
        for (final Ware ware : WareFactory.getInstance().getWares())
        {
            for (final FactorySize size : factoryFactory.getFactorySizes(ware))
            {
                final Factory factory = factoryFactory.getCheapestFactory(ware,
                    size);
                if (factory != null) this.cheapest.add(factory);
            }
        }
        this.topLevel.add(this.cheapestEntry);
        Collections.sort(this.cheapest);

        
        // Build map with factories by wares
        for (final Ware ware : WareFactory.getInstance().getWares())
        {
            final List<Factory> factories = FactoryFactory.getInstance()
                .getFactories(ware);
            if (factories.size() > 0)
            {
                this.byWareFactories.put(ware, factories);
                this.wares.add(ware);
            }
        }
        this.topLevel.add(this.byWareEntry);

        // Build map with factories by races
        for (final Race race : RaceFactory.getInstance().getRaces())
        {
            final List<Factory> factories = FactoryFactory.getInstance()
                .getFactories(race);
            if (factories.size() > 0)
            {
                this.topLevel.add(race);
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
        else if (parent == this.byWareEntry)
            return this.wares.size();
        else if (parent instanceof Ware)
            return this.byWareFactories.get(parent).size();
        else if (parent == this.cheapestEntry)
            return this.cheapest.size();
        else
            return this.topLevel.size();
    }


    /**
     * @see TreeModel#getChild(Object, int)
     */

    @Override
    public Object getChild(final Object parent, final int index)
    {
        if (parent instanceof Race)
            return this.factories.get(parent).get(index);
        else if (parent instanceof Ware)
            return this.byWareFactories.get(parent).get(index);
        else if (parent == this.byWareEntry)
            return this.wares.get(index);
        else if (parent == this.cheapestEntry)
            return this.cheapest.get(index);
        else
            return this.topLevel.get(index);
    }

    /**
     * @see TreeModel#getIndexOfChild(Object, Object)
     */

    @Override
    public int getIndexOfChild(final Object parent, final Object child)
    {
        if (parent instanceof Race)
            return this.factories.get(parent).indexOf(child);
        else if (parent instanceof Ware)
            return this.byWareFactories.get(parent).indexOf(child);
        else if (parent == this.byWareEntry)
            return this.cheapest.indexOf(child);
        else if (parent == this.cheapestEntry)
            return this.wares.indexOf(child);
        else
            return this.topLevel.indexOf(child);
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
