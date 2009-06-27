/*
 * $Id: SectorSelector.java 839 2009-03-22 14:21:30Z k $
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.components;

import javax.swing.JComboBox;

import de.ailis.xadrian.models.SectorComboBoxModel;
import de.ailis.xadrian.utils.SwingUtils;


/**
 * Component which displays a sector.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 839 $
 */

public class SectorComboBox extends JComboBox
{
    /** serialVersionUID */
    private static final long serialVersionUID = 3002228245039658859L;

    
    /**
     * Constructor
     */
    
    public SectorComboBox()
    {
        super(new SectorComboBoxModel());
        setMaximumSize(getPreferredSize());
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

        final SectorComboBox component = new SectorComboBox();
        SwingUtils.testComponent(component);
    }
}
