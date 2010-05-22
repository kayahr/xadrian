/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.data;

import java.awt.Color;


/**
 * Factory sizes
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public enum Suns
{
    /** 0% */
    P0("0 %", 0, 305),

    /** 100% */
    P100("100 %", 100, 239),

    /** 150% */
    P150("150 %", 150, 215),

    /** 300% */
    P300("300 %", 300, 166),

    /** 400% */
    P400("400 %", 400, 145);

    /** The suns caption text */
    private String text;

    /** The power in percent */
    private int percent;

    /** The cycle */
    private int cycle;


    /**
     * Constructor
     * 
     * @param text
     *            The caption text
     * @param percent
     *            The power in percent
     * @param cycle
     *            The cycle
     */

    private Suns(final String text, final int percent, final int cycle)
    {
        this.text = text;
        this.percent = percent;
        this.cycle = cycle;
    }


    /**
     * Returns the index.
     * 
     * @return The index
     */

    @Override
    public String toString()
    {
        return this.text;
    }


    /**
     * Returns the suns with the specified percent value. If not found then the
     * default sun power of 150% is returned.
     * 
     * @param percent
     *            The percent value
     * @return The suns
     */

    public static Suns valueOf(final int percent)
    {
        for (final Suns suns : Suns.values())
        {
            if (suns.getPercent() == percent) return suns;
        }
        
        // No match, return default sun
        return P150;
    }


    /**
     * Returns the sun power in percent.
     * 
     * @return The sun power in percent
     */

    public int getPercent()
    {
        return this.percent;
    }


    /**
     * Returns the cycle.
     * 
     * @return The cycle
     */

    public int getCycle()
    {
        return this.cycle;
    }
    
    
    /**
     * Returns the color of the suns.
     * 
     * @return The color of the suns
     */
    
    public Color getColor()
    {
        final int intensity = Math.min(255, 255 * this.percent / 400);
        return new Color(intensity, intensity, 0);
    }
}
