/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.data;

import java.awt.Color;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import de.ailis.xadrian.data.factories.SectorFactory;
import de.ailis.xadrian.support.Config;
import de.ailis.xadrian.support.I18N;
import de.ailis.xadrian.support.ReverseIntegerComparator;


/**
 * A sector.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Sector implements Serializable, Comparable<Sector>
{
    /** Serial version UID */
    private static final long serialVersionUID = -8004624270181949305L;

    /** The sector id */
    private final String id;

    /** The X position in the universe */
    private final int x;

    /** The Y position in the universe */
    private final int y;

    /** The race this sector belongs to */
    private final Race race;

    /** The number of planets in the sector */
    private final int planets;

    /** The suns */
    private final Suns suns;

    /** If this is a core sector or not */
    private final boolean core;

    /** The id of the sector which can be reached through the north gate */
    private final String northId;

    /** The id of the sector which can be reached through the north gate */
    private final String southId;

    /** The id of the sector which can be reached through the north gate */
    private final String westId;

    /** The id of the sector which can be reached through the north gate */
    private final String eastId;

    /** The message id */
    private final String messageId;

    /** If this sector has a shipyard or not */
    private final boolean shipyard;

    /** Array with asteroids in this sector */
    private final Asteroid[] asteroids;


    /**
     * Constructor
     * 
     * @param id
     *            The sector id
     * @param x
     *            The X position in the universe
     * @param y
     *            The Y position in the universe
     * @param race
     *            The race this sector belongs to
     * @param planets
     *            The number of planets in the sector
     * @param suns
     *            The suns
     * @param core
     *            If this is a core sector or not
     * @param shipyard
     *            If this sector has a shipyard or not
     * @param northId
     *            The id of the sector behind the north gate
     * @param eastId
     *            The id of the sector behind the east gate
     * @param southId
     *            The id of the sector behind the south gate
     * @param westId
     *            The id of the sector behind the west gate
     * @param asteroids
     *            Array with asteroids in this sector
     */

    public Sector(final String id, final int x, final int y, final Race race,
        final int planets, final Suns suns, final boolean core,
        final boolean shipyard, final String northId, final String eastId,
        final String southId, final String westId, final Asteroid[] asteroids)
    {
        this.id = id;
        this.messageId = "sector." + id;
        this.x = x;
        this.y = y;
        this.race = race;
        this.planets = planets;
        this.shipyard = shipyard;
        this.core = core;
        this.suns = suns;
        this.northId = northId;
        this.southId = southId;
        this.westId = westId;
        this.eastId = eastId;
        this.asteroids = asteroids;
    }


    /**
     * Return the sector id.
     * 
     * @return The sector id
     */

    public String getId()
    {
        return this.id;
    }


    /**
     * Returns the name.
     * 
     * @return The name
     */

    public String getName()
    {
        return I18N.getString(this.messageId);
    }


    /**
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.id).toHashCode();
    }


    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;
        final Sector other = (Sector) obj;
        return new EqualsBuilder().append(this.id, other.id).isEquals();
    }


    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */

    @Override
    public int compareTo(final Sector o)
    {
        return getName().compareTo(o.getName());
    }


    /**
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString()
    {
        return getName();
    }


    /**
     * Returns the x position in the universe.
     * 
     * @return The x position in the universe
     */

    public int getX()
    {
        return this.x;
    }


    /**
     * Returns the y position in the universe.
     * 
     * @return The y position in the universe
     */

    public int getY()
    {
        return this.y;
    }


    /**
     * Returns the race to which this sector belongs.
     * 
     * @return The race to which this sector belongs
     */

    public Race getRace()
    {
        return this.race;
    }


    /**
     * Returns the number of planets.
     * 
     * @return The number of planets
     */

    public int getPlanets()
    {
        return this.planets;
    }


    /**
     * Returns the suns.
     * 
     * @return The suns
     */

    public Suns getSuns()
    {
        return this.suns;
    }


    /**
     * Checks if this sector is a core sector or not.
     * 
     * @return True if core sector, false if not
     */

    public boolean isCore()
    {
        return this.core;
    }


    /**
     * Checks if this sector has a shipyard or not.
     * 
     * @return True if sector has a shipyard, false if not
     */

    public boolean hasShipyard()
    {
        return this.shipyard;
    }


    /**
     * Returns the sector behind the north gate.
     * 
     * @return The sector behind the north gate
     */

    public Sector getNorth()
    {
        return SectorFactory.getInstance().getSector(this.northId);
    }


    /**
     * Returns the sector behind the south gate.
     * 
     * @return The sector behind the south gate
     */

    public Sector getSouth()
    {
        return SectorFactory.getInstance().getSector(this.southId);
    }


    /**
     * Returns the sector behind the west gate.
     * 
     * @return The sector behind the west gate
     */

    public Sector getWest()
    {
        return SectorFactory.getInstance().getSector(this.westId);
    }


    /**
     * Returns the sector behind the east gate.
     * 
     * @return The sector behind the east gate
     */

    public Sector getEast()
    {
        return SectorFactory.getInstance().getSector(this.eastId);
    }


    /**
     * Returns the distance from this sector to the specified sector. If the
     * destination sector is unreachable (because it can't be reached via gates)
     * then -1 is returned.
     * 
     * @param dest
     *            The destination sector
     * @return The distance. -1 if destination sector is unreachable
     */

    public int getDistance(final Sector dest)
    {
        final Set<Sector> seen = new HashSet<Sector>();
        Set<Sector> todo = new HashSet<Sector>();

        // We begin with the current sector and a distance of 0
        todo.add(this);
        int distance = 0;

        // This loop is repeated until no more TODOs are present. If the
        // target sector is reached then the loop is exited with a return
        while (todo.size() > 0)
        {
            final Set<Sector> newTodo = new HashSet<Sector>();
            for (final Sector sector : todo)
            {
                // When we have reached our destination sector then return
                // the distance traveled so far
                if (dest.equals(sector)) return distance;

                // Mark this sector as already seen
                seen.add(sector);

                // Add the sectors which can be reached from the current sector
                // to the new TODOs list if they are not already seen
                Sector tmp = sector.getNorth();
                if (tmp != null && !seen.contains(tmp)) newTodo.add(tmp);
                tmp = sector.getEast();
                if (tmp != null && !seen.contains(tmp)) newTodo.add(tmp);
                tmp = sector.getWest();
                if (tmp != null && !seen.contains(tmp)) newTodo.add(tmp);
                tmp = sector.getSouth();
                if (tmp != null && !seen.contains(tmp)) newTodo.add(tmp);
            }

            // In the next run process our new TODOs list
            todo = newTodo;

            // Increment the distance
            distance++;
        }

        // No more TODOs and target sector was not reached
        return -1;
    }


    /**
     * Returns the nearest sector with a shipyard. This method honors the
     * ignored races the player has configured. It never considers a shipyard of
     * the Xenon. If no shipyard was found then null is returned.
     * 
     * @return The nearest sector with a shipyard or null if none
     */

    public Sector getNearestShipyardSector()
    {
        final Config config = Config.getInstance();
        final Set<Sector> seen = new HashSet<Sector>();
        Set<Sector> todo = new HashSet<Sector>();

        // We begin with the current sector
        todo.add(this);

        // This loop is repeated until no more TODOs are present. If the
        // target sector is reached then the loop is exited with a return
        while (todo.size() > 0)
        {
            final Set<Sector> newTodo = new HashSet<Sector>();
            for (final Sector sector : todo)
            {
                // When we have reached a sector with a shipyard and the player
                // can buy from it then return it.
                if (sector.hasShipyard()
                    && !sector.getRace().getId().equals("xenon")
                    && !sector.getRace().getId().equals("terran")
                    && !config.isRaceIgnored(sector.getRace())) return sector;

                // Mark this sector as already seen
                seen.add(sector);

                // Add the sectors which can be reached from the current sector
                // to the new TODOs list if they are not already seen
                Sector tmp = sector.getNorth();
                if (tmp != null && !seen.contains(tmp)) newTodo.add(tmp);
                tmp = sector.getEast();
                if (tmp != null && !seen.contains(tmp)) newTodo.add(tmp);
                tmp = sector.getWest();
                if (tmp != null && !seen.contains(tmp)) newTodo.add(tmp);
                tmp = sector.getSouth();
                if (tmp != null && !seen.contains(tmp)) newTodo.add(tmp);
            }

            // In the next run process our new TODOs list
            todo = newTodo;
        }

        // No more TODOs and no shipyard was found
        return null;
    }


    /**
     * Returns the array with asteroids.
     * 
     * @return The array with asteroids
     */

    public Asteroid[] getAsteroids()
    {
        return this.asteroids;
    }


    /**
     * Returns the total silicon yield of this sector.
     * 
     * @return The total silicon yield
     */

    public int getTotalSiliconYield()
    {
        int yield = 0;
        for (final Asteroid asteroid : this.asteroids)
            if (asteroid.getWare().getId().equals("siliconWafers"))
                yield += asteroid.getYield();
        return yield;
    }


    /**
     * Returns the total ore yield of this sector.
     * 
     * @return The total ore yield
     */

    public int getTotalOreYield()
    {
        int yield = 0;
        for (final Asteroid asteroid : this.asteroids)
            if (asteroid.getWare().getId().equals("ore"))
                yield += asteroid.getYield();
        return yield;
    }


    /**
     * Returns the total nividium yield of this sector.
     * 
     * @return The total nividium yield
     */

    public int getTotalNividiumYield()
    {
        int yield = 0;
        for (final Asteroid asteroid : this.asteroids)
            if (asteroid.getWare().getId().equals("nividium"))
                yield += asteroid.getYield();
        return yield;
    }


    /**
     * Returns the total ice yield of this sector.
     * 
     * @return The total ice yield
     */

    public int getTotalIceYield()
    {
        int yield = 0;
        for (final Asteroid asteroid : this.asteroids)
            if (asteroid.getWare().getId().equals("ice"))
                yield += asteroid.getYield();
        return yield;
    }


    /**
     * Returns the silicon color of this sector. The brighter the more silicon
     * is available.
     * 
     * @return The silicon color
     */

    public Color getSiliconColor()
    {
        final int max = SectorFactory.getInstance().getMaxSiliconYield();
        final int cur = this.getTotalSiliconYield();
        if (cur == 0) return Color.BLACK;
        final int intensity = Math.min(200, 200 * cur / max) + 55;
        return new Color(0, intensity, intensity);
    }


    /**
     * Returns the ore color of this sector. The brighter the more ore is
     * available.
     * 
     * @return The ore color
     */

    public Color getOreColor()
    {
        final int max = SectorFactory.getInstance().getMaxOreYield();
        final int cur = this.getTotalOreYield();
        if (cur == 0) return Color.BLACK;
        final int intensity = Math.min(200, 200 * cur / max) + 55;
        return new Color(0, intensity, intensity);
    }


    /**
     * Returns the nividium color of this sector. The brighter the more ore is
     * available.
     * 
     * @return The nividium color
     */

    public Color getNividiumColor()
    {
        final int max = SectorFactory.getInstance().getMaxNividiumYield();
        final int cur = this.getTotalNividiumYield();
        if (cur == 0) return Color.BLACK;
        final int intensity = Math.min(200, 200 * cur / max) + 55;
        return new Color(0, intensity, intensity);
    }


    /**
     * Returns the ice color of this sector. The brighter the more ore is
     * available.
     * 
     * @return The ice color
     */

    public Color getIceColor()
    {
        final int max = SectorFactory.getInstance().getMaxIceYield();
        final int cur = this.getTotalIceYield();
        if (cur == 0) return Color.BLACK;
        final int intensity = Math.min(200, 200 * cur / max) + 55;
        return new Color(0, intensity, intensity);
    }


    /**
     * Returns the yield map. This map has the yield as key and the number of
     * asteroids with this yield as value.
     * 
     * @param wareId
     *            The id of the asteroid ware to search for
     * @return The yield map
     */

    public SortedMap<Integer, Integer> getYields(final String wareId)
    {
        final SortedMap<Integer, Integer> yields = new TreeMap<Integer, Integer>(
            new ReverseIntegerComparator());

        // Iterate over all asteroids
        for (final Asteroid asteroid : this.asteroids)
        {
            // If this asteroid is not of the searched type then ignore it
            if (!wareId.equals(asteroid.getWare().getId())) continue;

            // Update the yield in the yields map
            final int yield = asteroid.getYield();
            Integer oldQuantity = yields.get(yield);
            if (oldQuantity == null) oldQuantity = 0;
            yields.put(yield, oldQuantity + 1);
        }

        // Return the yields map
        return yields;
    }
}
