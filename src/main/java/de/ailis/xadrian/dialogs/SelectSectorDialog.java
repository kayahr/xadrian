/*
 * $Id: AddFactoryDialog.java 800 2009-03-09 22:42:29Z k $
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import de.ailis.xadrian.components.SectorSelector;
import de.ailis.xadrian.components.SectorSelector.Mode;
import de.ailis.xadrian.data.Sector;
import de.ailis.xadrian.listeners.SectorSelectorStateListener;
import de.ailis.xadrian.resources.Images;
import de.ailis.xadrian.support.I18N;
import de.ailis.xadrian.support.ModalDialog;


/**
 * Dialog for selecting a sector.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 800 $
 */

public class SelectSectorDialog extends ModalDialog implements
    SectorSelectorStateListener
{
    /** Serial version UID */
    private static final long serialVersionUID = -3589101588161573682L;

    /** The singleton instance of this dialog */
    private final static SelectSectorDialog instance = new SelectSectorDialog();

    /** The sector selector */
    private SectorSelector selector;


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
        setTitle(I18N.getTitle("selectSector"));
        setIconImages(Images.LOGOS);

        // Create the selector panel
        final JPanel selectorPanel = new JPanel();
        selectorPanel.setBorder(BorderFactory
            .createBevelBorder(BevelBorder.LOWERED));
        final SectorSelector selector = new SectorSelector(512, 512);
        this.selector = selector;
        selector.addSectorSelectorStateListener(this);
        selector.addMouseListener(new MouseAdapter()
        {
            /** @see MouseAdapter#mouseClicked(MouseEvent) */
            @Override
            public void mouseClicked(final MouseEvent e)
            {
                if (e.getClickCount() == 2)
                    getRootPane().getDefaultButton().doClick(0);
            }
        });
        selectorPanel.add(selector);

        // Create the controls panel
        final JPanel controlsPanel = new JPanel();
        controlsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.X_AXIS));
        final JTextField quickSearch = new JTextField();
        quickSearch.setColumns(15);
        quickSearch.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyReleased(final KeyEvent e)
            {
                selector.setFilter(quickSearch.getText());
            }
        });
        final JLabel quickSearchLabel = new JLabel(I18N
            .getString("selectSector.filter"));
        quickSearchLabel.setDisplayedMnemonic(I18N
            .getMnemonic("selectSector.filter"));
        quickSearchLabel.setLabelFor(quickSearch);
        final JComboBox modeComboBox = new JComboBox(SectorSelector.Mode
            .values());
        modeComboBox.setSelectedItem(selector.getMode());
        modeComboBox.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(final ActionEvent e)
            {
                selector.setMode((Mode) modeComboBox.getSelectedItem());
            }
        });
        final JLabel modeLabel = new JLabel(I18N
            .getString("selectSector.viewMode"));
        modeLabel.setDisplayedMnemonic(I18N
            .getMnemonic("selectSector.viewMode"));
        modeLabel.setLabelFor(modeComboBox);
        controlsPanel.add(quickSearchLabel);
        controlsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        controlsPanel.add(quickSearch);
        controlsPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        controlsPanel.add(modeLabel);
        controlsPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        controlsPanel.add(modeComboBox);

        // Create another container for just adding some border
        final JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(controlsPanel);
        contentPanel.add(selectorPanel);

        // Put this last panel into the window
        add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * @see de.ailis.xadrian.support.ModalDialog#open()
     */

    @Override
    public Result open()
    {
        setResultEnabled(Result.OK, this.getSelected() != null);
        return super.open();
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
     * Returns the selected sector.
     * 
     * @return The selected sector
     */

    public Sector getSelected()
    {
        return this.selector.getSelected();
    }


    /**
     * Sets the selected sector. null deselectes the currently selected sector.
     * 
     * @param sector
     *            The sector to select
     */

    public void setSelected(final Sector sector)
    {
        this.selector.setSelected(sector);
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


    /**
     * @see SectorSelectorStateListener#sectorSelectorChanged(SectorSelector)
     */

    @Override
    public void sectorSelectorChanged(final SectorSelector sender)
    {
        setResultEnabled(Result.OK, this.getSelected() != null);
    }
}
