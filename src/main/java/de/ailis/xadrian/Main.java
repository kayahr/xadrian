/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian;

import de.ailis.xadrian.data.factories.GameFactory;
import de.ailis.xadrian.dialogs.AboutDialog;
import de.ailis.xadrian.dialogs.ChangeQuantityDialog;
import de.ailis.xadrian.dialogs.OpenComplexDialog;
import de.ailis.xadrian.dialogs.PreferencesDialog;
import de.ailis.xadrian.dialogs.SaveComplexDialog;
import de.ailis.xadrian.dialogs.SelectGameDialog;
import de.ailis.xadrian.frames.MainFrame;
import de.ailis.xadrian.frames.SplashFrame;
import de.ailis.xadrian.utils.SwingUtils;


/**
 * Main class.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class Main
{
    /**
     * Main method.
     *
     * @param args
     *            Command line arguments
     * @throws Exception
     *            When something goes wrong
     */

    public static void main(final String[] args) throws Exception
    {
        SwingUtils.prepareGUI();
        
        // Open the splash screen.
        SplashFrame splash = SplashFrame.getInstance();
        splash.setVisible(true);
        
        // Preload everything        
        GameFactory.getInstance().getGames();
        AboutDialog.getInstance();
        OpenComplexDialog.getInstance();
        SaveComplexDialog.getInstance();
        ChangeQuantityDialog.getInstance();
        PreferencesDialog.getInstance();
        SelectGameDialog.getInstance();
        
        
        // Close the splash screen and open the main window
        splash.setVisible(false);
        
        new MainFrame().setVisible(true);
    }
}
