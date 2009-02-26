/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.exceptions;


/**
 * Thrown when something goes wrong with loading data.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public final class DataException extends X3ToolsException
{
    /** Serial version UID */
    private static final long serialVersionUID = -5264397343641378695L;


    /**
     * Constructor
     * 
     * @param message
     *            The exception message
     */
    
    public DataException(final String message)
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
    
    public DataException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
