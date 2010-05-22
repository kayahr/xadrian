/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.support;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;


/**
 * Base class for all actions.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class BaseAction extends AbstractAction
{
    /** Serial version UID */
    private static final long serialVersionUID = -4447098088538868648L;


    /**
     * Constructor.
     * 
     * @param name
     *            The action name
     */

    public BaseAction(final String name)
    {
        this(name, null);
    }


    /**
     * Constructor
     * 
     * @param name
     *            The action name
     * @param icon
     *            The action icon
     */

    public BaseAction(final String name, final Icon icon)
    {
        super(I18N.getTitle("action." + name), icon);
        setMnemonic(I18N.getMnemonic("action." + name));
        setAccelerator(I18N.getAccelerator("action." + name));
        setToolTip(I18N.getToolTip("action." + name));
    }


    /**
     * Constructor
     * 
     * @param name
     *            The action name
     * @param icon
     *            The action icon
     * @param context
     *            The context name (For actions which have different settings in
     *            different contexts)
     */

    public BaseAction(final String name, final Icon icon, final String context)
    {
        this(name + "." + context, icon);
    }


    /**
     * Sets the selection state.
     * 
     * @param selected
     *            The selection state
     */

    protected void setSelected(final boolean selected)
    {
        putValue(Action.SELECTED_KEY, selected);
    }


    /**
     * Checks if this action is selected.
     * 
     * @return True if action is selected, false if not
     */

    protected boolean isSelected()
    {
        final Boolean selected = (Boolean) getValue(Action.SELECTED_KEY);
        return selected == null ? false : selected;
    }


    /**
     * Sets the mnemonic.
     * 
     * @param mnemonic
     *            The mnemonic to set
     */

    private void setMnemonic(final int mnemonic)
    {
        putValue(Action.MNEMONIC_KEY, mnemonic);
    }


    /**
     * Sets the tool tip text.
     * 
     * @param toolTip
     *            The tool tip text to set
     */

    private void setToolTip(final String toolTip)
    {
        putValue(Action.SHORT_DESCRIPTION, toolTip);
    }


    /**
     * Sets the accelerator key
     * 
     * @param accelerator
     *            The accelerator key to set
     */

    private void setAccelerator(final KeyStroke accelerator)
    {
        putValue(Action.ACCELERATOR_KEY, accelerator);
    }
}
