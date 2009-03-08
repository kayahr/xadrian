/*
 * $Id: NewComplexAction.java 704 2009-02-20 08:10:11Z k $
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
 * Adds a new factory to the complex.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 704 $
 */

public class AddFactoryAction extends BaseAction implements ComplexStateListener
{
    /** Serial version UID */
    private static final long serialVersionUID = -458513467199019742L;

    /** The complex provider */
    private final ComplexProvider provider;
    

    /**
     * Constructor
     * 
     * @param provider
     *            The complex provider
     */

    public AddFactoryAction(final ComplexProvider provider)
    {
        super("addFactory", Icons.ADD);
        this.provider = provider;
        setEnabled(provider.canAddFactory());
        provider.addComplexStateListener(this);
    }


    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */

    public void actionPerformed(final ActionEvent e)
    {
        this.provider.addFactory();
    }

    
    /**
     * @see de.ailis.xadrian.listeners.ComplexStateListener#complexStateChanged(de.ailis.xadrian.interfaces.ComplexProvider)
     */
    
    @Override
    public void complexStateChanged(final ComplexProvider provider)
    {
        setEnabled(provider.canAddFactory());
    }
}
