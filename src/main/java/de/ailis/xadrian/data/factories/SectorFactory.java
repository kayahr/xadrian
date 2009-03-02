/*
 * $Id: WareFactory.java 752 2009-03-01 23:24:14Z k $
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.data.factories;

import java.util.HashMap;
import java.util.Map;

import de.ailis.xadrian.data.Sector;


/**
 * Factory for Sector objects.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 752 $
 */

public class SectorFactory
{
    /** The sector map (for quick ID navigation) */
    private final Map<String, Sector> sectorMap = new HashMap<String, Sector>();

    /** The singleton instance */
    private final static SectorFactory instance = new SectorFactory();


    /**
     * Private constructor to prevent instantiation from outside.
     */

    private SectorFactory()
    {
        // Empty
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
     * Returns the sector with the specified id or null if not found.
     * 
     * @param id
     *            The sector id
     * @return The sector or null if not found
     */

    public Sector getSector(final String id)
    {
        Sector sector = this.sectorMap.get(id);
        if (sector == null)
        {
            sector = new Sector(id);
            this.sectorMap.put(id, sector);
        }
        return sector;
    }
}
