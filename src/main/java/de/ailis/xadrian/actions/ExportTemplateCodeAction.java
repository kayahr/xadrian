/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */
package de.ailis.xadrian.actions;

import java.awt.Component;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.ailis.xadrian.components.ComplexEditor;
import de.ailis.xadrian.frames.MainFrame;
import de.ailis.xadrian.listeners.MainStateListener;
import de.ailis.xadrian.support.FrameAction;

/**
 * Exports the complex template code to the clipboard.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class ExportTemplateCodeAction extends FrameAction<MainFrame> implements
    MainStateListener
{
    /** Serial version UID */
    private static final long serialVersionUID = 1;

    /**
     * Constructor
     *
     * @param frame
     *            The frame
     */
    public ExportTemplateCodeAction(final MainFrame frame)
    {
        super(frame, "exportTemplateCode");
        frame.addStateListener(this);
        setEnabled(false);
    }

    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent e)
    {
        final Component component = this.frame.getCurrentTab();
        if (component instanceof ComplexEditor)
        {
            final ComplexEditor editor = (ComplexEditor) component;
            final String templateCode = editor.getComplex().getTemplateCode();
            final Clipboard clipboard = editor.getToolkit().getSystemClipboard();
            clipboard.setContents(new StringSelection(templateCode), null);
        }
    }

    /**
     * @see MainStateListener#mainStateChanged(MainFrame)
     */
    @Override
    public void mainStateChanged(final MainFrame sender)
    {
        final ComplexEditor editor = (ComplexEditor) sender.getCurrentTab();
        setEnabled(editor != null && !editor.getComplex().isEmpty());
    }
}
