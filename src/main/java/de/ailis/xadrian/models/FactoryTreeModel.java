/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
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
import de.ailis.xadrian.data.Game;
import de.ailis.xadrian.data.Race;
import de.ailis.xadrian.data.Ware;
import de.ailis.xadrian.data.factories.FactoryFactory;
import de.ailis.xadrian.support.I18N;

/**
 * FactoryTreeModel
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class FactoryTreeModel implements TreeModel
{
    /** The top level entries */
    private final List<Object> topLevel = new ArrayList<Object>();

    /** The top level entries */
    private final List<Race> races = new ArrayList<Race>();

    /** The wares */
    private final List<Ware> wares = new ArrayList<Ware>();

    /** The cheapest factories for wares */
    private final List<Factory> cheapest = new ArrayList<Factory>();

    /** The title for the cheapest entry */
    private final String cheapestEntry = I18N.getString("addFactory.cheapest");

    /** The title for the By-Ware entry */
    private final String byWareEntry = I18N.getString("addFactory.byWare");

    /** The title for the By-Race entry */
    private final String byRaceEntry = I18N.getString("addFactory.byRace");

    /** The factories by races */
    private final Map<Race, List<Factory>> byRaceFactories =
        new TreeMap<Race, List<Factory>>();

    /** The factories by wares */
    private final Map<Ware, List<Factory>> byWareFactories =
        new TreeMap<Ware, List<Factory>>();

    /**
     * Constructor.
     *
     * @param game
     *            The game.
     */
    public FactoryTreeModel(Game game)
    {
        if (game == null) throw new IllegalArgumentException("game must be set");
        final FactoryFactory factoryFactory = game.getFactoryFactory();

        // Build the list with cheapest factories
        for (final Ware ware : game.getWareFactory().getWares())
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
        for (final Ware ware : game.getWareFactory().getWares())
        {
            final List<Factory> factories = game.getFactoryFactory()
                .getFactories(ware);
            if (factories.size() > 0)
            {
                this.byWareFactories.put(ware, factories);
                this.wares.add(ware);
            }
        }
        this.topLevel.add(this.byWareEntry);

        // Build map with factories by races
        for (final Race race : game.getRaceFactory().getRaces())
        {
            final List<Factory> factories = game.getFactoryFactory()
                .getFactories(race);
            if (factories.size() > 0)
            {
                this.byRaceFactories.put(race, factories);
                this.races.add(race);
            }
        }
        this.topLevel.add(this.byRaceEntry);
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
        else if (parent.equals(this.byRaceEntry))
            return this.races.size();
        else if (parent instanceof Race)
            return this.byRaceFactories.get(parent).size();
        else if (parent.equals(this.byWareEntry))
            return this.wares.size();
        else if (parent instanceof Ware)
            return this.byWareFactories.get(parent).size();
        else if (parent.equals(this.cheapestEntry))
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
            return this.byRaceFactories.get(parent).get(index);
        else if (parent.equals(this.byRaceEntry))
            return this.races.get(index);
        else if (parent instanceof Ware)
            return this.byWareFactories.get(parent).get(index);
        else if (parent.equals(this.byWareEntry))
            return this.wares.get(index);
        else if (parent.equals(this.cheapestEntry))
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
            return this.byRaceFactories.get(parent).indexOf(child);
        else if (parent.equals(this.byRaceEntry))
            return this.races.indexOf(child);
        else if (parent instanceof Ware)
            return this.byWareFactories.get(parent).indexOf(child);
        else if (parent.equals(this.byWareEntry))
            return this.wares.indexOf(child);
        else if (parent.equals(this.cheapestEntry))
            return this.cheapest.indexOf(child);
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
