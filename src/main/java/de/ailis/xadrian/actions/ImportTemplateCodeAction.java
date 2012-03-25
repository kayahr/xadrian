/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */

package de.ailis.xadrian.actions;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import de.ailis.xadrian.exceptions.TemplateCodeException;
import de.ailis.xadrian.frames.MainFrame;
import de.ailis.xadrian.listeners.MainStateListener;
import de.ailis.xadrian.support.FrameAction;

/**
 * Imports from a complex template code in clipboard.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public class ImportTemplateCodeAction extends FrameAction<MainFrame> implements
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
    public ImportTemplateCodeAction(final MainFrame frame)
    {
        super(frame, "importTemplateCode");
        frame.addStateListener(this);
    }

    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent e)
    {
        Clipboard clipboard = this.frame.getToolkit().getSystemClipboard();
        Transferable transferable = clipboard.getContents(null);
        if (transferable.isDataFlavorSupported(DataFlavor.stringFlavor))
        {
            try
            {
                String code = transferable.getTransferData(DataFlavor.stringFlavor)
                    .toString();
                this.frame.importFromTemplateCode(code);
            }
            catch (UnsupportedFlavorException ex)
            {
                throw new TemplateCodeException(ex.toString(), ex);
            }
            catch (IOException ex)
            {
                throw new TemplateCodeException(ex.toString(), ex);
            }
        }
    }

    /**
     * @see MainStateListener#mainStateChanged(MainFrame)
     */
    @Override
    public void mainStateChanged(MainFrame sender)
    {
        // Empty
    }
}
