/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.data.factories;

import java.util.HashMap;
import java.util.Map;

import de.ailis.xadrian.data.Game;
import de.ailis.xadrian.data.Station;


/**
 * Factory for Station objects.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public class StationFactory
{
    /** The game for which this factory is responsible. */
    private final Game game;
    
    /** The station map (for quick ID navigation) */
    private final Map<String, Station> stationMap = new HashMap<String, Station>();

    /**
     * Constructor.
     * 
     * @param game
     *            The game for which this factory is responsible.
     */
    public StationFactory(final Game game)
    {
        this.game = game;
    }


    /**
     * Returns the station with the specified id or null if not found.
     * 
     * @param id
     *            The station id
     * @param sectorId
     *            The sector id
     * @return The station or null if not found
     */

    public Station getStation(final String id, final String sectorId)
    {
        Station shipYard = this.stationMap.get(id);
        if (shipYard == null)
        {
            shipYard = new Station(id, this.game.getSectorFactory().getSector(
                sectorId));
            this.stationMap.put(id, shipYard);
        }
        return shipYard;
    }
}
