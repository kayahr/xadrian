/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.data;


/**
 * Factory sizes
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public enum FactorySize
{
    /** Small factory */
    S(0, 1),

    /** Medium factory */
    M(1, 2),

    /** Large factory */
    L(2, 5),

    /** Extra-Large factory */
    XL(3, 10);


    /** The multiplier */
    private int multiplier;

    /** The size index */
    private int index;


    /**
     * Constructor
     * 
     * @param index
     *            The index
     * @param multiplier
     *            The multiplier
     */

    private FactorySize(final int index, final int multiplier)
    {
        this.index = index;
        this.multiplier = multiplier;
    }


    /**
     * Returns the multiplier.
     * 
     * @return The multiplier
     */

    public int getMultiplier()
    {
        return this.multiplier;
    }

    
    /**
     * Returns the index.
     * 
     * @return The index
     */
    
    public int getIndex()
    {
        return this.index;
    }
}
