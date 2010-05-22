/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.ailis.xadrian.frames.MainFrame;
import de.ailis.xadrian.listeners.MainStateListener;
import de.ailis.xadrian.resources.Icons;
import de.ailis.xadrian.support.FrameAction;


/**
 * Closes all open tabs.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public class CloseAllAction extends FrameAction<MainFrame> implements MainStateListener
{
    /** Serial version UID */
    private static final long serialVersionUID = 3365648582314124361L;


    /**
     * Constructor
     * 
     * @param frame
     *            The frame
     */

    public CloseAllAction(final MainFrame frame)
    {
        super(frame, "closeAll", Icons.CLOSEALL);
        setEnabled(false);
        frame.addStateListener(this);
    }


    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */

    public void actionPerformed(final ActionEvent e)
    {
        this.frame.closeAllTabs();
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
