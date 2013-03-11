/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.components;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;

import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import de.ailis.xadrian.actions.CopyAction;
import de.ailis.xadrian.actions.CutAction;
import de.ailis.xadrian.actions.PasteAction;
import de.ailis.xadrian.actions.SelectAllAction;
import de.ailis.xadrian.interfaces.ClipboardProvider;
import de.ailis.xadrian.listeners.ClipboardStateListener;
import de.ailis.xadrian.utils.SwingUtils;

/**
 * Standard popup menu for text panes.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public class JTextPanePopupMenu extends JPopupMenu implements
    ClipboardProvider, CaretListener
{
    /** Serial version UID */
    private static final long serialVersionUID = 1;

    /** The text pane */
    private final JTextPane textPane;

    /**
     * Constructor
     * 
     * @param textPane
     *            The text pane.
     */
    public JTextPanePopupMenu(JTextPane textPane)
    {
        super();
        this.textPane = textPane;
        textPane.addCaretListener(this);
        if (textPane.isEditable())
        {
            add(new CutAction(this));
            add(new CopyAction(this));
            add(new PasteAction(this));
            addSeparator();
            add(new SelectAllAction(this));
        }
        else
        {
            add(new CopyAction(this));
            add(new SelectAllAction(this));
        }
        SwingUtils.setPopupMenu(textPane, this);
    }

    /**
     * @see de.ailis.xadrian.interfaces.ClipboardProvider#selectAll()
     */
    @Override
    public void selectAll()
    {
        this.textPane.requestFocus();
        this.textPane.selectAll();
        fireClipboardState();
    }

    /**
     * @see de.ailis.xadrian.interfaces.ClipboardProvider#copy()
     */
    @Override
    public void copy()
    {
        this.textPane.requestFocus();
        this.textPane.copy();
    }

    /**
     * @see de.ailis.xadrian.interfaces.ClipboardProvider#cut()
     */
    @Override
    public void cut()
    {
        this.textPane.requestFocus();
        this.textPane.cut();
    }

    /**
     * @see de.ailis.xadrian.interfaces.ClipboardProvider#paste()
     */
    @Override
    public void paste()
    {
        this.textPane.requestFocus();
        this.textPane.paste();
    }

    /**
     * @see de.ailis.xadrian.interfaces.ClipboardProvider#addClipboardStateListener(de.ailis.xadrian.listeners.ClipboardStateListener)
     */
    @Override
    public void addClipboardStateListener(ClipboardStateListener listener)
    {
        this.listenerList.add(ClipboardStateListener.class, listener);
    }

    /**
     * @see de.ailis.xadrian.interfaces.ClipboardProvider#removeClipboardStateListener(de.ailis.xadrian.listeners.ClipboardStateListener)
     */
    @Override
    public void removeClipboardStateListener(ClipboardStateListener listener)
    {
        this.listenerList.remove(ClipboardStateListener.class, listener);
    }

    /**
     * @see javax.swing.event.CaretListener#caretUpdate(javax.swing.event.CaretEvent)
     */
    @Override
    public void caretUpdate(final CaretEvent e)
    {
        fireClipboardState();
    }

    /**
     * Fire the clipboard state changed event.
     */
    private void fireClipboardState()
    {
        final Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2)
            if (listeners[i] == ClipboardStateListener.class)
                ((ClipboardStateListener) listeners[i + 1])
                    .clipboardStateChanged(this);
    }

    /**
     * @see de.ailis.xadrian.interfaces.ClipboardProvider#canCut()
     */
    @Override
    public boolean canCut()
    {
        return this.textPane.getSelectedText() != null && 
            this.textPane.isEditable();
    }

    /**
     * @see de.ailis.xadrian.interfaces.ClipboardProvider#canPaste()
     */
    @Override
    public boolean canPaste()
    {
        return this.textPane.isEditable()
            && Toolkit.getDefaultToolkit().getSystemClipboard()
                .isDataFlavorAvailable(DataFlavor.stringFlavor);
    }

    /**
     * @see de.ailis.xadrian.interfaces.ClipboardProvider#canCopy()
     */
    @Override
    public boolean canCopy()
    {
        return this.textPane.getSelectedText() != null;
    }

    /**
     * @see de.ailis.xadrian.interfaces.ClipboardProvider#canSelectAll()
     */
    @Override
    public boolean canSelectAll()
    {
        return true;
    }
}
