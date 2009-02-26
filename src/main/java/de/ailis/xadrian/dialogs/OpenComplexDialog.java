/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.dialogs;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import de.ailis.xadrian.support.I18N;


/**
 * Dialog for opening a complex.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class OpenComplexDialog extends JFileChooser
{
    /** Serial version UID */
    private static final long serialVersionUID = -2849909205937284265L;
    
    /** The singleton instance */
    private static final OpenComplexDialog instance = new OpenComplexDialog();


    /**
     * Constructor
     */

    private OpenComplexDialog()
    {
        super();
        setFileFilter(new FileNameExtensionFilter(I18N
            .getString("dialog.openComplex.filter"), "x3c"));
        setMultiSelectionEnabled(false);
    }


    /**
     * Returns the singleton instance.
     * 
     * @return The singleton instance
     */

    public static OpenComplexDialog getInstance()
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
        if (showOpenDialog(null) == APPROVE_OPTION)
        {
            return getSelectedFile();
        }
        else
        {
            return null;
        }
    }
}
