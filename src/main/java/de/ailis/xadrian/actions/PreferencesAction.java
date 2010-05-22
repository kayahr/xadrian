/*
 * $Id$
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.ailis.xadrian.frames.MainFrame;
import de.ailis.xadrian.resources.Icons;
import de.ailis.xadrian.support.FrameAction;


/**
 * Displays the preferences dialog.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public class PreferencesAction extends FrameAction<MainFrame>
{
    /** Serial version UID */
    private static final long serialVersionUID = 9160570583084250685L;


    /**
     * Constructor
     * 
     * @param frame
     *            The frame
     */

    public PreferencesAction(final MainFrame frame)
    {
        super(frame, "preferences", Icons.PREFS);
    }


    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */

    public void actionPerformed(final ActionEvent e)
    {
        this.frame.preferences();
    }
}
