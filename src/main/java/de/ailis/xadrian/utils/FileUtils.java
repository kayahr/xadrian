/*
 * $Id$
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.utils;

import java.io.File;


/**
 * Static file utility methods.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public final class FileUtils
{
    /**
     * Private constructor to prevent instantiation
     */

    private FileUtils()
    {
        // Empty
    }


    /**
     * Returns the file extension of the specified file. Returns null if the
     * file has no file extension.
     * 
     * @param file
     *            The file
     * @return The file extension or null if not found
     */

    public static String getExtension(final File file)
    {
        final String fileName = file.getName();
        final int pos = fileName.lastIndexOf('.');
        if (pos == -1) return null;
        return fileName.substring(pos + 1);
    }


    /**
     * Returns the filename without a file extension.
     * 
     * @param file
     *            The file
     * @return The filename without file extension
     */
    
    public static String getNameWithoutExt(final File file)
    {
        final String fileName = file.getName();
        final int pos = fileName.lastIndexOf('.');
        if (pos == -1) return fileName;
        return fileName.substring(0, pos);
    }
}
