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
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.BevelBorder;

import de.ailis.xadrian.actions.ChangeSectorAction;
import de.ailis.xadrian.components.AsteroidCheckList;
import de.ailis.xadrian.components.SectorView;
import de.ailis.xadrian.data.Ware;
import de.ailis.xadrian.data.factories.WareFactory;
import de.ailis.xadrian.models.AsteroidSelectionModel;
import de.ailis.xadrian.support.ModalDialog;
import de.ailis.xadrian.utils.SwingUtils;


/**
 * Dialog for setting the asteroids to use for a specific mine type in the
 * current complex.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 839 $
 */

public class SetupAsteroidsDialog extends ModalDialog
{
    /** Serial version UID */
    private static final long serialVersionUID = -2929706815604197020L;

    /** The sector view */
    private SectorView sectorView;

    /** The asteroid check list */
    private AsteroidCheckList asteroidCheckList;

    /** The asteroid selection model */
    private AsteroidSelectionModel model;


    /**
     * Constructor
     * 
     * @param ware
     *            The ware for which asteroids are selected
     */

    private SetupAsteroidsDialog(final Ware ware)
    {
        super("setupAsteroids", Result.OK, Result.CANCEL);
        this.model.setWare(ware);
        setResizable(true);
    }


    /**
     * @see de.ailis.xadrian.support.ModalDialog#init()
     */

    @Override
    protected void init()
    {
        this.model = new AsteroidSelectionModel();
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
        this.sectorView = new SectorView(this.model);
        sectorViewPanel.add(this.sectorView, BorderLayout.CENTER);

        // Create the controls panel
        final JPanel controlsPanel = new JPanel();
        controlsPanel
            .setLayout(new BoxLayout(controlsPanel, BoxLayout.Y_AXIS));

        this.asteroidCheckList = new AsteroidCheckList(this.model);
        this.asteroidCheckList.setAlignmentX(LEFT_ALIGNMENT);
        controlsPanel.add(this.asteroidCheckList);
        SwingUtils.setPreferredHeight(controlsPanel, 512);


        // controlsPanel.add(Box.createGlue());
        final JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel
            .setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        final JSplitPane splitPane =
            new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

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
        dialogActions.add(new ChangeSectorAction(this.model, "sector"));
        return dialogActions;
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

        new SetupAsteroidsDialog(WareFactory.getInstance().getWare(
            "siliconWafers")).open();
        System.exit(0);
    }
}
