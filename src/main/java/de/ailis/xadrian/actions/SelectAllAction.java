/*
 * $Id$
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.ailis.xadrian.interfaces.ClipboardProvider;
import de.ailis.xadrian.listeners.ClipboardStateListener;
import de.ailis.xadrian.resources.Icons;
import de.ailis.xadrian.support.BaseAction;


/**
 * Selects all the text.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class SelectAllAction extends BaseAction implements ClipboardStateListener
{
    /** Serial version UID */
    private static final long serialVersionUID = -8587288689179840559L;
    
    /** The clipboard provider */
    private final ClipboardProvider provider;


    /**
     * Constructor
     * 
     * @param provider
     *            The connected component
     */

    public SelectAllAction(final ClipboardProvider provider)
    {
        super("selectAll", Icons.SELECT_ALL);
        this.provider = provider;
        this.setEnabled(false);
        provider.addClipboardStateListener(this);
    }


    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */

    public void actionPerformed(final ActionEvent e)
    {
        this.provider.selectAll();
    }


    /**
     * @see ClipboardStateListener#clipboardStateChanged(ClipboardProvider)
     */

    @Override
    public void clipboardStateChanged(final ClipboardProvider provider)
    {
        setEnabled(provider.canSelectAll());
    }
}
