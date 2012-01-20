/*
 * Copyright (C) 2012-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */
package de.ailis.xadrian.data.factories;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import de.ailis.xadrian.data.Theme;

/**
 * Factory for themes.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class ThemeFactory
{
    /** The theme map  */
    private final Map<String, Theme> themeMap = new HashMap<String, Theme>();

    /** The themes (sorted) */
    private final SortedSet<Theme> themes = new TreeSet<Theme>();

    /** The singleton instance. */
    private final static ThemeFactory instance = new ThemeFactory();

    /**
     * Constructor.
     */
    private ThemeFactory()
    {
        for (LookAndFeelInfo laf: UIManager.getInstalledLookAndFeels())
        {
            Theme theme = new Theme(laf.getName(), laf.getClassName());
            this.themeMap.put(laf.getClassName(), theme);
            this.themes.add(theme);
        }
    }

    /**
     * Returns the singleton instance.
     *
     * @return The singleton instance.
     */
    public static ThemeFactory getInstance()
    {
        return instance;
    }

    /**
     * Returns all themes.
     *
     * @return The themes.
     */
    public SortedSet<Theme> getThemes()
    {
        return Collections.unmodifiableSortedSet(this.themes);
    }

    /**
     * Returns the theme with the specified class name.
     *
     * @param className
     *            The class name.
     * @return The theme or null if not found
     */
    public Theme getTheme(final String className)
    {
        return this.themeMap.get(className);
    }
}
