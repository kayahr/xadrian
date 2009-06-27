/*
 * $Id: SectorSelector.java 839 2009-03-22 14:21:30Z k $
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.util.Arrays;
import java.util.TreeSet;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.ailis.xadrian.data.Asteroid;
import de.ailis.xadrian.data.Sector;
import de.ailis.xadrian.data.factories.SectorFactory;
import de.ailis.xadrian.utils.SwingUtils;


/**
 * Component which displays a list of asteroids of a sector which can be
 * checked.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 839 $
 */

public class AsteroidCheckList extends JScrollPane
{
    /** Serial version UID */
    private static final long serialVersionUID = -81244231816578394L;

    /** The panel containing the check boxes */
    private JPanel panel;

    /** The sector */
    private Sector sector;


    /**
     * Constructor
     */

    public AsteroidCheckList()
    {
        super();
        setMinimumSize(new Dimension(128, 64));
        setPreferredSize(getMinimumSize());
    }


    /**
     * Sets the sector.
     * 
     * @param sector
     *            The sector to set
     */

    public void setSector(final Sector sector)
    {
        if (sector != this.sector)
        {
            this.sector = sector;
            
            // Create the panel with the check boxes within
            this.panel = new JPanel();
            this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.Y_AXIS));
            if (sector != null)
            {
                for (final Asteroid asteroid: new TreeSet<Asteroid>(Arrays
                    .asList(sector.getAsteroids())))
                {
                    final JCheckBox checkbox = new AsteroidCheckBox(asteroid);
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
            System.out.println(panelSize);
            setPreferredSize(new Dimension(insets.left + insets.right + 2
                + panelSize.width
                + getVerticalScrollBar().getPreferredSize().width,
                panelSize.height + insets.top + insets.bottom + 2));
        }
    }


    /**
     * Tests the component.
     * 
     * @param args
     *            Command line arguments
     * @throws Exception
     */

    public static void main(final String[] args) throws Exception
    {
        SwingUtils.prepareGUI();

        final Sector sector = SectorFactory.getInstance().getSector(14, 7);
        final AsteroidCheckList component = new AsteroidCheckList();
        component.setSector(sector);
        SwingUtils.testComponent(component);
    }
}
