/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.ailis.xadrian.dialogs.SelectSectorDialog;
import de.ailis.xadrian.interfaces.SectorProvider;
import de.ailis.xadrian.interfaces.StateProvider;
import de.ailis.xadrian.listeners.StateListener;
import de.ailis.xadrian.resources.Icons;
import de.ailis.xadrian.support.BaseAction;
import de.ailis.xadrian.support.ModalDialog.Result;


/**
 * Changes the sector of the complex.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ChangeSectorAction extends BaseAction implements StateListener
{
    /** Serial version UID */
    private static final long serialVersionUID = -5290504312967776304L;

    /** The complex provider */
    private final SectorProvider provider;


    /**
     * Constructor
     *
     * @param provider
     *            The provider
     * @param context
     *            The context name (for having different action settings per
     *            context)
     */

    public ChangeSectorAction(final SectorProvider provider,
        final String context)
    {
        super("changeSector", Icons.SECTOR, context);
        this.provider = provider;
        setEnabled(provider.canChangeSector());
        if (provider instanceof StateProvider)
            ((StateProvider) provider).addStateListener(this);
    }


    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */

    @Override
    public void actionPerformed(final ActionEvent e)
    {
        final SelectSectorDialog dialog = SelectSectorDialog.getInstance();
        dialog.setSelected(this.provider.getSector());
        if (dialog.open() == Result.OK)
            this.provider.setSector(dialog.getSelected());
    }


    /**
     * @see de.ailis.xadrian.listeners.StateListener#stateChanged()
     */

    @Override
    public void stateChanged()
    {
        setEnabled(this.provider.canChangeSector());
    }
}
