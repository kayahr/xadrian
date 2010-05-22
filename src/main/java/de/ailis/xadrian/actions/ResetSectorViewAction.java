/*
 * $Id: CopyAction.java 788 2009-03-08 14:14:51Z k $
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.ailis.xadrian.components.SectorView;
import de.ailis.xadrian.support.BaseAction;


/**
 * Resets the sector view.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 788 $
 */

public class ResetSectorViewAction extends BaseAction
{
    /** Serial version UID */
    private static final long serialVersionUID = 1054607090679022229L;
    
    /** The sector view */
    private final SectorView sectorView;


    /**
     * Constructor
     * 
     * @param sectorView
     *            The connected sector view
     */

    public ResetSectorViewAction(final SectorView sectorView)
    {
        super("reset"/* Icons.COPY*/);
        this.sectorView = sectorView;
    }


    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */

    public void actionPerformed(final ActionEvent e)
    {
        this.sectorView.reset();
    }
}
