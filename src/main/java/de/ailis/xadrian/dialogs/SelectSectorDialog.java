/*
 * $Id: AddFactoryDialog.java 800 2009-03-09 22:42:29Z k $
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.dialogs;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import de.ailis.xadrian.components.SectorSelector;
import de.ailis.xadrian.resources.Images;
import de.ailis.xadrian.support.I18N;
import de.ailis.xadrian.support.ModalDialog;


/**
 * Dialog for selecting a sector.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 800 $
 */

public class SelectSectorDialog extends ModalDialog
{
    /** Serial version UID */
    private static final long serialVersionUID = -3589101588161573682L;

    /** The singleton instance of this dialog */
    private final static SelectSectorDialog instance = new SelectSectorDialog();


    /**
     * Constructor
     */

    private SelectSectorDialog()
    {
        super(Result.OK, Result.CANCEL);
    }


    /**
     * Creates the UI
     */

    @Override
    protected void createUI()
    {
        setTitle(I18N.getTitle("dialog.selectSector"));
        setIconImages(Images.LOGOS);

        // Create the content controls
        final JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        final SectorSelector selector = new SectorSelector(640, 512);        
        panel.add(selector);
        
        // Create another container for just adding some border
        final JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(panel);

        // Put this last panel into the window
        add(contentPanel, BorderLayout.CENTER);
    }


    /**
     * Returns the singleton instance
     * 
     * @return The singleton instance
     */

    public static SelectSectorDialog getInstance()
    {
        return instance;
    }
    
    /**
     * @param args
     * @throws Exception
     */
    public static void main(final String args[]) throws Exception
    {
        SelectSectorDialog.getInstance().open();
        System.exit(0);
    }
}
