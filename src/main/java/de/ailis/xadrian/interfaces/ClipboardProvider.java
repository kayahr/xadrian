/*
 * $Id$
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.interfaces;

import de.ailis.xadrian.listeners.ClipboardStateListener;


/**
 * This interface is implemented by all components which can use the clipboard.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public interface ClipboardProvider
{
    /**
     * Selects all.
     */

    public void selectAll();


    /**
     * Copies the selected text to the clipard.
     */

    public void copy();


    /**
     * Cuts the selected text to the clipboard.
     */

    public void cut();


    /**
     * Pastes current clipboard content.
     */

    public void paste();


    /**
     * Adds a clipboard state listener.
     * 
     * @param listener
     *            The listener to add
     */

    public void addClipboardStateListener(ClipboardStateListener listener);


    /**
     * Removes a clipboard state listener.
     * 
     * @param listener
     *            The listener to remove
     */

    public void removeClipboardStateListener(ClipboardStateListener listener);


    /**
     * Returns true if component can cut.
     * 
     * @return True if component can cut. False if not.
     */

    public boolean canCut();


    /**
     * Returns true if component can paste.
     * 
     * @return True if component can paste. False if not.
     */


    public boolean canPaste();


    /**
     * Returns true if component can copy.
     * 
     * @return True if component can copy. False if not.
     */

    public boolean canCopy();
    
    
    /**
     * Returns true if component can select all.
     * 
     * @return True if component can select all. False if not.
     */
    
    public boolean canSelectAll();
}
