/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */
package de.ailis.xadrian.exceptions;

/**
 * Thrown when a game was not found.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public final class GameNotFoundException extends XadrianException
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     *
     * @param id
     *           The ID of the game which was not found.
     */
    public GameNotFoundException(final String id)
    {
        super("No game with id " + id + " found");
    }


    /**
     * Constructor
     *
     * @param nid
     *           The numeric ID of the game which was not found.
     */
    public GameNotFoundException(final int nid)
    {
        super("No game with numeric id " + nid + " found");
    }
}
