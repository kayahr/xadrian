/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian;

import java.util.Locale;

import javax.swing.UIManager;

import de.ailis.xadrian.frames.MainFrame;


/**
 * Main class.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Main
{
    /**
     * Main method.
     * 
     * @param args
     *            Command line arguments
     * @throws Exception
     */

    public static void main(final String[] args) throws Exception
    {
        final String language = System.getenv().get("XADRIAN_LANG");
        if (language != null) Locale.setDefault(new Locale(language));

        final String sysThemeStr = System.getenv().get("XADRIAN_SYSTHEME");
        if (sysThemeStr == null || Boolean.parseBoolean(sysThemeStr))
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        new MainFrame().setVisible(true);
    }
}
