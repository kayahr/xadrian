/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de> See LICENSE.TXT for licensing
 * information
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

    /** Config key for displaying factory resources in complex table */
    private static final String SHOW_FACTORY_RESOURCES = "showfactoryresources";

    /** COnfig key for the index of the chosen player sector */
    private static final String PLAYER_SECTOR = "playerSector";

    /** The singleton instance. */
    private static final Config instance = new Config();

    /** The ignored manufacturer races */
    private final List<String> ignoredRaces = new ArrayList<String>();

    /** The last file chooser path */
    private File lastFileChooserPath = null;

    /** The Factory Description flag */
    private boolean showFactoryResources = true;

    /** The index of the chosen player sector */
    private int playerSector = 0;

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
        final Preferences prefs = Preferences
            .userNodeForPackage(Main.class);
        final String races = prefs.get(IGNORED_RACES, null);
        if (races != null)
            for (final String raceId : races.split(" "))
                this.ignoredRaces.add(raceId);
        final String tmp = prefs.get(LAST_FILE_CHOOSER_PATH, null);
        this.lastFileChooserPath = tmp != null ? new File(tmp) : null;
        this.showFactoryResources = prefs.getBoolean(
            SHOW_FACTORY_RESOURCES, true);
        this.playerSector = prefs.getInt(PLAYER_SECTOR, 0);
    }

    /**
     * Resets the configuration.
     */

    public void reset()
    {
        this.ignoredRaces.clear();
        this.showFactoryResources = true;
        this.lastFileChooserPath = null;

    }

    /**
     * Saves the configuration.
     */

    public void save()
    {
        final Preferences prefs = Preferences
            .userNodeForPackage(Main.class);
        if (this.ignoredRaces.isEmpty())
            prefs.remove(IGNORED_RACES);
        else
        {
            final StringBuilder builder = new StringBuilder();
            for (final String raceId : this.ignoredRaces)
            {
                if (builder.length() > 0) builder.append(' ');
                builder.append(raceId);
            }
            prefs.put(IGNORED_RACES, builder.toString());
        }
        if (this.lastFileChooserPath != null)
            prefs.put(LAST_FILE_CHOOSER_PATH, this.lastFileChooserPath
                .getPath());
        else
            prefs.remove(LAST_FILE_CHOOSER_PATH);
        prefs.putBoolean(SHOW_FACTORY_RESOURCES, this.showFactoryResources);
        prefs.putInt(PLAYER_SECTOR, this.playerSector);
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
        return this.ignoredRaces.contains(race.getId());
    }

    /**
     * Checks if the factory resources should be displayed in the complex table.
     * 
     * @return True if factory resources should be displayed, false if not
     */

    public boolean isShowFactoryResources()
    {
        return this.showFactoryResources;
    }

    /**
     * Enables or disables the display of factory resources in complex table.
     * 
     * @param showFactoryResources
     *            True if resources should be displayed, false if not
     */

    public void setShowFactoryResources(final boolean showFactoryResources)
    {
        this.showFactoryResources = showFactoryResources;
    }

    /**
     * Sets the ignore status of a race.
     * 
     * @param race
     *            The race.
     * @param ignored
     *            If the race should be ignored or not
     */

    public void setRaceIgnored(Race race, final boolean ignored)
    {
        // Do nothing if state is not changed
        if (isRaceIgnored(race) == ignored) return;

        if (ignored)
            this.ignoredRaces.add(race.getId());
        else
            this.ignoredRaces.remove(race.getId());
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
                    .putInt(getPrefsName(window, "height"), window
                        .getHeight());
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
            // window.setPreferredSize(window.getSize());
            // window.setMaximumSize(window.getSize());
        }
        window.setLocation(prefs.getInt(getPrefsName(window, "left"), window
            .getX()), prefs.getInt(getPrefsName(window, "top"), window
            .getY()));
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
                "Unable to save state of component with no name: "
                    + component);
        return name.toLowerCase() + "." + key.toLowerCase();
    }

    /**
     * Sets the player sector.
     * 
     * @param playerSector
     *            The player sector to set
     */

    public void setPlayerSector(final int playerSector)
    {
        this.playerSector = playerSector;
    }

    /**
     * Returns the index of the selected player sector.
     * 
     * @return The player sector index
     */

    public int getPlayerSector()
    {
        return this.playerSector;
    }
}
