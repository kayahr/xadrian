/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.freemarker;

import java.util.Map;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateModelException;


/**
 * BaseDirective
 * 
 * @author k
 * @version $Revision$
 */

public abstract class BaseDirective implements TemplateDirectiveModel
{
    /**
     * Returns a parameter as string. Returns null if parameter was not found.
     * 
     * @param params
     *            The parameters
     * @param name
     *            The parameter name
     * @return The parameter value or null if not set
     */

    protected String getString(final Map<?, ?> params, final String name)
    {
        final SimpleScalar value = (SimpleScalar) params.get(name);
        if (value == null) return null;
        return value.getAsString();
    }


    /**
     * Returns a parameter as string. Throws exception if parameter is not set
     * 
     * @param params
     *            The parameters
     * @param name
     *            The parameter name
     * @return The parameter value
     * @throws TemplateModelException
     *             If parameter is not set
     */
    
    protected String getRequiredString(final Map<?, ?> params, final String name)
        throws TemplateModelException
    {
        final String value = getString(params, name);
        if (value == null)
            throw new TemplateModelException("Param " + name + " is not set");
        return value;
    }
}
