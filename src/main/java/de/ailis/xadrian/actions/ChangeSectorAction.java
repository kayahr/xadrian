/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */
package de.ailis.xadrian.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.ailis.xadrian.data.Sector;
import de.ailis.xadrian.dialogs.SelectSectorDialog;
import de.ailis.xadrian.interfaces.GameProvider;
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

    /** The game provider. */
    private final GameProvider gameProvider;

    /** The complex provider */
    private final SectorProvider sectorProvider;

    /**
     * Constructor
     *
     * @param gameProvider
     *            The game provider.
     * @param sectorProvider
     *            The sector provider.
     * @param context
     *            The context name (for having different action settings per
     *            context)
     */
    public ChangeSectorAction(final GameProvider gameProvider,
        final SectorProvider sectorProvider, final String context)
    {
        super("changeSector", Icons.SECTOR, context);
        if (gameProvider == null)
            throw new IllegalArgumentException("gameProvider must be set");
        this.gameProvider = gameProvider;
        this.sectorProvider = sectorProvider;
        setEnabled(sectorProvider.canChangeSector());
        if (sectorProvider instanceof StateProvider)
            ((StateProvider) sectorProvider).addStateListener(this);
    }

    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent e)
    {
        final Sector sector = this.sectorProvider.getSector();
        final SelectSectorDialog dialog =
            this.gameProvider.getGame().getSelectSectorDialog();
        dialog.setSelected(sector);
        if (dialog.open() == Result.OK)
            this.sectorProvider.setSector(dialog.getSelected());
    }

    /**
     * @see de.ailis.xadrian.listeners.StateListener#stateChanged()
     */
    @Override
    public void stateChanged()
    {
        setEnabled(this.sectorProvider.canChangeSector());
    }
}
