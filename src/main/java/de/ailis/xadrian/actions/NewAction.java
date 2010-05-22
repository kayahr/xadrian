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
 * Starts a new factory complex tab.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class NewAction extends FrameAction<MainFrame>
{
    /** Serial version UID */
    private static final long serialVersionUID = -6567399121371477027L;


    /**
     * Constructor
     * 
     * @param frame
     *            The frame
     */

    public NewAction(final MainFrame frame)
    {
        super(frame, "new", Icons.NEW);
    }


    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */

    public void actionPerformed(final ActionEvent e)
    {
        this.frame.createComplexTab();
    }
}
