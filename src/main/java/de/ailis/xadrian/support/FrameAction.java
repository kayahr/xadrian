/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.support;

import javax.swing.Icon;
import javax.swing.JFrame;


/**
 * Base class for all actions of the main frame.
 * 
 * @param <T>
 *            The frame type
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class FrameAction<T extends JFrame> extends BaseAction
{
    /** Serial version UID */
    private static final long serialVersionUID = 2141284935409378082L;

    /** The main frame */
    protected T frame;


    /**
     * Constructor
     * 
     * @param frame
     *            The frame
     * @param name
     *            The action name
     */

    public FrameAction(final T frame, final String name)
    {
        super(name);
        this.frame = frame;
    }
    

    /**
     * Constructor
     * 
     * @param frame
     *            The frame
     * @param name
     *            The action name
     * @param icon
     *            The action icon
     */

    public FrameAction(final T frame, final String name, final Icon icon)
    {
        super(name, icon);
        this.frame = frame;
    }
}
