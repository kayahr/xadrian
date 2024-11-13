/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.oneinstance;

/**
 * Thrown when port is already in use.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public final class PortAlreadyInUseException extends Exception
{
    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     */
    public PortAlreadyInUseException()
    {
        super();
    }
}
