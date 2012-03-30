/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */
package de.ailis.xadrian.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.ailis.xadrian.resources.Icons;
import de.ailis.xadrian.support.BaseAction;
import de.ailis.xadrian.support.I18N;
import de.ailis.xadrian.utils.SwingUtils;

/**
 * Opens the Google Plus page of Xadrian in the browser.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class GooglePlusAction extends BaseAction
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public GooglePlusAction()
    {
        super("googlePlus", Icons.GOOGLE_PLUS);
    }

    /**
     * @see ActionListener#actionPerformed(ActionEvent)
     */
    @Override
    public void actionPerformed(final ActionEvent e)
    {
        SwingUtils.openBrowser(I18N.getString("googlePlus"));
    }
}
