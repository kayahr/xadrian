/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.xadrian.components;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * Button which simply displays an icon.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public class IconButton extends JLabel
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param action
     *            The action to execute when icon is pressed.
     * @param icon
     *            The icon to display.
     */
    public IconButton(final Action action, Icon icon)
    {
        super(icon);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setToolTipText((String) action.getValue(Action.SHORT_DESCRIPTION));
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                action.actionPerformed(null);
            }
        });
    }
}
