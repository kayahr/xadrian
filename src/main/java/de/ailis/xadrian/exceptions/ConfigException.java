/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.exceptions;


/**
 * Thrown when something goes wrong with the configuration.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public final class ConfigException extends X3ToolsException
{
    /** Serial version UID */


    /** serialVersionUID */
    private static final long serialVersionUID = -6693756093147562489L;


    /**
     * Constructor
     * 
     * @param message
     *            The exception message
     */
    
    public ConfigException(final String message)
    {
        super(message);
    }


    /**
     * Constructor
     * 
     * @param message
     *            The exception message
     * @param cause
     *            The root cause
     */
    
    public ConfigException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
