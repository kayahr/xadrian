/*
 * $Id$
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.exceptions;


/**
 * Thrown when something goes wrong with freemarker templates.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public final class FreemarkerException extends X3ToolsException
{
    /** Serial version UID */
    private static final long serialVersionUID = -2215161122911612446L;


    /**
     * Constructor
     * 
     * @param message
     *            The exception message
     */
    
    public FreemarkerException(final String message)
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
    
    public FreemarkerException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
