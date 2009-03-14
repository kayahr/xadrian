/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.resources;

import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;


/**
 * The icons used in this application.
 * 
 * @author Klaus Reimer (k@ailis.de
 * @version $Revision$
 */

public final class Icons
{
    /** The logo icon (Used in about dialog) */
    public static final Icon LOGO = createIcon("xadrian-64");
    
    /** The EXIT icon */
    public static final Icon EXIT = createIcon("exit");

    /** The NEW icon */
    public static final Icon NEW = createIcon("new");

    /** The CLOSE icon */
    public static final Icon CLOSE = createIcon("close");

    /** The ADD icon */
    public static final Icon ADD = createIcon("add");

    /** The CLOSEALL icon */
    public static final Icon CLOSEALL = createIcon("closeall");

    /** The COPY icon */
    public static final Icon COPY = createIcon("copy");

    /** The DELETE icon */
    public static final Icon DELETE = createIcon("delete");

    /** The EDIT icon */
    public static final Icon EDIT = createIcon("edit");

    /** The HELP icon */
    public static final Icon HELP = createIcon("help");

    /** The INFO icon */
    public static final Icon INFO = createIcon("info");

    /** The OPEN icon */
    public static final Icon OPEN = createIcon("open");

    /** The SELECT ALL icon */
    public static final Icon SELECT_ALL = createIcon("selectall");

    /** The PRINT icon */
    public static final Icon PRINT = createIcon("print");

    /** The SAVE icon */
    public static final Icon SAVE = createIcon("save");

    /** The SAVEALL icon */
    public static final Icon SAVEALL = createIcon("saveall");

    /** The SAVEAS */
    public static final Icon SAVEAS = createIcon("saveas");

    /** The WAND icon */
    public static final Icon WAND = createIcon("wand");

    /** The SUNS icon */
    public static final Icon SUNS = createIcon("suns");

    /** The SUNS icon */
    public static final Icon SECTOR = createIcon("sector");

    /** The PREFS icon */
    public static final Icon PREFS = createIcon("prefs");


    /**
     * Private constructor to prevent instantiation.
     */

    private Icons()
    {
        // Empty
    }


    /**
     * Creates the icon for the specified filename and returns it.
     * 
     * @param filename
     *            The icon name (just the filename without extension)
     * @return The icon
     */

    private static ImageIcon createIcon(final String filename)
    {
        final URL url = Icons.class.getResource("/de/ailis/xadrian/images/"
            + filename + ".png");
        if (url != null)
        {
            return new ImageIcon(url);
        }
        else
        {
            System.err.println("Couldn't find file: " + filename);
            return null;
        }
    }
}
