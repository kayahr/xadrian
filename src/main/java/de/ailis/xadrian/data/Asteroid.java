/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.data;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * An asteroid
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Asteroid implements Serializable, Comparable<Asteroid>
{
    /** Serial version UID */
    private static final long serialVersionUID = -1614642614129191708L;

    /** The asteroid id */
    private final String id;

    /** The mining ware */
    private final Ware ware;

    /** The yield */
    private final int yield;

    /** The X position */
    private final int x;

    /** The Y position */
    private final int y;

    /** The Z position */
    private final int z;


    /**
     * Constructor
     * 
     * @param id
     *            The asteroid ID
     * @param ware
     *            The mining ware
     * @param yield
     *            The yield
     * @param x
     *            The X position
     * @param y
     *            The Y position
     * @param z
     *            The Z position
     */

    public Asteroid(final String id, final Ware ware, final int yield,
        final int x, final int y, final int z)
    {
        this.id = id;
        this.ware = ware;
        this.yield = yield;
        this.x = x;
        this.y = y;
        this.z = z;
    }


    /**
     * Returns the asteroid id.
     * 
     * @return The asteroid id
     */

    public String getId()
    {
        return this.id;
    }


    /**
     * Returns the mining ware.
     * 
     * @return The mining ware
     */

    public Ware getWare()
    {
        return this.ware;
    }


    /**
     * Returns the yield.
     * 
     * @return The yield
     */

    public int getYield()
    {
        return this.yield;
    }


    /**
     * Returns the X position.
     * 
     * @return The X position
     */

    public int getX()
    {
        return this.x;
    }


    /**
     * Returns the Y position.
     * 
     * @return The Y position
     */

    public int getY()
    {
        return this.y;
    }


    /**
     * Returns the Z position.
     * 
     * @return The Z position
     */

    public int getZ()
    {
        return this.z;
    }


    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */

    @Override
    public int compareTo(final Asteroid o)
    {
        int result = this.ware.compareTo(o.ware);
        if (result == 0)
            result = Integer.valueOf(this.yield).compareTo(o.yield);
        if (result == 0) result = this.id.compareTo(o.id);
        return result;
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
        final Asteroid other = (Asteroid) obj;
        return new EqualsBuilder().append(this.id, other.id).isEquals();
    }

    
    /**
     * @see java.lang.Object#toString()
     */
    
    @Override
    public String toString()
    {
        return getId();
    }
}
