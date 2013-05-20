/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */
package de.ailis.xadrian.data;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import de.ailis.xadrian.support.Config;
import de.ailis.xadrian.support.I18N;

/**
 * The player sector.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class PlayerSector extends Sector
{
    /** Serial version UID */
    private static final long serialVersionUID = 1;

    /** The asteorids */
    private final Asteroid[][] asteroids;

    /**
     * Constructor
     *
     * @param game
     *            The game this sector belongs to.
     * @param id
     *            The sector id
     * @param x
     *            The X position in the universe
     * @param y
     *            The Y position in the universe
     * @param race
     *            The race this sector belongs to
     * @param planets
     *            The number of planets in the sector
     * @param suns
     *            The suns
     * @param core
     *            If this is a core sector or not
     * @param northId
     *            The id of the sector behind the north gate
     * @param eastId
     *            The id of the sector behind the east gate
     * @param southId
     *            The id of the sector behind the south gate
     * @param westId
     *            The id of the sector behind the west gate
     * @param asteroids
     *            Array or array with asteroids in this sector
     */
    public PlayerSector(final Game game, final String id, final int x,
        final int y, final Race race, final int planets, final Sun suns,
        final boolean core, final String northId, final String eastId,
        final String southId, final String westId, final Asteroid[][] asteroids)
    {
        super(game, id, x, y, race, planets, suns, core, false, northId, eastId,
            southId, westId, null);
        this.asteroids = asteroids.clone();
    }

    /**
     * Returns the name.
     *
     * @return The name
     */
    @Override
    public String getName()
    {
        final int playerSector = Config.getInstance().getX3TCPlayerSector();
        return I18N.getString(this.game, "sector." + this.id + "-" + playerSector);
    }

    /**
     * Returns the array with asteroids.
     *
     * @return The array with asteroids
     */
    @Override
    public Asteroid[] getAsteroids()
    {
        final int playerSector = Config.getInstance().getX3TCPlayerSector();
        return this.asteroids[playerSector];
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
        final PlayerSector other = (PlayerSector) obj;
        return new EqualsBuilder().append(this.id, other.id).isEquals();
    }    
}
