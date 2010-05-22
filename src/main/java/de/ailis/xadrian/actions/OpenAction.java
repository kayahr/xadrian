/*
 * $Id$
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.ailis.xadrian.components.ComplexEditor;
import de.ailis.xadrian.frames.MainFrame;
import de.ailis.xadrian.resources.Icons;
import de.ailis.xadrian.support.FrameAction;


/**
 * Opens a previously saved factory complex.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class OpenAction extends FrameAction<MainFrame>
{
    /** Serial version UID */
    private static final long serialVersionUID = 8330430085801091906L;


    /**
     * Constructor
     * 
     * @param frame
     *            The frame
     */

    public OpenAction(final MainFrame frame)
    {
        super(frame, "open", Icons.OPEN);
    }


    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */

    public void actionPerformed(final ActionEvent e)
    {
        final ComplexEditor complexEditor = ComplexEditor.open();
        if (complexEditor != null)
        {
            this.frame.createLoadedComplexTab(complexEditor);
        }
    }
}
