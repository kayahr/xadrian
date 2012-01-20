/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */
package de.ailis.xadrian.exceptions;

/**
 * Thrown when something goes wrong with loading resources.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public final class ResourceException extends XadrianException
{
    /** Serial version UID */
    private static final long serialVersionUID = 6729604082474516588L;

    /**
     * Constructor
     *
     * @param message
     *            The exception message
     */

    public ResourceException(final String message)
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

    public ResourceException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
