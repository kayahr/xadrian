/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.interfaces;




/**
 * This interface is implemented by all components which works with a complex.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public interface ComplexProvider extends StateProvider
{
    /**
     * Adds a factory.
     */

    public void addFactory();

    
    /**
     * Change the suns
     */

    public void changeSuns();

    
    /**
     * Change the sector
     */

    public void changeSector();

    
    /**
     * Change the ware prices
     */

    public void changePrices();
    
    
    /**
     * Toggle the base complex
     */
    
    public void toggleBaseComplex();
    
    

    /**
     * Returns true if component can add a factory.
     * 
     * @return True if component can add a factory. False if not.
     */

    public boolean canAddFactory();
    
    
    /**
     * Returns true if component can change the suns.
     * 
     * @return True if component can change the suns. False if not.
     */

    public boolean canChangeSuns();

    
    /**
     * Returns true if component can change the ware prices.
     * 
     * @return True if component can change the ware prices. False if not.
     */

    public boolean canChangePrices();

    
    /**
     * Returns true if component can change the sector.
     * 
     * @return True if component can change the sector. False if not.
     */

    public boolean canChangeSector();

    
    /**
     * Returns true if component can toggle the base complex
     * 
     * @return True if component can toggle the base complex. False if not.
     */

    public boolean canToggleBaseComplex();


    
    /**
     * Returns true if base complex is added. False if not.
     * 
     * @return True if base complex is added. False if not
     */
    
    public boolean isAddBaseComplex();
}
