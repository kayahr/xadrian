/*
 * $Id: Ware.java 752 2009-03-01 23:24:14Z k $
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.data;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import de.ailis.xadrian.support.I18N;


/**
 * A sector.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 752 $
 */

public class Sector implements Serializable, Comparable<Sector>
{
    /** Serial version UID */
    private static final long serialVersionUID = -8004624270181949305L;

    /** The sector id */
    private final String id;

    /** The message id */
    private final String messageId;


    /**
     * Constructor
     * 
     * @param id
     *            The sector id
     */

    public Sector(final String id)
    {
        this.id = id;
        this.messageId = "sector." + id;
    }


    /**
     * Return the sector id.
     * 
     * @return The sector id
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
        final Sector other = (Sector) obj;
        return new EqualsBuilder().append(this.id, other.id).isEquals();
    }

    
    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    
    @Override
    public int compareTo(final Sector o)
    {
        return getName().compareTo(o.getName());
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
