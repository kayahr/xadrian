/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.ailis.xadrian.interfaces.ComplexProvider;
import de.ailis.xadrian.listeners.ComplexStateListener;
import de.ailis.xadrian.resources.Icons;
import de.ailis.xadrian.support.BaseAction;


/**
 * Changes the ware prices of the complex.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class ChangePricesAction extends BaseAction implements ComplexStateListener
{
    /** Serial version UID */
    private static final long serialVersionUID = -4477579341504318226L;
    
    /** The complex provider */
    private final ComplexProvider provider;

    
    /**
     * Constructor
     * 
     * @param provider
     *            The provider
     */

    public ChangePricesAction(final ComplexProvider provider)
    {
        super("changePrices", Icons.PRICES);
        this.provider = provider;
        setEnabled(provider.canChangePrices());
        provider.addComplexStateListener(this);
    }


    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */

    public void actionPerformed(final ActionEvent e)
    {
        this.provider.changePrices();
    }

    
    /**
     * @see ComplexStateListener#complexStateChanged(ComplexProvider)
     */
    
    @Override
    public void complexStateChanged(final ComplexProvider provider)
    {
        setEnabled(provider.canChangePrices());
    }
}
