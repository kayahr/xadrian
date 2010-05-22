/*
 * $Id$
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.support;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JSplitPane;

import de.ailis.xadrian.Main;
import de.ailis.xadrian.data.Race;
import de.ailis.xadrian.data.factories.RaceFactory;
import de.ailis.xadrian.exceptions.ConfigException;
import de.ailis.xadrian.utils.SwingUtils;


/**
 * The configuration
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public final class Config
{
    /** Config key for ignored races */
    private static final String IGNORED_RACES = "ignoredraces";

    /** Config key for last file chooser path */
    private static final String LAST_FILE_CHOOSER_PATH = "lastfilechooserpath";

    /** The singleton instance */
    private static final Config instance = new Config();

    /** The ignored manufacturer races */
    private final List<Race> ignoredRaces = new ArrayList<Race>();

    /** The last file chooser path */
    private File lastFileChooserPath = null;


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
        if (System.getProperty("xadrian.config", "true").equals("true"))
        {
            final Preferences prefs = Preferences.userNodeForPackage(Main.class);
            final String races = prefs.get(IGNORED_RACES, null);
            if (races != null)
            {
                final RaceFactory factory = RaceFactory.getInstance();
                for (final String raceId : races.split(" "))
                {
                    this.ignoredRaces.add(factory.getRace(raceId));
                }
            }
            final String tmp = prefs.get(LAST_FILE_CHOOSER_PATH, null);
            this.lastFileChooserPath = tmp != null ? new File(tmp) : null;
        }
    }


    /**
     * Saves the configuration.
     */

    public void save()
    {
        if (System.getProperty("xadrian.config", "true").equals("true"))
        {
            final Preferences prefs = Preferences.userNodeForPackage(Main.class);
            if (this.ignoredRaces.isEmpty())
                prefs.remove(IGNORED_RACES);
            else
            {
                final StringBuilder builder = new StringBuilder();
                for (final Race race : this.ignoredRaces)
                {
                    if (builder.length() > 0) builder.append(' ');
                    builder.append(race.getId());
                }
                prefs.put(IGNORED_RACES, builder.toString());
            }
            if (this.lastFileChooserPath != null)
                prefs.put(LAST_FILE_CHOOSER_PATH, this.lastFileChooserPath
                    .getPath());
            else
                prefs.remove(LAST_FILE_CHOOSER_PATH);
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

    public void setRaceIgnored(final Race race, final boolean ignored)
    {
        // Do nothing if state is not changed
        if (isRaceIgnored(race) == ignored) return;

        if (ignored)
            this.ignoredRaces.add(race);
        else
            this.ignoredRaces.remove(race);
    }


    /**
     * Returns the last file chooser path.
     * 
     * @return The last file chooser path
     */

    public File getLastFileChooserPath()
    {
        return this.lastFileChooserPath;
    }


    /**
     * Sets the last file chooser path.
     * 
     * @param lastFileChooserPath
     *            The last file chooser path to set
     */

    public void setLastFileChooserPath(final File lastFileChooserPath)
    {
        this.lastFileChooserPath = lastFileChooserPath;
    }


    /**
     * Saves the window preferences.
     * 
     * @param window
     *            The window
     */

    public static void saveWindowState(final Window window)
    {
        final Preferences prefs = Preferences.userNodeForPackage(Main.class);

        // Window preferences are only saved if state is NORMAL
        if (!(window instanceof Frame)
            || ((Frame) window).getExtendedState() == Frame.NORMAL)
        {
            if (SwingUtils.isResizable(window))
            {
                prefs.putInt(getPrefsName(window, "width"), window.getWidth());
                prefs
                    .putInt(getPrefsName(window, "height"), window.getHeight());
            }
            prefs.putInt(getPrefsName(window, "left"), window.getX());
            prefs.putInt(getPrefsName(window, "top"), window.getY());
        }
    }


    /**
     * Restores the window state.
     * 
     * @param window
     *            The window
     */

    public static void restoreWindowState(final Window window)
    {
        final Preferences prefs = Preferences.userNodeForPackage(Main.class);
        if (SwingUtils.isResizable(window))
        {
            window.setSize(prefs.getInt(getPrefsName(window, "width"), window
                .getWidth()), prefs.getInt(getPrefsName(window, "height"),
                window.getHeight()));
//            window.setPreferredSize(window.getSize());
            //window.setMaximumSize(window.getSize());
        }
        window.setLocation(prefs.getInt(getPrefsName(window, "left"), window
            .getX()), prefs.getInt(getPrefsName(window, "top"), window.getY()));
    }


    /**
     * Saves the split pane preferences.
     * 
     * @param splitPane
     *            The split pane
     */

    public static void saveSplitPaneState(final JSplitPane splitPane)
    {
        final Preferences prefs = Preferences.userNodeForPackage(Main.class);
        prefs.putInt(getPrefsName(splitPane, "dividerLocation"), splitPane
            .getDividerLocation());
    }


    /**
     * Restores the split pane preferences.
     * 
     * @param splitPane
     *            The split pane
     */

    public static void restoreSplitPaneState(final JSplitPane splitPane)
    {
        final Preferences prefs = Preferences.userNodeForPackage(Main.class);
        splitPane.setDividerLocation(prefs.getInt(getPrefsName(splitPane,
            "dividerLocation"), splitPane.getDividerLocation()));
    }


    /**
     * Returns the preferences name for the specified component and for the
     * specified key.
     * 
     * @param component
     *            The component
     * @param key
     *            The key
     * @return The preferences name.
     */

    private static String getPrefsName(final Component component,
        final String key)
    {
        final String name = component.getName();
        if (name == null)
            throw new ConfigException(
                "Unable to save state of component with no name: " + component);
        return name.toLowerCase() + "." + key.toLowerCase();
    }
}
