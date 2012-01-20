/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */
package de.ailis.xadrian.data;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * A complex ware with the info how much units are produced and how much are
 * needed.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class ComplexWare implements Serializable, Comparable<ComplexWare>
{
    /** Serial version UID */
    private static final long serialVersionUID = -651656278517737848L;

    /** The ware */
    private final Ware ware;

    /** The number of produces units */
    private final double produced;

    /** The number of needed units */
    private final double needed;

    /** The ware price */
    private final int price;

    /**
     * Constructor.
     *
     * @param ware
     *            The ware
     * @param produced
     *            The number of produced units
     * @param needed
     *            The number of needed units
     * @param price
     *            The buy/sell price of the ware
     */
    public ComplexWare(final Ware ware, final double produced,
        final double needed, final int price)
    {
        this.ware = ware;
        this.produced = produced;
        this.needed = needed;
        this.price = price;
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
     * Returns the surplus. Can return negative values if more units are needed
     * than produced.
     *
     * @return The surplus
     */
    public double getSurplus()
    {
        return this.produced - this.needed;
    }

    /**
     * Returns the buy/sell price.
     *
     * @return the buy/sell price
     */
    public int getPrice()
    {
        return this.price;
    }

    /**
     * Returns the profit per hour.
     *
     * @return The profit per hour
     */
    public double getProfit()
    {
        final double surplus = this.getSurplus();
        return surplus * this.price;
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final ComplexWare o)
    {
        return this.ware.compareTo(o.ware);
    }
    
    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;
        final ComplexWare other = (ComplexWare) obj;
        return new EqualsBuilder().append(this.ware, other.ware)
            .append(this.needed, other.needed)
            .append(this.produced, other.produced)
            .append(this.price, other.price).isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.ware).append(this.needed)
            .append(this.produced).append(this.price)
            .toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("ware", this.ware).append(
            "needed", this.needed).append("produced", this.produced).toString();
    }
}
