/*
 * $Id: ClipboardProvider.java 788 2009-03-08 14:14:51Z k $
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.interfaces;


/**
 * This interface is implemented by all components which can be reseted.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 788 $
 */

public interface ResetProvider
{
    /**
     * Performs a reset.
     */

    public void reset();


    /**
     * Checks if a reset is allowed.
     * 
     * @return True if reset is allowed, false if not
     */

    public boolean canReset();
}
