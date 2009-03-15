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
 * Storage capacity for a single ware.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Capacity implements Serializable, Comparable<Capacity>
{
    /** Serial version UID */
    private static final long serialVersionUID = -6949708282620186873L;

    /** The ware */
    private final Ware ware;

    /** The quantity */
    private final long quantity;
    

    /**
     * Constructor
     * 
     * @param ware
     *            The ware
     * @param quantity
     *            The quantity
     */

    public Capacity(final Ware ware, final long quantity)
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

    public long getQuantity()
    {
        return this.quantity;
    }
    
    
    /**
     * Returns the storage volume.
     * 
     * @return The storage volume
     */
    
    public long getVolume()
    {
        return this.ware.getVolume() * this.quantity;
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
        final Capacity other = (Capacity) obj;
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
    public int compareTo(final Capacity o)
    {
        return this.ware.compareTo(o.ware);
    }
}
