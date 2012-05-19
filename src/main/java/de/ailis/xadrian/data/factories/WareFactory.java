/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */
package de.ailis.xadrian.data.factories;

import java.io.Serializable;
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
import de.ailis.xadrian.data.Container;
import de.ailis.xadrian.data.Game;
import de.ailis.xadrian.data.Ware;
import de.ailis.xadrian.exceptions.DataException;
import de.ailis.xadrian.frames.SplashFrame;

/**
 * Factory for Ware objects.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class WareFactory implements Serializable
{
    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The game for which this factory is responsible. */
    private final Game game;

    /** The ware map (for quick ID navigation) */
    private final Map<String, Ware> wareMap = new HashMap<String, Ware>();

    /** The wares (sorted) */
    private final SortedSet<Ware> wares = new TreeSet<Ware>();

    /**
     * Constructor.
     *
     * @param game
     *            The game for which this factory is responsible.
     */
    public WareFactory(final Game game)
    {
        this.game = game;
        readData();
        SplashFrame.advanceProgress();
    }

    /**
     * Reads the data from the XML file.
     */
    private void readData()
    {
        String gameId = this.game.getId();
        URL url = Main.class.getResource("/" + gameId + "/wares.xml");
        if (url == null)
            url = Main.class.getResource("data/" + gameId + "/wares.xml");
        final SAXReader reader = new SAXReader();
        try
        {
            final Document document = reader.read(url);
            for (final Object item : document.getRootElement().elements("ware"))
            {
                final Element element = (Element) item;
                final String id = element.attributeValue("id");
                final int minPrice =
                    Integer.parseInt(element.attributeValue("minPrice"));
                final int avgPrice =
                    Integer.parseInt(element.attributeValue("avgPrice"));
                final int maxPrice =
                    Integer.parseInt(element.attributeValue("maxPrice"));
                final int volume =
                    Integer.parseInt(element.attributeValue("volume"));
                final Container container =
                    Container.valueOf(element.attributeValue("container"));
                final Ware ware =
                    new Ware(this.game, id, minPrice, avgPrice, maxPrice,
                        volume, container);
                this.wares.add(ware);
                this.wareMap.put(id, ware);
            }
        }
        catch (final DocumentException e)
        {
            throw new DataException("Unable to read XML file: " + e, e);
        }
    }

    /**
     * Returns all wares.
     *
     * @return The wares
     */
    public SortedSet<Ware> getWares()
    {
        return Collections.unmodifiableSortedSet(this.wares);
    }

    /**
     * Returns the ware with the specified id or null if not found.
     *
     * @param id
     *            The ware id
     * @return The ware or null if not found
     */
    public Ware getWare(final String id)
    {
        return this.wareMap.get(id);
    }
}
