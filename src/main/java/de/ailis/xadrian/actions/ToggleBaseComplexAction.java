/*
 * $Id$
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.ailis.xadrian.interfaces.ComplexProvider;
import de.ailis.xadrian.listeners.StateListener;
import de.ailis.xadrian.resources.Icons;
import de.ailis.xadrian.support.BaseAction;


/**
 * Toggles the addition of a base complex.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public class ToggleBaseComplexAction extends BaseAction implements
    StateListener
{
    /** Serial version UID */
    private static final long serialVersionUID = 5699001225424519853L;

    /** The complex provider */
    private final ComplexProvider provider;


    /**
     * Constructor
     * 
     * @param provider
     *            The provider
     */

    public ToggleBaseComplexAction(final ComplexProvider provider)
    {
        super("toggleBaseComplex", Icons.WAND);
        this.provider = provider;
        setEnabled(provider.canToggleBaseComplex());
        setSelected(false);
        provider.addStateListener(this);
    }


    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */

    public void actionPerformed(final ActionEvent e)
    {
        this.provider.toggleBaseComplex();
    }


    /**
     * @see de.ailis.xadrian.listeners.StateListener#stateChanged()
     */

    @Override
    public void stateChanged()
    {
        setEnabled(this.provider.canToggleBaseComplex());
        setSelected(this.provider.isAddBaseComplex());
    }
}
