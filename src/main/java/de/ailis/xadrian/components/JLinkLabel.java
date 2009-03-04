/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.components;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

import javax.swing.JLabel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Label component which styles the label as a hyperlink and opens a url in an
 * external browser when clicked.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class JLinkLabel extends JLabel
{
    /** Serial version UID */
    private static final long serialVersionUID = -6860272513583477660L;
    
    /** The logger */
    static final Log log = LogFactory.getLog(JLinkLabel.class);


    /**
     * Constructor
     * 
     * @param text
     *            The label text
     * @param url
     *            The URL to open in a browser
     */

    public JLinkLabel(final String text, final String url)
    {
        super("<html><a href=\"#\">" + text + "</a></html>");
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(final MouseEvent event)
            {
                try
                {
                    Desktop.getDesktop().browse(new URI(url));
                }
                catch (final Exception e)
                {
                    log.error("Unable to external browser: " + e, e);
                }
            }
        });
    }
}
