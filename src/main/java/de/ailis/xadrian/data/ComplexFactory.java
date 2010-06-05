/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * A factory (or multiple factories with same configuration) in a complex.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ComplexFactory implements Serializable, Comparable<ComplexFactory>
{
    /** Serial version UID */
    private static final long serialVersionUID = -4731442033981700443L;

    /** The factory */
    private final Factory factory;

    /** The number of factories */
    private int quantity;

    /** The yields (for mines) */
    private final List<Integer> yields;

    /** If this factory is currently disabled. */
    private boolean disabled;


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
        if (factory.isMine())
        {
            this.yields = new ArrayList<Integer>();
            this.yields.add(yield);
            this.quantity = 1;
        }
        else
        {
            this.quantity = quantity;
            this.yields = null;
        }
    }


    /**
     * Constructor for adding a mine with multiple yields.
     *
     * @param factory
     *            The mine
     * @param yields
     *            The yields
     */

    public ComplexFactory(final Factory factory, final List<Integer> yields)
    {
        this.factory = factory;
        this.yields = new ArrayList<Integer>();
        setYields(yields);
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
        if (this.factory.isMine()) return this.yields.size();
        return this.quantity;
    }


    /**
     * Increases the number of factories.
     *
     * @return True if quantity was changed, false if not
     */

    public boolean increaseQuantity()
    {
        if (this.factory.isMine()) return false;
        this.quantity++;
        return true;
    }


    /**
     * Decreases the number of factories.
     *
     * @return True if quantity was changed, false if not
     */

    public boolean decreaseQuantity()
    {
        if (this.factory.isMine()) return false;
        if (this.quantity == 0) return false;
        this.quantity--;
        return true;
    }


    /**
     * Returns the yield (for mines)
     *
     * @return The yield
     */

    public int getYield()
    {
        final double productPerHour = getProductPerHour().getQuantity()
            / getQuantity();
        final int base = this.factory.isSiliconMine() ? 2400 : 600;
        final double quantity = this.factory.getProduct().getQuantity();
        return (int) Math.round((productPerHour * base)
            / ((1800 * quantity) - productPerHour) - 1);
    }


    /**
     * Returns the asteroid yields (for mines)
     *
     * @return The asteroid yields
     */

    public List<Integer> getYields()
    {
        return Collections.unmodifiableList(this.yields);
    }


    /**
     * Returns the yields as a string to be displayed in the complex table.
     *
     * @return The yields as a string
     */

    public String getYieldsAsString()
    {
        final StringBuilder builder = new StringBuilder();
        for (final Integer yield : this.yields)
        {
            if (builder.length() > 0) builder.append(", ");
            builder.append(yield);
        }
        return builder.toString();
    }


    /**
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.factory).append(this.quantity)
                .append(this.yields).hashCode();
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
            this.quantity, other.quantity).append(this.yields, other.yields)
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
            return -1 * Integer.valueOf(getYield()).compareTo(other.getYield());
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
        if (this.factory.isMine()) return;
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
        if (this.factory.isMine()) return;
        this.quantity = quantity;
    }


    /**
     * Sets the yields.
     *
     * @param yields
     *            The yields to set
     */

    public void setYields(final List<Integer> yields)
    {
        this.yields.clear();
        this.yields.addAll(yields);
        this.quantity = this.yields.size();
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
        if (this.factory.isMine())
        {
            double quantity = 0;
            for (final Integer yield : this.yields)
            {
                final Product product = this.factory
                        .getProductPerHour(suns, yield);
                quantity += product.getQuantity();
            }
            return new Product(this.factory.getProduct().getWare(), quantity
                * (this.disabled ? 0 : 1));
        }

        final Product product = this.factory
                .getProductPerHour(suns, 0);
        return new Product(product.getWare(), product.getQuantity()
            * (this.disabled ? 0 : this.quantity));
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

        if (this.factory.isMine())
        {
            for (final Integer yield : this.yields)
            {
                for (final Product resource : this.factory.getResourcesPerHour(
                    suns,
                    yield))
                {
                    resources.add(new Product(resource.getWare(), resource
                            .getQuantity()
                        * (this.disabled ? 0 : 1)));
                }
            }
            return resources;
        }

        for (final Product resource : this.factory.getResourcesPerHour(suns,
            0))
        {
            resources.add(new Product(resource.getWare(), resource
                    .getQuantity()
                * (this.disabled ? 0 : this.quantity)));
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
                * (this.disabled ? 0 : getQuantity())));
        }
        return capacities;
    }


    /**
     * Sets the disabled state of this factory.
     *
     * @param disabled
     *            True to disable the factory, false to enable it
     */

    public void setDisabled(final boolean disabled)
    {
        this.disabled = disabled;
    }


    /**
     * Enables the factory
     */

    public void enable()
    {
        setDisabled(false);
    }


    /**
     * Disables the factory
     */

    public void disable()
    {
        setDisabled(true);
    }


    /**
     * Checks if the factory is currently disabled.
     *
     * @return True if factory is disabled, false if not
     */

    public boolean isDisabled()
    {
        return this.disabled;
    }


    /**
     * Checks if the factory is currently enabled.
     *
     * @return True if factory is enabled, false if not
     */

    public boolean isEnabled()
    {
        return !this.disabled;
    }
}
