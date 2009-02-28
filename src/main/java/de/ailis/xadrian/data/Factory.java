/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import de.ailis.xadrian.exceptions.DataException;
import de.ailis.xadrian.support.I18N;


/**
 * A factory
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Factory implements Serializable, Comparable<Factory>
{
    /** Serial version UID */
    private static final long serialVersionUID = 4851121299100273466L;

    /**
     * Factory type.
     */

    public enum Type
    {
        /** A standard factory */
        STANDARD,

        /** A mine */
        MINE,

        /** A solar power plant */
        SOLAR
    }

    /** The factory id */
    private final String id;

    /** The factory type */
    private final Type type;

    /** The manufacturer race */
    private final Race race;

    /** The production cycle time */
    private final int cycle;

    /** The product this factory produces in one cycle */
    private final Product product;

    /** The price of this factory */
    private final int price;

    /** The volume of this factory */
    private final int volume;

    /** The resources this factory needs in one cycle to produce its product */
    private final Product[] resources;

    /** The message id */
    private final String messageId;
    
    /** The factory size */
    private final FactorySize size;


    /**
     * Constructor
     * 
     * @param id
     *            The factory id
     *            @param size The factory size
     * @param type
     *            The factory type
     * @param race
     *            The race
     * @param cycle
     *            The production cycle
     * @param product
     *            The produces product per cycle
     * @param price
     *            The factory price
     * @param volume
     *            The factory volume
     * @param resources
     *            The needed resources per cycle
     */

    public Factory(final String id, final FactorySize size, final Type type, final Race race,
        final int cycle, final Product product, final int price,
        final int volume, final Product[] resources)
    {
        this.id = id;
        this.size = size;
        this.type = type;
        this.race = race;
        this.cycle = cycle;
        this.product = product;
        this.price = price;
        this.volume = volume;
        this.resources = resources;
        this.messageId = "factory." + id.substring(0, id.lastIndexOf("-"));
    }


    /**
     * Return the factory id.
     * 
     * @return The factory id
     */

    public String getId()
    {
        return this.id;
    }


    /**
     * Return the factory size.
     * 
     * @return The factory size
     */

    public FactorySize getSize()
    {
        return this.size;
    }


    /**
     * Return the factory type.
     * 
     * @return The factory type
     */

    public Type getType()
    {
        return this.type;
    }


    /**
     * Returns the factory name.
     * 
     * @return The factory name
     */

    public String getName()
    {
        return I18N.getString(this.messageId);
    }


    /**
     * Returns the race which manufactures this factory.
     * 
     * @return The race
     */

    public Race getRace()
    {
        return this.race;
    }


    /**
     * Returns the production cycle.
     * 
     * @return The production cycle
     */

    public int getCycle()
    {
        return this.cycle;
    }
    
    
    /**
     * Returns the production cycle as a human readable time string.
     * 
     * @return The production cycle as a string
     */
    
    public String getCycleAsString()
    {
        final int cycle = this.cycle;
        if (cycle >= 24 * 60)
            return String.format("%d:%02d:%02d", cycle / 24 / 60,
                cycle % (24 * 60) / 60, cycle % 60);
        else if (cycle >= 60)
            return String.format("%d:%02d",
                cycle / 60, cycle % 60);
        else 
            return Integer.toString(cycle);
    }


    /**
     * Returns the produces product per cycle.
     * 
     * @return The product
     */

    public Product getProduct()
    {
        return this.product;
    }


    /**
     * Returns the factory price.
     * 
     * @return The price
     */

    public int getPrice()
    {
        return this.price;
    }


    /**
     * Returns the factory volume.
     * 
     * @return The volume
     */

    public int getVolume()
    {
        return this.volume;
    }


    /**
     * Returns the needed resources per cycle.
     * 
     * @return The resources
     */

    public Product[] getResources()
    {
        return this.resources;
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
        final Factory other = (Factory) obj;
        return new EqualsBuilder().append(this.id, other.id).isEquals();
    }


    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */

    @Override
    public int compareTo(final Factory o)
    {
        final int result = getName().compareTo(o.getName());
        if (result == 0) return getRace().compareTo(o.getRace());
        return result;
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
     * Returns the real cycle of this factory by including the sun power and the
     * asteroid yield into the calculation.
     * 
     * @param suns
     *            The sun power in percent
     * @param yield
     *            The asteroid yield
     * @return The production cycle
     */

    private int getRealCycle(final int suns, final int yield)
    {
        final String wareId = this.product.getWare().getId();

        // Handle solar power plants
        if (wareId.equals("energyCells"))
        {
            switch (suns)
            {
                case 0:
                    return 76;

                case 100:
                    return 118;

                case 150:
                    return 106;

                case 300:
                    return 90;

                case 400:
                    return 90;

                default:
                    throw new DataException("Unknown solar power " + suns);
            }
        }

        // Handle silicon mines
        if (wareId.equals("siliconWafers"))
        {
            final int basetime = 2400 / (yield + 1) + 1;
            final int multiple = (int) Math.floor(59.9 / basetime) + 1;
            return basetime * multiple;
        }

        // Handle ore mines
        if (wareId.equals("ore"))
        {
            final int basetime = 600 / (yield + 1) + 1;
            final int multiple = (int) Math.floor(59.9 / basetime) + 1;
            return basetime * multiple;
        }

        // Normal factories use normal cycle
        return this.cycle;
    }


    /**
     * Returns the real product of this factory by including the specified sun
     * power and asteroid yield into the calculation.
     * 
     * @param suns
     *            The sun power in percent
     * @param yield
     *            The asteroid yield
     * @return The product
     */

    private Product getRealProduct(final int suns, final int yield)
    {
        final Ware ware = this.product.getWare();
        final String wareId = ware.getId();

        // If no suns are present and this factory is a solar power plant then
        // it produces only the half product
        if (suns == 0 && wareId.equals("energyCells"))
            return new Product(ware, this.product.getQuantity() / 2);

        // Handle silicon mines
        if (wareId.equals("siliconWafers"))
        {
            final int baseTime = 2400 / (yield + 1) + 1;
            final int multiple = (int) Math.floor(59.9 / baseTime) + 1;
            return new Product(ware, multiple * this.product.getQuantity());
        }

        // Handle ore mines
        if (wareId.equals("ore"))
        {
            final int baseTime = 600 / (yield + 1) + 1;
            final int multiple = (int) Math.floor(59.9 / baseTime) + 1;
            return new Product(ware, multiple * (this.product.getQuantity() / 3));
        }

        // Normal factory, return normal product
        return this.product;
    }


    /**
     * Returns the real resources of this factory by including the specified sun
     * power and asteroid yield into the calculation.
     * 
     * @param suns
     *            The sun power in percent
     * @param yield
     *            The asteroid yield
     * @return The resources
     */

    private Product[] getRealResources(final int suns, final int yield)
    {
        final String wareId = this.product.getWare().getId();

        // If no suns are present and this factory is a solar power plant then
        // return half the resources
        if (suns == 0 && wareId.equals("energyCells"))
        {
            final Product resources[] = new Product[1];
            final Product resource = this.resources[0];
            resources[0] = new Product(resource.getWare(), resource
                .getQuantity() / 2);
            return resources;
        }

        // Handle silicon mines
        if (wareId.equals("siliconWafers"))
        {
            final Product resources[] = new Product[1];
            final Product resource = this.resources[0];
            final int baseTime = 2400 / (yield + 1) + 1;
            final int multiple = (int) Math.floor(59.9 / baseTime) + 1;
            resources[0] = new Product(resource.getWare(), multiple
                * resource.getQuantity());
            return resources;
        }

        // Handle ore mines
        if (wareId.equals("ore"))
        {
            final Product resources[] = new Product[1];
            final Product resource = this.resources[0];
            final int baseTime = 600 / (yield + 1) + 1;
            final int multiple = (int) Math.floor(59.9 / baseTime) + 1;
            resources[0] = new Product(resource.getWare(), multiple
                * (resource.getQuantity() / 3));
            return resources;
        }

        // Normal factory, return normal product
        return this.resources;
    }


    /**
     * Returns the product this factory produces in one hour.
     * 
     * @param suns
     *            The sun power to use in the calculation (for solar power
     *            plants)
     * @param yield
     *            The yield to use in the calculation
     * @return The product per hour.
     */

    public final Product getProductPerHour(final int suns, final int yield)
    {
        final Product product = getRealProduct(suns, yield);
        return new Product(product.getWare(), product.getQuantity() * 60 * 60
            / getRealCycle(suns, yield));
    }


    /**
     * Returns the product this factory produces in one hour for a default yield
     * (100% for power plants, 25 for mines).
     * 
     * @return The product produced per hour for a default yield
     */

    public Product getProductPerHour()
    {
        return getProductPerHour(100, 25);
    }


    /**
     * Returns the resources this factory needs in our hour.
     * 
     * @param suns
     *            The sun power to use in the calculation (for solar power
     *            plants)
     * @param yield
     *            The yield to use in the calculation (for mines)
     * @return The resources needed per hour
     */

    public Collection<Product> getResourcesPerHour(final int suns,
        final int yield)
    {
        final Product[] resources = this.getRealResources(suns, yield);
        final Collection<Product> resourcesPH = new ArrayList<Product>();
        for (final Product resource : resources)
        {
            resourcesPH.add(new Product(resource.getWare(), resource
                .getQuantity()
                * 60 * 60 / getRealCycle(suns, yield)));
        }
        return resourcesPH;
    }


    /**
     * Returns the resources this factory needs in one hour for a default yield
     * (100% for power plants, 25 for mines).
     * 
     * @return The resources needed per hour for a default yield
     */

    public Collection<Product> getResourcesPerHour()
    {
        return getResourcesPerHour(100, 25);
    }
}
