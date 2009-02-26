/*
 * $Id: NewComplexAction.java 704 2009-02-20 08:10:11Z k $
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.ailis.xadrian.components.ComplexEditor;
import de.ailis.xadrian.frames.MainFrame;
import de.ailis.xadrian.listeners.MainStateListener;
import de.ailis.xadrian.support.FrameAction;


/**
 * Changes the sun power.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 704 $
 */

public class SetSunsAction extends FrameAction<MainFrame> implements
    MainStateListener
{
    /** Serial version UID */
    private static final long serialVersionUID = 4088425344137553110L;


    /**
     * Constructor
     * 
     * @param frame
     *            The frame
     */

    public SetSunsAction(final MainFrame frame)
    {
        super(frame, "setSuns", null);
        setEnabled(false);
        frame.addStateListener(this);
    }


    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */

    public void actionPerformed(final ActionEvent e)
    {
        ((ComplexEditor) this.frame.getCurrentTab()).changeSuns();
    }


    /**
     * @see MainStateListener#mainStateChanged(MainFrame)
     */

    @Override
    public void mainStateChanged(final MainFrame sender)
    {
        setEnabled(sender.getTabs().getComponentCount() > 0);
    }
}
