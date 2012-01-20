/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */
package de.ailis.xadrian.data;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Theme.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class Theme implements Serializable, Comparable<Theme>
{
    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The theme name. */
    private final String name;

    /** The theme class name. */
    private final String className;

    /**
     * Constructor.
     *
     * @param name
     *            The theme name.
     * @param className
     *            The theme class name.
     */
    public Theme(final String name, final String className)
    {
        this.name = name;
        this.className = className;
    }

    /**
     * Returns the theme name.
     *
     * @return The theme name
     */
    public String getName()
    {
        return this.name;
    }

    /**
     * Returns the theme class name.
     *
     * @return The theme class name.
     */
    public String getClassName()
    {
        return this.className;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return this.getName();
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Theme o)
    {
        return getName().compareTo(o.getName());
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.className).toHashCode();
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;
        final Theme other = (Theme) obj;
        return new EqualsBuilder().append(this.className, other.className)
            .isEquals();
    }
}
