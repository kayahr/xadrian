/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.freemarker.directives;

import java.io.IOException;
import java.util.Map;

import de.ailis.xadrian.freemarker.BaseDirective;
import de.ailis.xadrian.support.I18N;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;


/**
 * Message directive for freemarker templates
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class MessageDirective extends BaseDirective
{
    /**
     * @see TemplateDirectiveModel#execute(Environment, Map, TemplateModel[],
     *      TemplateDirectiveBody)
     */

    @Override
    @SuppressWarnings("unchecked")
    public void execute(final Environment env, final Map params,
        final TemplateModel[] loopVars, final TemplateDirectiveBody body)
        throws TemplateException, IOException
    {
        if (body != null) throw new TemplateModelException("No body allowed");
        env.getOut().append(I18N.getString(getRequiredString(params, "key")));
    }
}
