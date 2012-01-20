/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */
package de.ailis.xadrian.interfaces;

/**
 * This interface is implemented by all components which can be reseted.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public interface ResetProvider
{
    /**
     * Performs a reset.
     */
    public void reset();

    /**
     * Checks if a reset is allowed.
     *
     * @return True if reset is allowed, false if not
     */
    public boolean canReset();
}
