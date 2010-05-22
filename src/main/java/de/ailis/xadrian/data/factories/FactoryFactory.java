/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.data.factories;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import de.ailis.xadrian.Main;
import de.ailis.xadrian.data.Capacity;
import de.ailis.xadrian.data.Factory;
import de.ailis.xadrian.data.FactorySize;
import de.ailis.xadrian.data.Product;
import de.ailis.xadrian.data.Race;
import de.ailis.xadrian.data.Station;
import de.ailis.xadrian.data.Ware;
import de.ailis.xadrian.exceptions.DataException;
import de.ailis.xadrian.support.Config;


/**
 * Factory for Factory objects.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public class FactoryFactory
{
    /** The factory map (for quick ID navigation) */
    private final Map<String, Factory> factoryMap = new HashMap<String, Factory>();

    /** The factories (sorted) */
    private final SortedSet<Factory> factories = new TreeSet<Factory>();

    /** The singleton instance */
    private final static FactoryFactory instance = new FactoryFactory();

    /** The configuration */
    private final static Config config = Config.getInstance();


    /**
     * Private constructor to prevent instantiation from outside.
     */

    private FactoryFactory()
    {
        readData();
    }


    /**
     * Returns the singleton instance.
     * 
     * @return The singleton instance
     */

    public static final FactoryFactory getInstance()
    {
        return instance;
    }


    /**
     * Reads the data from the XML file.
     */

    private void readData()
    {
        final URL url = Main.class.getResource("data/factories.xml");
        final SAXReader reader = new SAXReader();
        try
        {
            final Document document = reader.read(url);
            final WareFactory wareFactory = WareFactory.getInstance();
            final RaceFactory raceFactory = RaceFactory.getInstance();
            for (final Object item : document.getRootElement().elements(
                "factory"))
            {
                final Element element = (Element) item;
                final String id = element.attributeValue("id");
                FactorySize size;
                final String sizeStr = element.attributeValue("size");
                if (sizeStr == null)
                    size = FactorySize.S;
                else
                    size = FactorySize.valueOf(sizeStr);
                final Race race = raceFactory.getRace(element
                    .attributeValue("race"));
                final int cycle = Integer.parseInt(element
                    .attributeValue("cycle"));
                final int price = Integer.parseInt(element
                    .attributeValue("price"));
                final int volume = Integer.parseInt(element
                    .attributeValue("volume"));

                final Element productElement = element.element("product");
                final Ware productWare = wareFactory.getWare(productElement
                    .attributeValue("ware"));
                final int productQuantity = Integer.parseInt(productElement
                    .attributeValue("quantity"));
                final Product product = new Product(productWare,
                    productQuantity);

                final List<?> resItems = element.elements("resource");
                final Product[] resources = new Product[resItems.size()];
                final Capacity[] storage = new Capacity[resItems.size() + 1];
                storage[0] = new Capacity(product.getWare(), Integer
                    .parseInt(productElement.attributeValue("storage")));
                int i = 0;
                for (final Object resItem : resItems)
                {
                    final Element resElement = (Element) resItem;
                    final Ware resWare = wareFactory.getWare(resElement
                        .attributeValue("ware"));
                    final int resQuantity = Integer.parseInt(resElement
                        .attributeValue("quantity"));
                    final int resStorage = Integer.parseInt(resElement
                        .attributeValue("storage"));
                    resources[i] = new Product(resWare, resQuantity);
                    storage[i + 1] = new Capacity(resWare, resStorage);
                    i++;
                }

                final List<?> manuItems = element.elements("manufacturer");
                final Station[] manufacturers = new Station[manuItems.size()];
                i = 0;
                for (final Object manuItem : manuItems)
                {
                    final Element manuElement = (Element) manuItem;
                    manufacturers[i] = StationFactory.getInstance().getStation(
                        manuElement.attributeValue("station"),
                        manuElement.attributeValue("sector"));
                    i++;
                }
                Arrays.sort(resources);
                Arrays.sort(storage);
                final Factory factory = new Factory(id, size, race, cycle,
                    product, price, volume, resources, storage, manufacturers);
                this.factories.add(factory);
                this.factoryMap.put(id, factory);
            }
        }
        catch (final DocumentException e)
        {
            throw new DataException("Unable to read XML file: " + e, e);
        }
    }

    /**
     * Returns all factories.
     * 
     * @return The factories
     */

    public SortedSet<Factory> getFactories()
    {
        return Collections.unmodifiableSortedSet(this.factories);
    }


    /**
     * Returns the factory with the specified id or null if not found.
     * 
     * @param id
     *            The factory id
     * @return The factory or null if not found
     */

    public Factory getFactory(final String id)
    {
        return this.factoryMap.get(id);
    }


    /**
     * Returns the factories of the specified race.
     * 
     * @param race
     *            The race
     * @return The factories of the specified race
     */

    public List<Factory> getFactories(final Race race)
    {
        final List<Factory> factories = new ArrayList<Factory>();
        for (final Factory factory : this.factories)
        {
            if (factory.getRace().equals(race)) factories.add(factory);
        }
        return factories;
    }


    /**
     * Returns the factories which produces the specified ware. This method only
     * uses factories which races are not set to be ignored.
     * 
     * @param ware
     *            The ware
     * @return The factories which products the specified ware
     */

    public List<Factory> getFactories(final Ware ware)
    {
        final List<Factory> factories = new ArrayList<Factory>();
        for (final Factory factory : this.factories)
        {
            // Ignore factories of ignored races.
            if (config.isRaceIgnored(factory.getRace())) continue;

            if (factory.getProduct().getWare().equals(ware))
                factories.add(factory);
        }
        return factories;
    }


    /**
     * Returns the factory which produces the specified ware and have the
     * specified size and belongs to the specified race. This method only uses
     * factories which races are not set to be ignored. So if no factory was
     * matched then null is returned.
     * 
     * @param ware
     *            The ware
     * @param size
     *            The factory size
     * @param race
     *            The owner race
     * @return The matching factory or null if none matched
     */

    public Factory getFactory(final Ware ware, final FactorySize size,
        final Race race)
    {
        for (final Factory factory : getFactories(ware, size))
        {
            if (factory.getRace().equals(race)) return factory;
        }
        return null;
    }


    /**
     * Returns the factories which produces the specified ware and have the
     * specified size. This method only uses factories which races are not set
     * to be ignored.
     * 
     * @param ware
     *            The ware
     * @param size
     *            The factory size
     * @return The matching factories
     */

    public List<Factory> getFactories(final Ware ware, final FactorySize size)
    {
        final List<Factory> factories = new ArrayList<Factory>();
        for (final Factory factory : this.factories)
        {
            // Ignore factories of ignored races.
            if (config.isRaceIgnored(factory.getRace())) continue;

            if (factory.getProduct().getWare().equals(ware)
                && factory.getSize().equals(size)) factories.add(factory);
        }
        return factories;
    }


    /**
     * Returns the available sizes of factories producing the specified ware.
     * This method only uses factories which races are not set to be ignored.
     * 
     * @param ware
     *            The product id ware
     * @return The set with available factory sizes
     */

    public SortedSet<FactorySize> getFactorySizes(final Ware ware)
    {
        return getFactorySizes(ware, null);
    }


    /**
     * Returns the available sizes of factories producing the specified ware and
     * belonging to the specified race. This method only uses factories which
     * races are not set to be ignored.
     * 
     * @param ware
     *            The product id ware
     * @param race
     *            Optional race to filter for. Maybe null
     * @return The set with available factory sizes
     */

    public SortedSet<FactorySize> getFactorySizes(final Ware ware,
        final Race race)
    {
        final SortedSet<FactorySize> sizes = new TreeSet<FactorySize>();

        for (final Factory factory : this.factories)
        {
            // Ignore race if it does not match the specified one
            if (race != null && !factory.getRace().equals(race)) continue;

            // Ignore factories of ignored races.
            if (config.isRaceIgnored(factory.getRace())) continue;

            if (factory.getProduct().getWare().equals(ware))
                sizes.add(factory.getSize());
        }
        return sizes;
    }


    /**
     * Returns the cheapest factory of the given size which produces the
     * specified ware. Returns null if none found. This method only uses
     * factories which races are not set to be ignored.
     * 
     * @param ware
     *            The ware
     * @param size
     *            The factory size
     * @return The cheapest matching factory or null if none found.
     */

    public Factory getCheapestFactory(final Ware ware, final FactorySize size)
    {
        Factory cheapestFactory = null;
        int cheapestPrice = Integer.MAX_VALUE;
        for (final Factory factory : this.factories)
        {
            // Ignore factories of ignored races.
            if (config.isRaceIgnored(factory.getRace())) continue;

            final int price = factory.getPrice();
            if (factory.getProduct().getWare().equals(ware)
                && factory.getSize().equals(size) && price < cheapestPrice)
            {
                cheapestFactory = factory;
                cheapestPrice = price;
            }
        }
        return cheapestFactory;
    }


    /**
     * Checks if the specified race has at least one factory which produces the
     * specified ware. If the race is ignored then this method always returns
     * false.
     * 
     * @param race
     *            The race
     * @param ware
     *            The ware
     * @return True if the race has matching factories, false if not
     */

    public boolean hasFactories(final Race race, final Ware ware)
    {
        // If race is ignored then it can't have matching factories
        if (config.isRaceIgnored(race)) return false;

        for (final Factory factory : this.factories)
        {
            // Ignore race if it does not match the specified one
            if (race != null && !factory.getRace().equals(race)) continue;

            // Return true if matching factory produces the right ware.
            if (factory.getProduct().getWare().equals(ware)) return true;
        }
        return false;
    }
}
