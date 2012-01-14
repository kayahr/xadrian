/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de> See LICENSE.txt file for
 * licensing information.
 */

package de.ailis.xadrian.data.factories;

import java.net.URL;
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
import de.ailis.xadrian.data.Asteroid;
import de.ailis.xadrian.data.Game;
import de.ailis.xadrian.data.PlayerSector;
import de.ailis.xadrian.data.Race;
import de.ailis.xadrian.data.Sector;
import de.ailis.xadrian.data.Sun;
import de.ailis.xadrian.data.Ware;
import de.ailis.xadrian.exceptions.DataException;

/**
 * Factory for Sector objects.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public class SectorFactory
{
    /** The game for which this factory is responsible. */
    private final Game game;

    /** The sectors (sorted) */
    private final SortedSet<Sector> sectors = new TreeSet<Sector>();

    /** The sector map (for quick ID navigation) */
    private final Map<String, Sector> sectorMap = new HashMap<String, Sector>();

    /** The maximum X position */
    private int maxX = 0;

    /** The maximum Y position */
    private int maxY = 0;

    /** The maximum silicon yield in a single sector */
    private int maxSiliconYield = 0;

    /** The maximum silicon yield in a single sector */
    private int maxOreYield = 0;

    /** The maximum nividium yield in a single sector */
    private int maxNividiumYield = 0;

    /** The maximum ice yield in a single sector */
    private int maxIceYield = 0;

    /**
     * Constructor.
     * 
     * @param game
     *            The game for which this factory is responsible.
     */
    public SectorFactory(final Game game)
    {
        if (game == null)
            throw new IllegalArgumentException("game must be set");
        this.game = game;
        readData();
    }

    /**
     * Reads the data from the XML file.
     */

    private void readData()
    {
        String gameId = this.game.getId();
        URL url = Main.class.getResource("/" + gameId + "/sectors.xml");
        if (url == null)
            url = Main.class.getResource("data/" + gameId + "/sectors.xml");
        final SAXReader reader = new SAXReader();
        try
        {
            final RaceFactory raceFactory = this.game.getRaceFactory();
            final SunFactory sunsFactory = this.game.getSunFactory();
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
                final Sun suns = sunsFactory.getSun(Integer.parseInt(element
                    .attributeValue("suns")));
                final Race race = raceFactory.getRace(element
                    .attributeValue("race"));
                final boolean core = Boolean.parseBoolean(element
                    .attributeValue("core"));
                final String northId = element.attributeValue("north");
                final String eastId = element.attributeValue("east");
                final String southId = element.attributeValue("south");
                final String westId = element.attributeValue("west");

                final Sector sector;
                final List<?> switches = element.elements("switch");
                if (switches.size() == 0)
                {
                    final boolean shipyard = hasShipyard(element);
                    final Asteroid[] asteroids = getAsteroids(element);
                    sector =
                        new Sector(this.game, id, x, y, race, planets, suns,
                            core, shipyard, northId, eastId, southId, westId,
                            asteroids);
                }
                else
                {
                    final Asteroid[][] asteroidsList =
                        new Asteroid[switches.size()][];
                    int i = 0;
                    for (final Object switchItem : switches)
                    {
                        final Element switchElement = (Element) switchItem;
                        asteroidsList[i] = getAsteroids(switchElement);
                        i++;
                    }
                    sector =
                        new PlayerSector(this.game, id, x, y, race, planets,
                            suns, core, northId, eastId, southId, westId,
                            asteroidsList);
                }

                this.sectors.add(sector);
                this.sectorMap.put(id, sector);

                this.maxSiliconYield = Math.max(this.maxSiliconYield, sector
                    .getTotalSiliconYield());
                this.maxOreYield = Math.max(this.maxOreYield, sector
                    .getTotalOreYield());
                this.maxNividiumYield = Math.max(this.maxNividiumYield, sector
                    .getTotalNividiumYield());
                this.maxIceYield = Math.max(this.maxIceYield, sector
                    .getTotalIceYield());
            }
        }
        catch (final DocumentException e)
        {
            throw new DataException("Unable to read XML file: " + e, e);
        }
    }

    /**
     * Checks if sector has a shipyard.
     * 
     * @param element
     *            The sector XML element
     * @return True if sector has a shipyard, false if not
     */

    private boolean hasShipyard(final Element element)
    {
        final Element stationsElement = element.element("stations");
        if (stationsElement != null)
        {
            for (final Object stationItem : stationsElement
                .elements("station"))
            {
                final Element stationElement = (Element) stationItem;
                if (stationElement.attributeValue("class").equals(
                    "bigShipyard"))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the asteroids of the sector.
     * 
     * @param element
     *            The sector XML element
     * @return The asteroids
     */

    private Asteroid[] getAsteroids(final Element element)
    {
        final Element asteroidsElement = element.element("asteroids");
        final Asteroid[] asteroids;
        if (asteroidsElement != null)
        {
            final List<?> asteroidElements = asteroidsElement
                .elements("asteroid");
            asteroids = new Asteroid[asteroidElements.size()];
            int i = 0;
            for (final Object asteroidItem : asteroidElements)
            {
                final Element asteroidElement = (Element) asteroidItem;
                final String asteroidId = asteroidElement
                    .attributeValue("id");
                final int yield = Integer.parseInt(asteroidElement
                    .attributeValue("yield"));
                final int astX = Integer.parseInt(asteroidElement
                    .attributeValue("x"));
                final int astY = Integer.parseInt(asteroidElement
                    .attributeValue("y"));
                final int astZ = Integer.parseInt(asteroidElement
                    .attributeValue("z"));
                final Ware ware = this.game.getWareFactory().getWare(
                    asteroidElement
                        .attributeValue("ware"));
                final Asteroid asteroid = new Asteroid(asteroidId,
                    ware, yield, astX, astY, astZ);
                asteroids[i] = asteroid;
                i++;
            }
        }
        else
            asteroids = new Asteroid[0];

        return asteroids;
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

    /**
     * Returns the maximum silicon yield in a single sector.
     * 
     * @return The maximum silicon yield
     */

    public int getMaxSiliconYield()
    {
        return this.maxSiliconYield;
    }

    /**
     * Returns the maximum ore yield in a single sector.
     * 
     * @return The maximum ore yield
     */

    public int getMaxOreYield()
    {
        return this.maxOreYield;
    }

    /**
     * Returns the maximum nividium yield in a single sector.
     * 
     * @return The maximum nividium yield
     */

    public int getMaxNividiumYield()
    {
        return this.maxNividiumYield;
    }

    /**
     * Returns the maximum ice yield in a single sector.
     * 
     * @return The maximum ice yield
     */

    public int getMaxIceYield()
    {
        return this.maxIceYield;
    }
}
