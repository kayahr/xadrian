/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.data.factories;

import java.net.URL;
import java.util.ArrayList;
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
import de.ailis.xadrian.data.Factory;
import de.ailis.xadrian.data.Product;
import de.ailis.xadrian.data.Race;
import de.ailis.xadrian.data.Ware;
import de.ailis.xadrian.data.Factory.Type;
import de.ailis.xadrian.exceptions.DataException;


/**
 * Factory for Factory objects.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class FactoryFactory
{
    /** The factory map (for quick ID navigation) */
    private final Map<String, Factory> factoryMap =
        new HashMap<String, Factory>();

    /** The factories (sorted) */
    private final SortedSet<Factory> factories = new TreeSet<Factory>();

    /** The singleton instance */
    private final static FactoryFactory instance = new FactoryFactory();


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
            for (final Object item: document.getRootElement().elements(
                "factory"))
            {
                final Element element = (Element) item;
                final String id = element.attributeValue("id");
                final Race race =
                    raceFactory.getRace(element.attributeValue("race"));
                final int cycle =
                    Integer.parseInt(element.attributeValue("cycle"));
                final int price =
                    Integer.parseInt(element.attributeValue("price"));
                Type type = Type.STANDARD;
                final String typeStr = element.attributeValue("type");
                if (typeStr != null) type = Type.valueOf(typeStr.toUpperCase());
                // TODO implement me
                // final int volume =
                // Integer.parseInt(element.attributeValue("volume"));
                final int volume = 0;                

                final Element productElement = element.element("product");
                final Ware productWare =
                    wareFactory.getWare(productElement.attributeValue("ware"));
                final int productQuantity =
                    Integer
                        .parseInt(productElement.attributeValue("quantity"));
                final Product product =
                    new Product(productWare, productQuantity);

                final List<?> resItems = element.elements("resource");
                final Product[] resources = new Product[resItems.size()];
                int i = 0;
                for (final Object resItem: resItems)
                {
                    final Element resElement = (Element) resItem;
                    final Ware resWare =
                        wareFactory.getWare(resElement.attributeValue("ware"));
                    final int resQuantity =
                        Integer
                            .parseInt(resElement.attributeValue("quantity"));
                    resources[i] = new Product(resWare, resQuantity);
                    i++;
                }

                final Factory factory =
                    new Factory(id, type, race, cycle, product, price, volume,
                        resources);
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

    public List<Factory> getFactoriesByRace(final Race race)
    {
        final List<Factory> factories = new ArrayList<Factory>();
        for (final Factory factory: this.factories)
        {
            if (factory.getRace().equals(race)) factories.add(factory);
        }
        return factories;
    }
}
