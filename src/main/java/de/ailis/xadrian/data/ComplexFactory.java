/*
 * $Id$
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * A factory (or multiple factories with same configuration) in a complex.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class ComplexFactory implements Serializable, Comparable<ComplexFactory>
{
    /** Serial version UID */
    private static final long serialVersionUID = -4731442033981700443L;

    /** The factory */
    private final Factory factory;

    /** The number of factories */
    private int quantity;

    /** The yield (for mines) */
    private int yield;


    /**
     * Constructor
     * 
     * @param factory
     *            The factory
     * @param quantity
     *            The number of factories
     * @param yield
     *            The yield (for mines)
     */

    public ComplexFactory(final Factory factory, final int quantity,
        final int yield)
    {
        this.factory = factory;
        this.quantity = quantity;
        this.yield = yield;
    }


    /**
     * Return the factory.
     * 
     * @return The factory
     */

    public Factory getFactory()
    {
        return this.factory;
    }


    /**
     * Returns the number of factories.
     * 
     * @return The number of factories
     */

    public int getQuantity()
    {
        return this.quantity;
    }


    /**
     * Returns the yield (for mines)
     * 
     * @return The yield
     */

    public int getYield()
    {
        return this.yield;
    }


    /**
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.factory).append(this.quantity)
            .append(this.yield).hashCode();
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
        final ComplexFactory other = (ComplexFactory) obj;
        return new EqualsBuilder().append(this.factory, other.factory).append(
            this.quantity, other.quantity).append(this.yield, other.yield)
            .isEquals();
    }


    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */

    @Override
    public int compareTo(final ComplexFactory other)
    {
        final int result = this.factory.getName().compareTo(
            other.factory.getName());
        if (result == 0)
            return -1 * Integer.valueOf(this.yield).compareTo(other.yield);
        return result;
    }


    /**
     * Adds the specified quantity.
     * 
     * @param quantity
     *            The quantity to add
     */

    public void addQuantity(final int quantity)
    {
        this.quantity += quantity;
    }


    /**
     * Sets the quantity.
     * 
     * @param quantity
     *            The quantity to set
     */

    public void setQuantity(final int quantity)
    {
        this.quantity = quantity;
    }


    /**
     * Sets the yield.
     * 
     * @param yield
     *            The yield to set
     */

    public void setYield(final int yield)
    {
        this.yield = yield;
    }


    /**
     * Returns the product this complex factory/factories produces in one hour.
     * 
     * @param suns
     *            The sun power to use in the calculation (for solar power
     *            plants)
     * @return The product per hour.
     */

    public Product getProductPerHour(final Suns suns)
    {
        final Product product = this.factory
            .getProductPerHour(suns, this.yield);
        return new Product(product.getWare(), product.getQuantity()
            * this.quantity);
    }


    /**
     * Returns the product this complex factory/factories produces in one hour
     * using a default sun power of 100%.
     * 
     * @return The product per hour.
     */

    public Product getProductPerHour()
    {
        return getProductPerHour(Suns.P100);
    }


    /**
     * Returns the resources this complex factory/factories needs in our hour.
     * 
     * @param suns
     *            The sun power to use in the calculation (for solar power
     *            plants)
     * @return The resources needed per hour
     */

    public Collection<Product> getResourcesPerHour(final Suns suns)
    {
        final Collection<Product> resources = new ArrayList<Product>();
        for (final Product resource : this.factory.getResourcesPerHour(suns,
            this.yield))
        {
            resources.add(new Product(resource.getWare(), resource
                .getQuantity()
                * this.quantity));
        }
        return resources;
    }

    /**
     * Returns the resources this complex factory/factories needs in our hour
     * using a default sun power of 100%.
     * 
     * @return The resources needed per hour
     */

    public Collection<Product> getResourcesPerHour()
    {
        return getResourcesPerHour(Suns.P100);
    }


    /**
     * Returns the storage capacities.
     * 
     * @return The storage capacities
     */

    public Collection<Capacity> getCapacities()
    {
        final Collection<Capacity> capacities = new ArrayList<Capacity>();
        for (final Capacity capacity : this.factory.getCapacities())
        {
            capacities.add(new Capacity(capacity.getWare(), capacity
                .getQuantity()
                * this.quantity));
        }
        return capacities;
    }
}
