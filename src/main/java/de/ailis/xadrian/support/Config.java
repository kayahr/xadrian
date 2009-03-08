/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.support;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import de.ailis.xadrian.Main;
import de.ailis.xadrian.data.Race;
import de.ailis.xadrian.data.factories.RaceFactory;


/**
 * The configuration
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public final class Config
{   
    /** The ignored manufacturer races */
    private final List<Race> ignoredRaces = new ArrayList<Race>();

    /** The singleton instance */
    private static final Config instance = new Config();
    
    /** Config key for ignored races */
    private static final String IGNORED_RACES = "ignoredRaces";


    /**
     * Private constructor to prevent instantiation
     */

    private Config()
    {
        load();
    }


    /**
     * Returns the singleton instance.
     * 
     * @return The singleton instance
     */

    public static Config getInstance()
    {
        return instance;
    }


    /**
     * Loads the configuration.
     */

    private void load()
    {
        final Preferences prefs = Preferences.userNodeForPackage(Main.class);
        final String races = prefs.get(IGNORED_RACES, null);
        if (races != null)
        {
            final RaceFactory factory = RaceFactory.getInstance();
            for (final String raceId: races.split(" "))
            {
                this.ignoredRaces.add(factory.getRace(raceId));
            }
        }
    }


    /**
     * Saves the configuration.
     */

    public void save()
    {
        final Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (this.ignoredRaces.isEmpty())
            prefs.remove(IGNORED_RACES);
        else
        {
            final StringBuilder builder = new StringBuilder();
            for (final Race race: this.ignoredRaces)
            {
                if (builder.length() > 0) builder.append(' ');
                builder.append(race.getId());
            }
            prefs.put(IGNORED_RACES, builder.toString());
        }
    }


    /**
     * Returns true if the specified race is ignored.
     * 
     * @param race
     *            The race to check
     * @return True if the specified race is ignored
     */

    public boolean isRaceIgnored(final Race race)
    {
        return this.ignoredRaces.contains(race);
    }


    /**
     * Sets the ignore status of a race.
     * 
     * @param race
     *            The race
     * @param ignored
     *            If the race should be ignored or not
     */

    public void setRaceIgnored(final Race race,
        final boolean ignored)
    {
        // Do nothing if state is not changed
        if (isRaceIgnored(race) == ignored) return;

        if (ignored)
            this.ignoredRaces.add(race);
        else
            this.ignoredRaces.remove(race);
    }
}
