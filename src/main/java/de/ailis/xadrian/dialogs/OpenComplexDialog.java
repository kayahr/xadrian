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
 * Dialog for opening a complex.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class OpenComplexDialog extends JFileChooser
{
    /** Serial version UID */
    private static final long serialVersionUID = -2849909205937284265L;


    /**
     * Constructor
     */

    public OpenComplexDialog()
    {
        super();
        setFileFilter(new FileNameExtensionFilter(I18N
            .getString("dialog.openComplex.filter"), "x3c"));
        setMultiSelectionEnabled(false);
        setFileSelectionMode(FILES_ONLY);
    }


    /**
     * Opens the dialog. Returns the selected file or null if canceled.
     *
     * @return The selected file or null if canceled
     */

    public File open()
    {
        setCurrentDirectory(Config.getInstance().getLastFileChooserPath());
        if (showOpenDialog(null) == APPROVE_OPTION)
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
