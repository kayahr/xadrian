/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.data;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import de.ailis.xadrian.support.I18N;


/**
 * A product
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public class Ware implements Serializable, Comparable<Ware>
{
    /** Serial version UID */
    private static final long serialVersionUID = 3725420312079108478L;

    /** The product id */
    private final String id;

    /** The minimum price */
    private final int minPrice;

    /** The average price */
    private final int avgPrice;

    /** The maximum price */
    private final int maxPrice;

    /** The ware volume */
    private final int volume;

    /** The container class */
    private final Container container;
    
    /** The message id */
    private final String messageId;


    /**
     * Constructor
     * 
     * @param id
     *            The race id
     * @param minPrice
     *            The minimum price
     * @param avgPrice
     *            The average price
     * @param maxPrice
     *            The maximum price
     * @param volume
     *            The ware volume
     * @param container
     *            The container class
     */

    public Ware(final String id, final int minPrice, final int avgPrice,
        final int maxPrice, final int volume, final Container container)
    {
        this.id = id;
        this.minPrice = minPrice;
        this.avgPrice = avgPrice;
        this.maxPrice = maxPrice;
        this.volume = volume;
        this.container = container;
        this.messageId = "ware." + id;
    }


    /**
     * Return the id.
     * 
     * @return The id
     */

    public String getId()
    {
        return this.id;
    }


    /**
     * Returns the name.
     * 
     * @return The name
     */

    public String getName()
    {
        return I18N.getString(this.messageId);
    }


    /**
     * Returns the minimum price.
     * 
     * @return The minimum price
     */

    public int getMinPrice()
    {
        return this.minPrice;
    }


    /**
     * Returns the average price.
     * 
     * @return The average price
     */

    public int getAvgPrice()
    {
        return this.avgPrice;
    }


    /**
     * Returns the maximum price.
     * 
     * @return The maximum price
     */

    public int getMaxPrice()
    {
        return this.maxPrice;
    }


    /**
     * Returns the ware volume.
     * 
     * @return The ware volume
     */

    public int getVolume()
    {
        return this.volume;
    }

    
    /**
     * Returns the container class.
     * 
     * @return The container class
     */

    public Container getContainer()
    {
        return this.container;
    }

    
    /**
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.id).toHashCode();
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
        final Ware other = (Ware) obj;
        return new EqualsBuilder().append(this.id, other.id).isEquals();
    }

    
    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    
    @Override
    public int compareTo(final Ware o)
    {
        return getName().compareTo(o.getName());
    }
    
    
    /**
     * @see java.lang.Object#toString()
     */
    
    @Override
    public String toString()
    {
        return getName();
    }
}
