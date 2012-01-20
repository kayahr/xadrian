/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */
package de.ailis.xadrian.data;

import java.awt.Color;
import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A sun.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class Sun implements Serializable, Comparable<Sun>
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The game this sun belongs to. */
    private final Game game;

    /** The Suns percent. */
    private final int percent;

    /** The Suns cycle. */
    private final int cycle;

    /**
     * Constructor
     *
     * @param game
     *            The game this sun belongs to.
     * @param percent
     *            The sun percent.
     * @param cycle
     *            The sun cycle.
     */
    public Sun(final Game game, final int percent, final int cycle)
    {
        this.game = game;
        this.percent = percent;
        this.cycle = cycle;
    }

    /**
     * Returns the percent.
     *
     * @return The percent.
     */
    public int getPercent()
    {
        return this.percent;
    }

    /**
     * Returns the name.
     *
     * @return The name
     */
    public String getName()
    {
        return this.percent + " %";
    }

    /**
     * Returns the cycle.
     *
     * @return The cycle.
     */
    public int getCycle()
    {
        return this.cycle;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.percent).toHashCode();
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
        final Sun other = (Sun) obj;
        return new EqualsBuilder().append(this.percent, other.percent)
            .isEquals();
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final Sun o)
    {
        return this.percent - o.percent;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return getName();
    }

    /**
     * Returns the color of the sun.
     *
     * @return The color of the sun
     */
    public Color getColor()
    {
        final int intensity = Math.min(255, 255 * this.percent
            / this.game.getSunFactory().getMaxPercent());
        return new Color(intensity, intensity, 0);
    }
}
