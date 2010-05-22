/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.components;

import javax.swing.JCheckBox;

import de.ailis.xadrian.data.Asteroid;
import de.ailis.xadrian.data.Container;
import de.ailis.xadrian.data.Ware;
import de.ailis.xadrian.support.I18N;
import de.ailis.xadrian.utils.SwingUtils;


/**
 * A check box to select an asteroid.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class AsteroidCheckBox extends JCheckBox
{
    /** Serial version UID */
    private static final long serialVersionUID = 4870661458814309824L;

    /** The asteroid */
    private final Asteroid asteroid;


    /**
     * Constructor
     *
     * @param asteroid
     *            The asteroid
     */

    public AsteroidCheckBox(final Asteroid asteroid)
    {
        this.asteroid = asteroid;
        setText(String.format("%s [%s, " + I18N.getString("yield") + "=%d]", asteroid.getId(),
            asteroid.getWare().getName(), asteroid.getYield()));
    }


    /**
     * Returns the asteroid.
     *
     * @return The asteroid
     */

    public Asteroid getAsteroid()
    {
        return this.asteroid;
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
        SwingUtils
            .testComponent(new AsteroidCheckBox(new Asteroid("AO-6998",
                new Ware("siliconWafers", 1, 2, 3, 10, Container.XL), 25, 0,
                0, 0)));
    }
}
