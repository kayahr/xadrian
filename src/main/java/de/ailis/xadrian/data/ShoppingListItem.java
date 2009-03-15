/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.data;

import java.io.Serializable;


/**
 * A single item on the factory factory shopping list
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
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


    /**
     * Constructor
     * 
     * @param factory
     *            The factory
     * @param quantity
     *            The number of factories
     * @param nearestManufacturer
     *            The nearest manufacturer
     */

    public ShoppingListItem(final Factory factory, final int quantity,
        final Station nearestManufacturer)
    {
        this.factory = factory;
        this.quantity = quantity;
        this.nearestManufacturer = nearestManufacturer;
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
}
