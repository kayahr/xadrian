/*
 * $Id: WareFactory.java 752 2009-03-01 23:24:14Z k $
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.data.factories;

import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import de.ailis.xadrian.Main;
import de.ailis.xadrian.data.Race;
import de.ailis.xadrian.data.Sector;
import de.ailis.xadrian.data.Suns;
import de.ailis.xadrian.exceptions.DataException;


/**
 * Factory for Sector objects.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 752 $
 */

public class SectorFactory
{
    /** The singleton instance */
    private final static SectorFactory instance = new SectorFactory();

    /** The sectors (sorted) */
    private final SortedSet<Sector> sectors = new TreeSet<Sector>();

    /** The sector map (for quick ID navigation) */
    private final Map<String, Sector> sectorMap = new HashMap<String, Sector>();

    /** The maximum X position */
    private int maxX = 0;

    /** The maximum Y position */
    private int maxY = 0;


    /**
     * Private constructor to prevent instantiation from outside.
     */

    private SectorFactory()
    {
        readData();
    }


    /**
     * Reads the data from the XML file.
     */

    private void readData()
    {
        final URL url = Main.class.getResource("data/sectors.xml");
        final SAXReader reader = new SAXReader();
        try
        {
            final RaceFactory raceFactory = RaceFactory.getInstance();
            final Document document = reader.read(url);
            for (final Object item : document.getRootElement().elements(
                "sector"))
            {
                final Element element = (Element) item;
                final String id = element.attributeValue("id");
                final int x = Integer.parseInt(element.attributeValue("x"));
                this.maxX = Math.max(this.maxX, x);
                final int y = Integer.parseInt(element.attributeValue("y"));
                this.maxY = Math.max(this.maxY, y);
                final int planets = Integer.parseInt(element
                    .attributeValue("planets"));
                final Suns suns = Suns.valueOf(Integer.parseInt(element
                    .attributeValue("suns")));
                final Race race = raceFactory.getRace(element
                    .attributeValue("race"));
                final boolean core = Boolean.parseBoolean(element
                    .attributeValue("core"));
                final String northId = element.attributeValue("north");
                final String eastId = element.attributeValue("east");
                final String southId = element.attributeValue("south");
                final String westId = element.attributeValue("west");
                boolean shipyard = false;
                final Element stationsElement = element.element("stations");
                if (stationsElement != null)
                {
                    for (final Object stationItem : stationsElement.elements("station"))
                    {
                        final Element stationElement = (Element) stationItem;
                        if (stationElement.attributeValue("class").equals(
                            "bigShipyard"))
                        {
                            shipyard = true;
                            continue;
                        }
                    }
                }
                final Sector sector = new Sector(id, x, y, race, planets, suns,
                    core, shipyard, northId, eastId, southId, westId);
                this.sectors.add(sector);
                this.sectorMap.put(id, sector);
            }
        }
        catch (final DocumentException e)
        {
            throw new DataException("Unable to read XML file: " + e, e);
        }
    }


    /**
     * Returns the singleton instance.
     * 
     * @return The singleton instance
     */

    public static final SectorFactory getInstance()
    {
        return instance;
    }


    /**
     * Returns all sectors.
     * 
     * @return The sectors
     */

    public SortedSet<Sector> getSectors()
    {
        return Collections.unmodifiableSortedSet(this.sectors);
    }


    /**
     * Returns the sector with the specified id or null if not found.
     * 
     * @param id
     *            The sector id
     * @return The sector or null if not found
     */

    public Sector getSector(final String id)
    {
        return this.sectorMap.get(id);
    }


    /**
     * Returns the maximum X position in the universe.
     * 
     * @return The maximum X position
     */

    public int getMaxX()
    {
        return this.maxX;
    }


    /**
     * Returns the maximum Y position in the universe.
     * 
     * @return The maximum Y position
     */

    public int getMaxY()
    {
        return this.maxY;
    }


    /**
     * Returns the sector at the specified coordinates. Returns null if there is
     * no sector at this coordinate.
     * 
     * @param x
     *            The X coordinate
     * @param y
     *            The Y coordinate
     * @return The sector at this coordinate or null if none
     */

    public Sector getSector(final int x, final int y)
    {
        return getSector(String.format("sec-%d-%d", x, y));
    }
}
