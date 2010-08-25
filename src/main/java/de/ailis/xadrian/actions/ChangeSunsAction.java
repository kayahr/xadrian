/*
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
 * Changes the sun power.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ChangeSunsAction extends BaseAction implements StateListener
{
    /** Serial version UID */
    private static final long serialVersionUID = 4088425344137553110L;

    /** The complex provider */
    private final ComplexProvider provider;


    /**
     * Constructor
     *
     * @param provider
     *            The provider
     */

    public ChangeSunsAction(final ComplexProvider provider)
    {
        super("changeSuns", Icons.SUNS);
        this.provider = provider;
        setEnabled(provider.canChangeSuns());
        provider.addStateListener(this);
    }


    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */

    @Override
    public void actionPerformed(final ActionEvent e)
    {
        this.provider.changeSuns();
    }


    /**
     * @see de.ailis.xadrian.listeners.StateListener#stateChanged()
     */

    @Override
    public void stateChanged()
    {
        setEnabled(this.provider.canChangeSuns());
    }
}
