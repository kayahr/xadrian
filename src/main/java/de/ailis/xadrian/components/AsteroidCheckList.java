/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Arrays;
import java.util.TreeSet;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.ailis.xadrian.data.Asteroid;
import de.ailis.xadrian.data.Sector;
import de.ailis.xadrian.data.factories.SectorFactory;
import de.ailis.xadrian.data.factories.WareFactory;
import de.ailis.xadrian.listeners.AsteroidSelectionModelListener;
import de.ailis.xadrian.models.AsteroidSelectionModel;
import de.ailis.xadrian.utils.SwingUtils;


/**
 * Component which displays a list of asteroids of a sector which can be
 * checked.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class AsteroidCheckList extends JScrollPane
{
    /** Serial version UID */
    private static final long serialVersionUID = -81244231816578394L;

    /** The panel containing the check boxes */
    private JPanel panel;

    /** The asteroid selection model */
    final AsteroidSelectionModel model;


    /**
     * Constructor.
     *
     * @param model
     *            The asteroid selection model
     */

    public AsteroidCheckList(final AsteroidSelectionModel model)
    {
        super();
        this.model = model;
        setupListeners();
        setMinimumSize(new Dimension(128, 64));
        setPreferredSize(getMinimumSize());
        reset();
    }


    /**
     * Setup event listeners.
     */

    private void setupListeners()
    {
        this.model.addModelListener(new AsteroidSelectionModelListener()
        {
            @Override
            public void focusChanged(final AsteroidSelectionModel model)
            {
                syncFocus();
            }


            /**
             * Reset the check list when sector was changed.
             */

            @Override
            public void sectorChanged(final AsteroidSelectionModel model)
            {
                reset();
            }


            /**
             * Sync the selections
             */

            @Override
            public void selectionChanged(final AsteroidSelectionModel model)
            {
                syncSelections();
            }
        });
    }


    /**
     * Sync the statuses of the checkboxes with the model.
     */

    void syncSelections()
    {
        for (final Component component: this.panel.getComponents())
        {
            if (!(component instanceof AsteroidCheckBox)) continue;
            final AsteroidCheckBox checkBox = (AsteroidCheckBox) component;
            checkBox
                .setSelected(this.model.isSelected(checkBox.getAsteroid()));
        }
    }


    /**
     * Sync the focus status of the checkboxes with the model.
     */

    void syncFocus()
    {
        for (final Component component: this.panel.getComponents())
        {
            if (!(component instanceof AsteroidCheckBox)) continue;
            final AsteroidCheckBox checkBox = (AsteroidCheckBox) component;
            if (checkBox.getAsteroid().equals(this.model.getFocused()))
            {
                checkBox.requestFocus();
                this.panel.scrollRectToVisible(checkBox.getBounds());
            }
            else if (checkBox.hasFocus() && this.model.getFocused() == null)
            {
                requestFocus();
            }
        }
    }


    /**
     * Resets the check list. Must be called every time the sector changed
     */

    void reset()
    {
        final Sector sector = this.model.getSector();

        // Create the panel with the check boxes within
        this.panel = new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
        if (sector != null)
        {
            for (final Asteroid asteroid: new TreeSet<Asteroid>(Arrays
                .asList(sector.getAsteroids())))
            {
                final JCheckBox checkbox = new AsteroidCheckBox(asteroid);
                checkbox.setSelected(this.model.isSelected(asteroid));
                checkbox.addFocusListener(new FocusListener()
                {
                    public void focusGained(final FocusEvent e)
                    {
                        AsteroidCheckList.this.model
                            .focus(((AsteroidCheckBox) e.getSource())
                                .getAsteroid());
                    }

                    public void focusLost(final FocusEvent e)
                    {
                        AsteroidCheckList.this.model.focus(null);
                    }
                });
                checkbox.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(final ActionEvent e)
                    {
                        final AsteroidCheckBox checkbox =
                            (AsteroidCheckBox) e.getSource();
                        if (checkbox.isSelected())
                            AsteroidCheckList.this.model.select(checkbox
                                .getAsteroid());
                        else
                            AsteroidCheckList.this.model.deselect(checkbox
                                .getAsteroid());
                    }
                });
                this.panel.setAlignmentX(LEFT_ALIGNMENT);
                this.panel.add(checkbox);
            }
        }

        // Use the panel as the content for this scroll pane
        setViewportView(this.panel);
        this.panel.setBackground(Color.WHITE);

        // Calculate the preferred size
        final Insets insets = getInsets();
        final Dimension panelSize = this.panel.getPreferredSize();
        setPreferredSize(new Dimension(insets.left + insets.right + 2
            + panelSize.width
            + getVerticalScrollBar().getPreferredSize().width,
            panelSize.height + insets.top + insets.bottom + 2));
    }


    /**
     * Tests the component.
     *
     * @param args
     *            Command line arguments
     * @throws Exception
     *            When something goes wrong
     */

    public static void main(final String[] args) throws Exception
    {
        SwingUtils.prepareGUI();

        final Sector sector = SectorFactory.getInstance().getSector(14, 7);
        final AsteroidSelectionModel model = new AsteroidSelectionModel();
        model.setSector(sector);
        model.setWare(WareFactory.getInstance().getWare("siliconWafers"));
        final AsteroidCheckList component = new AsteroidCheckList(model);
        SwingUtils.testComponent(component);
    }
}
