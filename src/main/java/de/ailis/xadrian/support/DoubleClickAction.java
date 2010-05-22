/*
 * $Id$
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.support;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;


/**
 * This mouse adapter can be attached to a widget to tell it to execute the
 * specified action on double click. The action is only executed when it is
 * enabled.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class DoubleClickAction extends MouseAdapter
{
    /** The action to execute on double click */
    private final Action action;


    /**
     * Constructor
     * 
     * @param action
     *            The action to execute on double click
     */

    public DoubleClickAction(final Action action)
    {
        this.action = action;
    }


    /**
     * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
     */

    @Override
    public void mouseClicked(final MouseEvent e)
    {
        if (e.getClickCount() == 2 && this.action.isEnabled())
        {
            this.action.actionPerformed(null);
        }
    }
}
