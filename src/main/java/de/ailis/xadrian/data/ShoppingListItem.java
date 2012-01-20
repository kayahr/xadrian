/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */

package de.ailis.xadrian.data;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * A single item on the factory factory shopping list
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public class ShoppingListItem implements Serializable,
    Comparable<ShoppingListItem>
{
    /** Serial version UID */
    private static final long serialVersionUID = -1834341071680921642L;

    /** The factory */
    private final Factory factory;

    /** The number of factories */
    private final int quantity;

    /** The nearest manufacturer of this factory */
    private final Station nearestManufacturer;

    /** The number of built factories */
    private final int quantityBuilt;

    /**
     * Constructor
     * 
     * @param factory
     *            The factory
     * @param quantity
     *            The number of factories
     * @param nearestManufacturer
     *            The nearest manufacturer
     * @param quantityBuilt
     *            The number of built factories
     */
    public ShoppingListItem(final Factory factory, final int quantity,
        final Station nearestManufacturer, final int quantityBuilt)
    {
        this.factory = factory;
        this.quantity = quantity;
        this.nearestManufacturer = nearestManufacturer;
        this.quantityBuilt = quantityBuilt;
    }

    /**
     * Returns the factory.
     * 
     * @return The factory
     */
    public Factory getFactory()
    {
        return this.factory;
    }

    /**
     * Returns the quantity.
     * 
     * @return The quantity
     */
    public int getQuantity()
    {
        return this.quantity;
    }

    /**
     * Returns the nearest manufacturer.
     * 
     * @return The nearest manufacturer
     */
    public Station getNearestManufacturer()
    {
        return this.nearestManufacturer;
    }

    /**
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(final ShoppingListItem o)
    {
        return this.factory.compareTo(o.getFactory());
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.factory).append(this.quantity)
            .append(this.quantityBuilt).append(this.nearestManufacturer)
            .toHashCode();
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
        final ShoppingListItem other = (ShoppingListItem) obj;
        return new EqualsBuilder().append(this.factory, other.factory)
            .append(this.quantity, other.quantity)
            .append(this.quantityBuilt, other.quantityBuilt)
            .append(this.nearestManufacturer, other.nearestManufacturer)
            .isEquals();
    }

    /**
     * Returns the volume of a single factory.
     * 
     * @return The volume of a single factory
     */
    public int getVolume()
    {
        return this.factory.getVolume();
    }

    /**
     * Returns the total volume of all factories.
     * 
     * @return The total volume of all factories
     */
    public int getTotalVolume()
    {
        return getVolume() * this.quantity;
    }

    /**
     * Returns the price of a single factory.
     * 
     * @return The price of a single factory
     */
    public int getPrice()
    {
        return this.factory.getPrice();
    }

    /**
     * Returns the total price of all factories.
     * 
     * @return The total price of all factories
     */
    public int getTotalPrice()
    {
        return getPrice() * this.quantity;
    }

    /**
     * Returns the number of built factories.
     * 
     * @return The number of built factories
     */
    public int getQuantityBuilt()
    {
        return this.quantityBuilt;
    }

    /**
     * Returns the number of factories left.
     * 
     * @return The number of factories left
     */
    public int getQuantityLeft()
    {
        return this.quantity - this.quantityBuilt;
    }
}
