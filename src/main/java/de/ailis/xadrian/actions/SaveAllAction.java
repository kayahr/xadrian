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
 * Saves all modified files.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public class SaveAllAction extends FrameAction<MainFrame> implements MainStateListener
{
    /** Serial version UID */
    private static final long serialVersionUID = -455313858214195938L;


    /**
     * Constructor
     * 
     * @param frame
     *            The frame
     */

    public SaveAllAction(final MainFrame frame)
    {
        super(frame, "saveAll", Icons.SAVEALL);
        frame.addStateListener(this);
        setEnabled(false);
    }


    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */

    public void actionPerformed(final ActionEvent e)
    {
        for (final Component component: this.frame.getTabs().getComponents())
        {
            final ComplexEditor editor = (ComplexEditor) component;
            if (editor.isChanged())
            {
                editor.save();
                if (editor.isChanged()) return;
            }
        }
    }


    /**
     * @see MainStateListener#mainStateChanged(MainFrame)
     */
    
    @Override
    public void mainStateChanged(final MainFrame sender)
    {
        for (final Component component: sender.getTabs().getComponents())
        {
            final ComplexEditor editor = (ComplexEditor) component;
            if (editor.isChanged())
            {
                setEnabled(true);
                return;
            }
        }
        setEnabled(false);
    }
}
