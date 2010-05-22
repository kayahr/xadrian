/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.exceptions;


/**
 * Base class for all custom exceptions of this application.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class X3ToolsException extends RuntimeException
{
    /** Serial version UID */
    private static final long serialVersionUID = 3076213970661745531L;


    /**
     * Constructor
     * 
     * @param message
     *            The exception message
     */
    
    public X3ToolsException(final String message)
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
    
    public X3ToolsException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
