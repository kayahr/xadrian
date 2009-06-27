/*
 * $Id: SelectSectorDialog.java 839 2009-03-22 14:21:30Z k $
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.dialogs;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;

import de.ailis.xadrian.actions.ChangeSectorAction;
import de.ailis.xadrian.components.AsteroidCheckList;
import de.ailis.xadrian.components.SectorComboBox;
import de.ailis.xadrian.components.SectorView;
import de.ailis.xadrian.data.Sector;
import de.ailis.xadrian.interfaces.SectorProvider;
import de.ailis.xadrian.listeners.SectorListener;
import de.ailis.xadrian.listeners.StateListener;
import de.ailis.xadrian.support.ModalDialog;
import de.ailis.xadrian.utils.SwingUtils;


/**
 * Dialog for setting the asteroids to use for a specific mine type in the
 * current complex.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 839 $
 */

public class SetupAsteroidsDialog extends ModalDialog implements
    SectorProvider, StateListener
{
    /** Serial version UID */
    private static final long serialVersionUID = -2929706815604197020L;

    /** The sector view */
    private SectorView sectorView;

    /** The sector combo box */
    private SectorComboBox sectorComboBox;

    /** The asteroid check list */
    private AsteroidCheckList asteroidCheckList;

    /** The currently selected sector */
    private Sector sector;


    /**
     * Constructor
     */

    private SetupAsteroidsDialog()
    {
        super("setupAsteroids", Result.OK, Result.CANCEL);
        setResizable(true);
    }


    /**
     * Creates the UI
     */

    @Override
    protected void createUI()
    {
        // Create the sector view panel
        final JPanel sectorViewPanel = new JPanel();
        sectorViewPanel.setLayout(new BorderLayout());
        sectorViewPanel.setBorder(BorderFactory
            .createBevelBorder(BevelBorder.LOWERED));
        this.sectorView = new SectorView();
        this.sectorView.addSectorListener(new SectorListener()
        {
            public void sectorChanged(final Sector sector)
            {
                setSector(sector);
            }
        });
        sectorViewPanel.add(this.sectorView, BorderLayout.CENTER);

        // Create the controls panel
        final JPanel controlsPanel = new JPanel();
        controlsPanel
            .setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));
        this.sectorComboBox = new SectorComboBox();
        // modeComboBox.setSelectedItem(selector.getMode());
        final JLabel sectorLabel = new JLabel("Sector");
        sectorLabel.setAlignmentX(LEFT_ALIGNMENT);
        //controlsPanel.add(sectorLabel);
        //controlsPanel.add(this.sectorComboBox);
//        this.sectorComboBox.setAlignmentX(LEFT_ALIGNMENT);

        this.asteroidCheckList = new AsteroidCheckList();
        this.asteroidCheckList.setAlignmentX(LEFT_ALIGNMENT);
        controlsPanel.add(this.asteroidCheckList);
        SwingUtils.setPreferredHeight(controlsPanel, 512);


        // controlsPanel.add(Box.createGlue());
      final JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel
            .setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

        // Create another container for just adding some border
        // contentPanel.setLayout(new BoxLayout(contentPanel,
        // BoxLayout.Y_AXIS));
        splitPane.add(controlsPanel);
        splitPane.setDividerSize(10);
        splitPane.setContinuousLayout(true);
        
        splitPane.add(sectorViewPanel);
        contentPanel.add(splitPane, BorderLayout.CENTER);

        // Put this last panel into the window
        add(contentPanel, BorderLayout.CENTER);
    }


    /**
     * @see de.ailis.xadrian.support.ModalDialog#open()
     */

    @Override
    public Result open()
    {
        final Result result = super.open();
        return result;
    }


    /**
     * @see de.ailis.xadrian.support.ModalDialog#createDialogActions()
     */

    @Override
    protected List<Action> createDialogActions()
    {
        final List<Action> dialogActions = new ArrayList<Action>();
        dialogActions.add(new ChangeSectorAction(this, "sector"));
        return dialogActions;
    }


    /**
     * @see de.ailis.xadrian.interfaces.SectorProvider#canChangeSector()
     */

    @Override
    public boolean canChangeSector()
    {
        return true;
    }


    /**
     * @see de.ailis.xadrian.interfaces.SectorProvider#getSector()
     */

    @Override
    public Sector getSector()
    {
        return this.sector;
    }


    /**
     * @see de.ailis.xadrian.interfaces.SectorProvider#setSector(de.ailis.xadrian.data.Sector)
     */

    @Override
    public void setSector(final Sector sector)
    {
        if (sector != this.sector)
        {
            this.sector = sector;
            this.sectorView.setSector(sector);
            this.asteroidCheckList.setSector(sector);
        }
    }

    /**
     * @see de.ailis.xadrian.listeners.StateListener#stateChanged()
     */

    @Override
    public void stateChanged()
    {
        setSector(this.sectorView.getSector());
    }


    /**
     * Tests the component.
     * 
     * @param args
     *            Command line arguments
     * 
     * @throws Exception
     */

    public static void main(final String args[]) throws Exception
    {
        SwingUtils.prepareGUI();

        new SetupAsteroidsDialog().open();
        System.exit(0);
    }
}
