/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
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
import de.ailis.xadrian.data.Game;
import de.ailis.xadrian.data.Sun;
import de.ailis.xadrian.exceptions.DataException;
import de.ailis.xadrian.frames.SplashFrame;

/**
 * Factory for sun objects.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class SunFactory
{
    /** The game for which this factory is responsible. */
    private final Game game;

    /** The Suns map (percent to Suns). */
    private final Map<Integer, Sun> sunMap = new HashMap<Integer, Sun>();

    /** The suns (sorted). */
    private final SortedSet<Sun> suns = new TreeSet<Sun>();

    /** The default suns. */
    private Sun defaultSuns;

    /**
     * Constructor.
     *
     * @param game
     *            The game for which this factory is responsible.
     */
    public SunFactory(final Game game)
    {
        this.game = game;
        readData();
        SplashFrame.getInstance().advanceProgress();
    }

    /**
     * Reads the data from the XML file.
     */
    private void readData()
    {
        String gameId = this.game.getId();
        URL url = Main.class.getResource("/" + gameId + "/suns.xml");
        if (url == null)
            url = Main.class.getResource("data/" + gameId + "/suns.xml");
        final SAXReader reader = new SAXReader();
        try
        {
            final Document document = reader.read(url);
            for (final Object item : document.getRootElement().elements("sun"))
            {
                final Element element = (Element) item;
                final int percent = Integer.parseInt(element
                    .attributeValue("percent"));
                final int cycle = Integer.parseInt(element
                    .attributeValue("cycle"));
                final Sun suns = new Sun(this.game, percent, cycle);
                if (this.defaultSuns == null
                    || Boolean.parseBoolean(element
                        .attributeValue("default", "false")))
                    this.defaultSuns = suns;
                this.suns.add(suns);
                this.sunMap.put(percent, suns);
            }
        }
        catch (final DocumentException e)
        {
            throw new DataException("Unable to read XML file: " + e, e);
        }
    }

    /**
     * Returns all suns.
     *
     * @return The all suns.
     */
    public SortedSet<Sun> getSuns()
    {
        return Collections.unmodifiableSortedSet(this.suns);
    }

    /**
     * Returns the sun with the specified percent.
     *
     * @param percent
     *            The percent
     * @return The sun. Never null.
     * @throws DataException
     *             When no sun with specified percent was found.
     */
    public Sun getSun(final int percent)
    {
        if (!this.sunMap.containsKey(percent))
            throw new DataException("There are no " + percent + "% suns");
        return this.sunMap.get(percent);
    }

    /**
     * Returns the default sun.
     *
     * @return The default sun.
     */
    public Sun getDefaultSun()
    {
        return this.defaultSuns;
    }

    /**
     * Returns the maximum percent.
     *
     * @return The maximum percent.
     */
    public int getMaxPercent()
    {
        return this.suns.last().getPercent();
    }
}
