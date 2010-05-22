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
 * Exits the application
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public class ExitAction extends FrameAction<MainFrame>
{
    /** Serial version UID */
    private static final long serialVersionUID = 6016394500475659097L;


    /**
     * Constructor
     * 
     * @param frame
     *            The frame
     */

    public ExitAction(final MainFrame frame)
    {
        super(frame, "exit", Icons.EXIT);
    }


    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */

    public void actionPerformed(final ActionEvent e)
    {
        this.frame.exit();
    }
}
