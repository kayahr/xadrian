/*
 * $Id$
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.freemarker;

import java.io.IOException;
import java.io.StringWriter;

import de.ailis.xadrian.Main;
import de.ailis.xadrian.exceptions.FreemarkerException;
import de.ailis.xadrian.freemarker.directives.MessageDirective;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;


/**
 * This factory is used to access the freemarker templates. It also manages the
 * freemarker engine configuration and the interface between the templates and
 * the model.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public final class TemplateFactory
{
    /** The singleton instance */
    private static final TemplateFactory instance = new TemplateFactory();

    /** The freemarker configuration */
    private final Configuration cfg;


    /**
     * Private constructor to prevent instantiation of singleton
     */

    private TemplateFactory()
    {
        this.cfg = new Configuration();
        this.cfg.setClassForTemplateLoading(Main.class, "templates");
        this.cfg.setObjectWrapper(new DefaultObjectWrapper());
        this.cfg.setDefaultEncoding("UTF-8");
        this.cfg.setSharedVariable("message", new MessageDirective());
        this.cfg.setTagSyntax(Configuration.SQUARE_BRACKET_TAG_SYNTAX);
    }


    /**
     * Returns the template with the specified name.
     * 
     * @param name
     *            The template name
     * @return The template
     */

    public static Template getTemplate(final String name)
    {
        try
        {
            return instance.cfg.getTemplate(name);
        }
        catch (final IOException e)
        {
            throw new FreemarkerException("Unable to load template " + name
                + ": " + e, e);
        }
    }


    /**
     * Processes the specified template and returns the result as a string.
     * 
     * @param template
     *            The template to process
     * @param rootMap
     *            The root map
     * @return The output
     */

    public static String processTemplate(final Template template,
        final Object rootMap)
    {
        try
        {
            final StringWriter writer = new StringWriter();
            try
            {
                template.process(rootMap, writer);
                return writer.toString();
            }
            finally
            {
                writer.close();
            }
        }
        catch (final TemplateException e)
        {
            throw new FreemarkerException("Unable to process template: " + e, e);
        }
        catch (final IOException e)
        {
            throw new FreemarkerException("Unable to process template: " + e, e);
        }
    }
}
