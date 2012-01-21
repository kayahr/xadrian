/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.xadrian.components;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A combo box entry with a display name and a value.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public class ComboBoxEntry
{
    /** The display name. */
    private final String name;

    /** The value. */
    private final Object value;

    /**
     * Constructor.
     * 
     * @param name
     *            The display name.
     * @param value
     *            The value.
     */
    public ComboBoxEntry(final String name, final Object value)
    {
        this.name = name;
        this.value = value;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return this.name;
    }

    /**
     * Returns the display name.
     * 
     * @return The display name.
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Returns the value.
     * 
     * @return The value.
     */
    public Object getValue()
    {
        return this.value;
    }
    
    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.value).toHashCode();
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
        final ComboBoxEntry other = (ComboBoxEntry) obj;
        return new EqualsBuilder().append(this.value, other.value).isEquals();
    }    
}
