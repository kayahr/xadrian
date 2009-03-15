/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;


/**
 * Static utility methods for common Swing tasks.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public final class SwingUtils
{
    /**
     * Private constructor to prevent instantiation
     */

    private SwingUtils()
    {
        // Empty
    }


    /**
     * Gives a component a popup menu
     * 
     * @param component
     *            The target component
     * @param popup
     *            The popup menu
     */

    public static void setPopupMenu(final JComponent component,
        final JPopupMenu popup)
    {
        component.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(final MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    popup.show(component, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(final MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    popup.show(component, e.getX(), e.getY());
                }
            }
        });
    }
}
