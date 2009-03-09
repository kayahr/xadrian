/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.data;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * A product
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Product implements Serializable, Comparable<Product>
{
    /** Serial version UID */
    private static final long serialVersionUID = 4086385499471771372L;

    /** The ware */
    private final Ware ware;

    /** The quantity */
    private final double quantity;
    

    /**
     * Constructor
     * 
     * @param ware
     *            The ware
     * @param quantity
     *            The quantity
     */

    public Product(final Ware ware, final double quantity)
    {
        this.ware = ware;
        this.quantity = quantity;
    }


    /**
     * Return the ware.
     * 
     * @return The ware
     */

    public Ware getWare()
    {
        return this.ware;
    }


    /**
     * Returns the quantity.
     * 
     * @return The quantity
     */

    public double getQuantity()
    {
        return this.quantity;
    }


    /**
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.ware).append(this.quantity)
            .toHashCode();
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
        final Product other = (Product) obj;
        return new EqualsBuilder().append(this.ware, other.ware).append(
            this.quantity, other.quantity).isEquals();
    }


    /**
     * @see java.lang.Object#toString()
     */

    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("ware", this.ware).append(
            "quantity", this.quantity).toString();
    }


    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    
    @Override
    public int compareTo(final Product o)
    {
        return this.ware.compareTo(o.ware);
    }
}
