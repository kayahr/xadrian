/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.ailis.xadrian.components.ComplexEditor;
import de.ailis.xadrian.frames.MainFrame;
import de.ailis.xadrian.listeners.MainStateListener;
import de.ailis.xadrian.resources.Icons;
import de.ailis.xadrian.support.FrameAction;


/**
 * Prints the current file.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public class PrintAction extends FrameAction<MainFrame> implements MainStateListener
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
        frame.addStateListener(this);
    }


    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */

    public void actionPerformed(final ActionEvent e)
    {
        final Component component = this.frame.getCurrentTab();
        if (component instanceof ComplexEditor)
            ((ComplexEditor) component).print();
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
