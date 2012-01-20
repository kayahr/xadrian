/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */
package de.ailis.xadrian.data;

import java.awt.Color;
import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import de.ailis.xadrian.support.I18N;

/**
 * A race
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class Race implements Serializable, Comparable<Race>
{
    /** Serial version UID */
    private static final long serialVersionUID = 1477337332848671379L;

    /** The race id */
    private final String id;

    /** The race color */
    private final Color color;

    /** The message id */
    private final String messageId;

    /** The game. */
    private final Game game;

    /**
     * Constructor
     *
     * @param game
     *            The game.
     * @param id
     *            The race id
     * @param color
     *            The race color
     */
    public Race(final Game game, final String id, final Color color)
    {
        this.game = game;
        this.id = id;
        this.color = color;
        this.messageId = "race." + id;
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
     * Return the color.
     *
     * @return The color
     */
    public Color getColor()
    {
        return this.color;
    }

    /**
     * Return the color with the specified alpha value.
     *
     * @param alpha
     *            The alpha value
     * @return The color
     */
    public Color getColor(final int alpha)
    {
        return new Color(this.color.getRed(), this.color.getGreen(), this.color
            .getBlue(), alpha);
    }

    /**
     * Returns the name.
     *
     * @return The name
     */
    public String getName()
    {
        return I18N.getString(this.game, this.messageId);
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
        final Race other = (Race) obj;
        return new EqualsBuilder().append(this.id, other.id).isEquals();
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final Race o)
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
