/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.utils;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.text.JTextComponent;


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


    /**
     * Installs a workaround for bug #4699955 in a JSpinner.
     * 
     * @param spinner
     *            The spinner to fix
     */

    public static void installSpinnerBugWorkaround(final JSpinner spinner)
    {
        ((DefaultEditor) spinner.getEditor()).getTextField().addFocusListener(
            new FocusAdapter()
            {
                @Override
                public void focusGained(final FocusEvent e)
                {
                    if (e.getSource() instanceof JTextComponent)
                    {
                        final JTextComponent text = ((JTextComponent) e
                            .getSource());
                        SwingUtilities.invokeLater(new Runnable()
                        {
                            public void run()
                            {
                                text.selectAll();
                            }
                        });
                    }
                }
            });
        spinner.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(final FocusEvent e)
            {
                if (e.getSource() instanceof JSpinner)
                {
                    final JTextComponent text = ((DefaultEditor) ((JSpinner) e
                        .getSource()).getEditor()).getTextField();
                    SwingUtilities.invokeLater(new Runnable()
                    {
                        public void run()
                        {
                            text.requestFocus();
                        }
                    });
                }
            }
        });
    }
}
