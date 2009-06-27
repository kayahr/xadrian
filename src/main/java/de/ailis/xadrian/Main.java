/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian;

import de.ailis.xadrian.frames.MainFrame;
import de.ailis.xadrian.utils.SwingUtils;


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
        SwingUtils.prepareGUI();
        new MainFrame().setVisible(true);
    }
}
