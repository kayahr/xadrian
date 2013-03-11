/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */
package de.ailis.xadrian.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.ailis.xadrian.interfaces.ClipboardProvider;
import de.ailis.xadrian.listeners.ClipboardStateListener;
import de.ailis.xadrian.resources.Icons;
import de.ailis.xadrian.support.BaseAction;

/**
 * Pastes the current content of the clipboard.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class PasteAction extends BaseAction implements ClipboardStateListener
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
    public PasteAction(final ClipboardProvider provider)
    {
        super("paste", Icons.PASTE);
        this.provider = provider;
        setEnabled(false);
        provider.addClipboardStateListener(this);
    }

    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent e)
    {
        this.provider.paste();
    }

    /**
     * @see ClipboardStateListener#clipboardStateChanged(ClipboardProvider)
     */
    @Override
    public void clipboardStateChanged(final ClipboardProvider provider)
    {
        setEnabled(provider.canPaste());
    }
}
