/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de> See LICENSE.txt file for
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
import de.ailis.xadrian.data.Game;
import de.ailis.xadrian.exceptions.DataException;
import de.ailis.xadrian.exceptions.GameNotFoundException;
import de.ailis.xadrian.frames.SplashFrame;

/**
 * Factory for Game objects.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public class GameFactory
{
    /** The game map (for quick ID navigation) */
    private final Map<String, Game> gameMap = new HashMap<String, Game>();

    /** The games (sorted) */
    private final SortedSet<Game> games = new TreeSet<Game>();

    /** The singleton instance */
    private final static GameFactory instance = new GameFactory();

    /**
     * Private constructor to prevent instantiation from outside.
     */
    private GameFactory()
    {
        readData();
    }

    /**
     * Returns the singleton instance.
     * 
     * @return The singleton instance
     */
    public static final GameFactory getInstance()
    {
        return instance;
    }

    /**
     * Reads the data from the XML file.
     */
    private void readData()
    {
        URL url = Main.class.getResource("/games.xml");
        if (url == null) url = Main.class.getResource("data/games.xml");
        final SAXReader reader = new SAXReader();
        try
        {
            final Document document = reader.read(url);
            List<Object> games = document.getRootElement().elements("game");
            SplashFrame.getInstance().setNumberOfGames(games.size());
            for (final Object item : games)
            {
                final Element element = (Element) item;
                final String id = element.attributeValue("id");
                final Game game = new Game(id);
                this.games.add(game);
                this.gameMap.put(id, game);
            }
        }
        catch (final DocumentException e)
        {
            throw new DataException("Unable to read XML file: " + e, e);
        }
    }

    /**
     * Returns all games.
     * 
     * @return The games
     */
    public SortedSet<Game> getGames()
    {
        return Collections.unmodifiableSortedSet(this.games);
    }

    /**
     * Returns the game with the specified id.
     * 
     * @param id
     *            The game id
     * @return The game.
     * @throws GameNotFoundException When game was not found.
     */
    public Game getGame(final String id) throws GameNotFoundException
    {
        Game game = this.gameMap.get(id);
        if (game == null) throw new GameNotFoundException(id);
        return game;
    }

    /**
     * Returns the number of games.
     * 
     * @return The number of games
     */
    public int countGames()
    {
        return this.games.size();
    }
}
