/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */
package de.ailis.xadrian.exceptions;

/**
 * Thrown when something goes wrong with a template code.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public final class TemplateCodeException extends XadrianException
{
    /** Serial version UID */
    private static final long serialVersionUID = 1;

    /**
     * Constructor
     *
     * @param message
     *            The exception message
     */

    public TemplateCodeException(final String message)
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

    public TemplateCodeException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
