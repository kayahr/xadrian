/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */

package de.ailis.xadrian;

import de.ailis.oneinstance.OneInstance;
import de.ailis.xadrian.data.factories.GameFactory;
import de.ailis.xadrian.dialogs.AboutDialog;
import de.ailis.xadrian.dialogs.ChangeQuantityDialog;
import de.ailis.xadrian.dialogs.OpenComplexDialog;
import de.ailis.xadrian.dialogs.PreferencesDialog;
import de.ailis.xadrian.dialogs.SaveComplexDialog;
import de.ailis.xadrian.dialogs.SelectGameDialog;
import de.ailis.xadrian.frames.MainFrame;
import de.ailis.xadrian.frames.SplashFrame;
import de.ailis.xadrian.support.ErrorHandler;
import de.ailis.xadrian.utils.SwingUtils;

/**
 * Main class.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public class Main
{
    /** The application name. */
    private static final String APP_NAME = "Xadrian";

    /**
     * Opens the splash screen.
     */
    /**
     * Main method.
     * 
     * @param args
     *            Command line arguments
     * @throws Exception
     *             When something goes wrong
     */
    public static void main(final String[] args) throws Exception
    {
        // If Xadrian is already running then focus the already running
        // Xadrian and pass command line arguments to it. This allows us
        // to open more complexes in the already running Xadrian by
        // double-clicking the *.x3c files.
        if (!OneInstance.getInstance().register(Main.class, args))
            System.exit(0);

        try
        {
            // Set the application name (needed for Gnome Shell for example)
            SwingUtils.setAppName(APP_NAME);

            // Set theme and locale
            SwingUtils.prepareGUI();

            // Install the error handler
            ErrorHandler.install();

            // Open the splash screen.
            SplashFrame.open();

            // Preload everything
            GameFactory.getInstance().getGames();
            AboutDialog.getInstance();
            OpenComplexDialog.getInstance();
            SaveComplexDialog.getInstance();
            ChangeQuantityDialog.getInstance();
            PreferencesDialog.getInstance();
            SelectGameDialog.getInstance();

            // Close the splash screen and open the main window
            SplashFrame.close();
            MainFrame.open(args);
        }
        catch (Throwable t)
        {
            ErrorHandler.showError(t);
            System.exit(1);
        }
    }
}
