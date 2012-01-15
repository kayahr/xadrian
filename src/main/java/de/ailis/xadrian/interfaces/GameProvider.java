/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.interfaces;

import de.ailis.xadrian.data.Game;


/**
 * This interface is implemented by all components which provide a reference
 * to the selected game.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public interface GameProvider
{
    /**
     * Returns the game.
     *
     * @return Return the game.
     */
    public Game getGame();
}
