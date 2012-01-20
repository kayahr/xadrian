/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */
package de.ailis.xadrian.data;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import de.ailis.xadrian.data.factories.FactoryFactory;
import de.ailis.xadrian.data.factories.RaceFactory;
import de.ailis.xadrian.data.factories.SectorFactory;
import de.ailis.xadrian.data.factories.StationFactory;
import de.ailis.xadrian.data.factories.SunFactory;
import de.ailis.xadrian.data.factories.WareFactory;
import de.ailis.xadrian.dialogs.AddFactoryDialog;
import de.ailis.xadrian.dialogs.ChangePricesDialog;
import de.ailis.xadrian.dialogs.ChangeSunsDialog;
import de.ailis.xadrian.dialogs.SelectSectorDialog;
import de.ailis.xadrian.support.I18N;

/**
 * A game.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class Game implements Serializable, Comparable<Game>
{
    /** Serial version UID */
    private static final long serialVersionUID = 1477337332848671379L;

    /** The race id */
    private final String id;

    /** The message id */
    private final String messageId;

    /** The sector factory. */
    private final SectorFactory sectorFactory;

    /** The factory factory. */
    private final FactoryFactory factoryFactory;

    /** The race factory. */
    private final RaceFactory raceFactory;

    /** The station factory. */
    private final StationFactory stationFactory;

    /** The sun factory. */
    private final SunFactory sunFactory;

    /** The ware factory. */
    private final WareFactory wareFactory;

    /** The add factory dialog. */
    private final AddFactoryDialog addFactoryDialog;

    /** The select sector dialog. */
    private final SelectSectorDialog selectSectorDialog;

    /** The change suns dialog. */
    private final ChangeSunsDialog changeSunsDialog;

    /** The change prices dialog. */
    private final ChangePricesDialog changePricesDialog;

    /**
     * Constructor.
     *
     * @param id
     *            The race id
     */
    public Game(final String id)
    {
        this.id = id;
        this.messageId = "game." + id;
        this.sunFactory = new SunFactory(this);
        this.raceFactory = new RaceFactory(this);
        this.wareFactory = new WareFactory(this);
        this.sectorFactory = new SectorFactory(this);
        this.stationFactory = new StationFactory(this);
        this.factoryFactory = new FactoryFactory(this);

        this.addFactoryDialog = new AddFactoryDialog(this);
        this.selectSectorDialog = new SelectSectorDialog(this);
        this.changeSunsDialog = new ChangeSunsDialog(this);
        this.changePricesDialog = new ChangePricesDialog(this);
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
        final Game other = (Game) obj;
        return new EqualsBuilder().append(this.id, other.id).isEquals();
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final Game o)
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

    /**
     * Returns the sector factory for this game.
     *
     * @return The sector factory for this game.
     */
    public SectorFactory getSectorFactory()
    {
        return this.sectorFactory;
    }

    /**
     * Returns the factory factory for this game.
     *
     * @return The factory factory for this game.
     */
    public FactoryFactory getFactoryFactory()
    {
        return this.factoryFactory;
    }

    /**
     * Returns the race factory for this game.
     *
     * @return The race factory for this game.
     */
    public RaceFactory getRaceFactory()
    {
        return this.raceFactory;
    }

    /**
     * Returns the station factory for this game.
     *
     * @return The station factory for this game.
     */
    public StationFactory getStationFactory()
    {
        return this.stationFactory;
    }

    /**
     * Returns the sun factory for this game.
     *
     * @return The sun factory for this game.
     */
    public SunFactory getSunFactory()
    {
        return this.sunFactory;
    }

    /**
     * Returns the ware factory for this game.
     *
     * @return The ware factory for this game.
     */
    public WareFactory getWareFactory()
    {
        return this.wareFactory;
    }

    /**
     * Returns the add factory dialog.
     *
     * @return The add factory dialog.
     */
    public AddFactoryDialog getAddFactoryDialog()
    {
        return this.addFactoryDialog;
    }

    /**
     * Returns the select sector dialog.
     *
     * @return The select sector dialog.
     */
    public SelectSectorDialog getSelectSectorDialog()
    {
        return this.selectSectorDialog;
    }

    /**
     * Returns the change suns dialog.
     *
     * @return The change suns dialog.
     */
    public ChangeSunsDialog getChangeSunsDialog()
    {
        return this.changeSunsDialog;
    }

    /**
     * Returns the change prices dialog.
     *
     * @return The change prices dialog.
     */
    public ChangePricesDialog getChangePricesDialog()
    {
        return this.changePricesDialog;
    }
    
    /**
     * Checks if the game is X3: Terrn Conflict.
     * 
     * @return True if game is X3TC, false if not.
     */
    public boolean isX3TC()
    {
        return this.id.equals("x3tc");
    }
}
