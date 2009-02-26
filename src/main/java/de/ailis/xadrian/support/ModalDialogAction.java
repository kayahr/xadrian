/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.support;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.ailis.xadrian.support.ModalDialog.Result;



/**
 * Closes a modal dialog with the specified modal result.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class ModalDialogAction extends BaseAction
{
    /** Serial version UID */
    private static final long serialVersionUID = 2398662376489192326L;

    /** The modal dialog connected to this action */
    private final ModalDialog dialog;
    
    /** The modal result */
    private final Result result;


    /**
     * Constructor
     * 
     * @param dialog
     *            The modal dialog to control
     * @param result
     *            The modal result
     */

    public ModalDialogAction(final ModalDialog dialog, final Result result)
    {
        super(result.toString().toLowerCase());
        this.dialog = dialog;
        this.result = result;
    }


    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */

    public void actionPerformed(final ActionEvent e)
    {
        this.dialog.close(this.result);
    }
}
