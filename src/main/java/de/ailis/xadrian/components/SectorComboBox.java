/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de> See LICENSE.txt file for
 * licensing information.
 */

package de.ailis.xadrian.components;

import javax.swing.JComboBox;

import de.ailis.xadrian.data.Game;
import de.ailis.xadrian.data.factories.GameFactory;
import de.ailis.xadrian.models.SectorComboBoxModel;
import de.ailis.xadrian.utils.SwingUtils;

/**
 * Component which displays a sector.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public class SectorComboBox extends JComboBox
{
    /** serialVersionUID */
    private static final long serialVersionUID = 3002228245039658859L;

    /**
     * Constructor.
     * 
     * @param game
     *            The game. Must not be null.
     */

    public SectorComboBox(final Game game)
    {
        super(new SectorComboBoxModel(game));
        setMaximumSize(getPreferredSize());
    }

    /**
     * Tests the component.
     * 
     * @param args
     *            Command line arguments
     * @throws Exception
     *             When something goes wrong
     */

    public static void main(final String[] args) throws Exception
    {
        SwingUtils.prepareGUI();

        Game game = GameFactory.getInstance().getGame("x3tc");
        final SectorComboBox component = new SectorComboBox(game);
        SwingUtils.testComponent(component);
    }
}
