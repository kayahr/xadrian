/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.xadrian.support;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import de.ailis.xadrian.exceptions.DataException;
import de.ailis.xadrian.frames.MainFrame;

/**
 * Transfer handler for complex data.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public final class ComplexTransferHandler extends TransferHandler
{
    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The main frame. */
    private MainFrame mainFrame;

    /**
     * Constructor.
     * 
     * @param mainFrame
     *            The main frame.
     */
    public ComplexTransferHandler(MainFrame mainFrame)
    {
        this.mainFrame = mainFrame;
    }

    /**
     * @see javax.swing.TransferHandler#canImport(javax.swing.JComponent,
     *      java.awt.datatransfer.DataFlavor[])
     */
    @Override
    public boolean canImport(final JComponent component,
        final DataFlavor[] flavors)
    {
        for (int i = 0; i < flavors.length; i++)
        {
            DataFlavor flavor = flavors[i];
            if (flavor.equals(DataFlavor.javaFileListFlavor))
                return true;
        }
        return false;
    }

    /**
     * @see TransferHandler#importData(JComponent, Transferable)
     */
    @Override
    public boolean importData(JComponent comp, Transferable transferable)
    {
        DataFlavor[] flavors = transferable.getTransferDataFlavors();
        for (int i = 0; i < flavors.length; i++)
        {
            DataFlavor flavor = flavors[i];
            try
            {
                if (flavor.equals(DataFlavor.javaFileListFlavor))
                {
                    for (File file : (List<File>) transferable
                        .getTransferData(DataFlavor.javaFileListFlavor))
                    {
                        // Ignore non-files
                        if (!file.isFile()) continue;

                        // Ignore files which have no x3c extension
                        if (!file.getName().toLowerCase().endsWith(".x3c"))
                            continue;

                        this.mainFrame.open(file);
                    }
                    return true;
                }
            }
            catch (IOException e)
            {
                throw new DataException("Unable to read drag&drop data: " + e,
                    e);
            }
            catch (UnsupportedFlavorException e)
            {
                // Can't happen because we only process known flavours which
                // are also reported by the transferable itself.
                throw new RuntimeException(e.toString(), e);
            }
        }

        return false;
    }
}
