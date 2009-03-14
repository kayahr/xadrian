/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.interfaces;

import de.ailis.xadrian.listeners.ComplexStateListener;



/**
 * This interface is implemented by all components which works with a complex.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public interface ComplexProvider
{
    /**
     * Adds a complex state listener.
     * 
     * @param listener
     *            The listener to add
     */

    public void addComplexStateListener(ComplexStateListener listener);


    /**
     * Removes a complex state listener.
     * 
     * @param listener
     *            The listener to remove
     */

    public void removeComplexStateListener(ComplexStateListener listener);

    
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
