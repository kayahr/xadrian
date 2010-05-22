/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.dialogs;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.ailis.xadrian.support.Config;
import de.ailis.xadrian.support.I18N;


/**
 * Dialog for saving a complex.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public class SaveComplexDialog extends JFileChooser
{
    /** Serial version UID */
    private static final long serialVersionUID = -4869151957494169958L;

    /** The singleton instance */
    private static final SaveComplexDialog instance = new SaveComplexDialog();


    /**
     * Constructor
     */

    private SaveComplexDialog()
    {
        super();
        setFileFilter(new FileNameExtensionFilter(I18N
            .getString("dialog.saveComplex.filter"), "x3c"));
        setMultiSelectionEnabled(false);
        setFileSelectionMode(FILES_ONLY);
    }


    /**
     * Returns the singleton instance.
     * 
     * @return The singleton instance
     */

    public static SaveComplexDialog getInstance()
    {
        return instance;
    }


    /**
     * Opens the dialog. Returns the selected file or null if canceled.
     * 
     * @return The selected file or null if canceled
     */

    public File open()
    {
        setCurrentDirectory(Config.getInstance().getLastFileChooserPath());
        if (showSaveDialog(null) == APPROVE_OPTION)
        {
            Config.getInstance().setLastFileChooserPath(getCurrentDirectory());
            return getSelectedFile();
        }
        else
        {
            return null;
        }
    }
}
