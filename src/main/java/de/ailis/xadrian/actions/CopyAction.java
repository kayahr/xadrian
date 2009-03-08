/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
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
 * Copies the selected text into the clipboard.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class CopyAction extends BaseAction implements ClipboardStateListener
{
    /** Serial version UID */
    private static final long serialVersionUID = -5394121906415648442L;

    /** The clipboard provider */
    private final ClipboardProvider provider;


    /**
     * Constructor
     * 
     * @param provider
     *            The connected component
     */

    public CopyAction(final ClipboardProvider provider)
    {
        super("copy", Icons.COPY);
        this.provider = provider;
        this.setEnabled(false);
        provider.addClipboardStateListener(this);
    }


    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */

    public void actionPerformed(final ActionEvent e)
    {
        this.provider.copy();
    }


    /**
     * @see ClipboardStateListener#clipboardStateChanged(ClipboardProvider)
     */

    @Override
    public void clipboardStateChanged(final ClipboardProvider provider)
    {
        setEnabled(provider.canCopy());
    }
}
