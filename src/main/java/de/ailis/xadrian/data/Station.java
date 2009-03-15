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
 * A station.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Station implements Serializable, Comparable<Station>
{
    /** Serial version UID */
    private static final long serialVersionUID = -4127874613297626660L;

    /** The station id */
    private final String id;

    /** The sector */
    private final Sector sector;
    

    /**
     * Constructor
     * 
     * @param id
     *            The station id
     * @param sector
     *            The sector id
     */

    public Station(final String id, final Sector sector)
    {
        this.id = id;
        this.sector = sector;
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
     * Returns the sector.
     * 
     * @return The sector
     */

    public Sector getSector()
    {
        return this.sector;
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
        final Station other = (Station) obj;
        return new EqualsBuilder().append(this.id, other.id).isEquals();
    }

    
    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    
    @Override
    public int compareTo(final Station o)
    {
        return this.id.compareTo(o.id);
    }
    
    
    /**
     * @see java.lang.Object#toString()
     */
    
    @Override
    public String toString()
    {
        return new ToStringBuilder(this).append("id", this.id).toString();
    }
}
