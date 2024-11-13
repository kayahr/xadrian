/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
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
    /** The game map (for quick textual ID navigation) */
    private final Map<String, Game> gameMap = new HashMap<String, Game>();

    /** The game map (for quick numeric ID navigation) */
    private final Map<Integer, Game> gameNidMap = new HashMap<Integer, Game>();

    /** The games (sorted) */
    private final SortedSet<Game> games = new TreeSet<Game>();

    /** The singleton instance */
    private final static GameFactory instance = new GameFactory();

    /** The default game. */
    private Game defaultGame;

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
            List<Element> games = document.getRootElement().elements("game");
            SplashFrame.setNumberOfGames(games.size());
            for (final Object item : games)
            {
                final Element element = (Element) item;
                final String id = element.attributeValue("id");
                final int nid = Integer.parseInt(element.attributeValue("nid"));
                final Game game = new Game(nid, id);
                this.games.add(game);
                this.gameNidMap.put(nid, game);
                this.gameMap.put(id, game);
                if (this.defaultGame == null
                    || Boolean.parseBoolean(element
                        .attributeValue("default", "false")))
                    this.defaultGame = game;
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
     * Returns the game with the specified textual id.
     *
     * @param id
     *            The textual game id
     * @return The game.
     * @throws GameNotFoundException
     *             When game was not found.
     */
    public Game getGame(final String id) throws GameNotFoundException
    {
        Game game = this.gameMap.get(id);
        if (game == null) throw new GameNotFoundException(id);
        return game;
    }

    /**
     * Returns the game with the specified numeric id.
     *
     * @param nid
     *            The numeric game id
     * @return The game.
     * @throws GameNotFoundException
     *             When game was not found.
     */
    public Game getGame(final int nid) throws GameNotFoundException
    {
        Game game = this.gameNidMap.get(nid);
        if (game == null) throw new GameNotFoundException(nid);
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

    /**
     * Returns the default game.
     *
     * @return The default game.
     */
    public Game getDefaultGame()
    {
        return this.defaultGame;
    }

    /**
     * Checks if a game with the specified id exists.
     *
     * @param id
     *            The game ID to check.
     * @return True if game exists, false if not.
     */
    public boolean hasGame(String id)
    {
        return this.gameMap.containsKey(id);
    }
}
