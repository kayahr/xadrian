/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.ailis.xadrian.frames.MainFrame;
import de.ailis.xadrian.resources.Icons;
import de.ailis.xadrian.support.FrameAction;


/**
 * Prints the current file.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class PrintAction extends FrameAction<MainFrame>
{
    /** Serial version UID */
    private static final long serialVersionUID = -6423012855791954539L;


    /**
     * Constructor
     * 
     * @param frame
     *            The frame
     */

    public PrintAction(final MainFrame frame)
    {
        super(frame, "print", Icons.PRINT);
        setEnabled(false);
    }


    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */

    public void actionPerformed(final ActionEvent e)
    {
        // Empty
    }
}
