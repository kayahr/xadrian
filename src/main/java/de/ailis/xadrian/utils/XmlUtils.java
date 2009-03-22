/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;


/**
 * Static XML utility methods.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public final class XmlUtils
{
    /**
     * Private constructor to prevent instantiation
     */

    private XmlUtils()
    {
        // Empty
    }


    /**
     * Writes the specified document to the specified file.
     * 
     * @param document
     *            The document to write
     * @param file
     *            The file to write to
     * @throws IOException
     *             If file could not be written
     */

    public static void write(final Document document, final File file)
        throws IOException
    {
        final OutputFormat format = OutputFormat.createPrettyPrint();
        final XMLWriter writer = new XMLWriter(new FileWriter(file), format);
        try
        {
            writer.write(document);
        }
        finally
        {
            writer.close();
        }
    }
}
