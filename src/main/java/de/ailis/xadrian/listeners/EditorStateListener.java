/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */
package de.ailis.xadrian.listeners;

import java.util.EventListener;

import de.ailis.xadrian.components.ComplexEditor;

/**
 * Listener interface for editor changes.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public interface EditorStateListener extends EventListener
{
    /**
     * Called when the editor has changed
     *
     * @param sender
     *            The sender
     */
    public void editorStateChanged(ComplexEditor sender);
}
