/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * The shopping list.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class ShoppingList implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = -2174714813369451947L;

    /** The shopping list items */
    private final List<ShoppingListItem> items = new ArrayList<ShoppingListItem>();

    /** The total quantity */
    private int totalQuantity = 0;

    /** The total volume */
    private int totalVolume = 0;

    /** The total price */
    private int totalPrice = 0;

    /** The sector with the nearest shipyard */
    private final Sector nearestShipyard;


    /**
     * Constructor.
     * 
     * @param nearestShipyard
     *            The nearest shipyard where the player can buy complex kits.
     *            Can be null if not known.
     */

    public ShoppingList(final Sector nearestShipyard)
    {
        this.nearestShipyard = nearestShipyard;
    }

    /**
     * Adds a new shopping list item to the list.
     * 
     * @param item
     *            The shopping list item to add
     */

    public void addItem(final ShoppingListItem item)
    {
        int i, max;

        this.totalVolume += item.getTotalVolume();
        this.totalQuantity += item.getQuantity();
        this.totalPrice += item.getTotalPrice();

        // Try to update an existing list item first
        for (i = 0, max = this.items.size(); i < max; i++)
        {
            final ShoppingListItem oldItem = this.items.get(i);
            if (oldItem.getFactory().equals(item.getFactory()))
            {
                this.items.set(i, new ShoppingListItem(item.getFactory(), item
                    .getQuantity()
                    + oldItem.getQuantity(), item.getNearestManufacturer()));
                return;
            }
        }

        // Add the new item
        this.items.add(item);
        Collections.sort(this.items);
    }


    /**
     * Returns the shopping list items.
     * 
     * @return The shopping list items
     */

    public List<ShoppingListItem> getItems()
    {
        return Collections.unmodifiableList(this.items);
    }


    /**
     * Returns the total volume.
     * 
     * @return The total volume
     */

    public int getTotalVolume()
    {
        return this.totalVolume + getTotalKitVolume();
    }


    /**
     * Returns the total quantity.
     * 
     * @return The total quantity
     */

    public int getTotalQuantity()
    {
        return this.totalQuantity + getKitQuantity();
    }


    /**
     * Returns the total price.
     * 
     * @return The total price
     */

    public int getTotalPrice()
    {
        return this.totalPrice + getTotalKitPrice();
    }

    
    /**
     * Returns the kit quantity.
     * 
     * @return The kit quantity
     */
    
    public int getKitQuantity()
    {
        return Math.max(0, this.totalQuantity - 1);
    }
    
    
    /**
     * Returns the single kit price
     * 
     * @return The single kit price
     */

    public int getKitPrice()
    {
        return Complex.KIT_PRICE;
    }

    
    /**
     * Returns the total kit price.
     * 
     * @return The total kit price
     */
    
    public int getTotalKitPrice()
    {
        return getKitPrice() * getKitQuantity();
    }


    /**
     * Returns the nearest shipyard where the player can buy complex
     * construction kits. Can be null if no shipyard is available.
     * 
     * @return The nearest shipyard or null if none
     */

    public Sector getNearestShipyard()
    {
        return this.nearestShipyard;
    }
    

    /**
     * Returns the kit volume.
     * 
     * @return The kit volume
     */
    
    public int getKitVolume()
    {
        return Complex.KIT_VOLUME;
    }
    
    
    /**
     * Returns the total kit volume.
     * 
     * @return The total kit volume
     */
    
    public int getTotalKitVolume()
    {
        return getKitVolume() * getKitQuantity();
    }
}
