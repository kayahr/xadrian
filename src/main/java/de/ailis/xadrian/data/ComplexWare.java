/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.data;

import java.io.Serializable;


/**
 * A complex ware with the info how much units are produced and how much are
 * needed.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class ComplexWare implements Serializable
{
    /** Serial version UID */    
    private static final long serialVersionUID = -651656278517737848L;

    /** The ware */
    private final Ware ware;

    /** The number of produces units */
    private final double produced;

    /** The number of needed units */
    private final double needed;


    /**
     * Constructor.
     * 
     * @param ware
     *            The ware
     * @param produced
     *            The number of produced units
     * @param needed
     *            The number of needed units
     */

    public ComplexWare(final Ware ware, final double produced,
        final double needed)
    {
        this.ware = ware;
        this.produced = produced;
        this.needed = needed;
    }
    
    
    /**
     * Returns the ware.
     * 
     * @return The ware
     */
    
    public Ware getWare()
    {
        return this.ware;
    }
    
    
    /**
     * Returns the number of produced units.
     * 
     * @return The number of produced units
     */
    
    public double getProduced()
    {
        return this.produced;
    }

    
    /**
     * Returns the number of needed units.
     * 
     * @return The number of needed units
     */
    
    public double getNeeded()
    {
        return this.needed;
    }
    
    
    /**
     * Returns the number of missing units. If more units are produced than
     * needed then this method returns 0.
     * 
     * @return The number of missing units
     */
    
    public double getMissing()
    {
        return Math.max(0, this.needed - this.produced);
    }
    
    
    /**
     * Returns the surplus. Can return negative values if more units are
     * needed than produced.
     * 
     * @return The surplus
     */
    
    public double getSurplus()
    {
        return this.produced - this.needed;
    }
    
    
    /**
     * Returns the buy price.
     * 
     * @return the buy price
     */
    
    public int getBuyPrice()
    {
        return this.ware.getMedPrice();
    }
    
    
    /**
     * Returns the sell price.
     * 
     * @return The sell price
     */
    
    public int getSellPrice()
    {
        return this.ware.getMedPrice();
    }
    
    
    /**
     * Returns the profit per hour.
     * 
     * @return The profit per hour
     */
    
    public double getProfit()
    {
        final double surplus = this.getSurplus();
        if (surplus < 0)
        {
            return surplus * getBuyPrice();
        }
        else
        {
            return surplus * getSellPrice();
        }
    }
}
