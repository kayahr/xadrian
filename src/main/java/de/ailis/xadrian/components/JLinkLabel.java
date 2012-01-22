/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.components;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

import de.ailis.xadrian.utils.SwingUtils;

/**
 * Label component which styles the label as a hyperlink and opens a url in an
 * external browser when clicked.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class JLinkLabel extends JLabel
{
    /** Serial version UID */
    private static final long serialVersionUID = -6860272513583477660L;

    /**
     * Opens a URL on click.
     */
    private static class LinkClickHandler extends MouseAdapter
    {
        /** The URL to open. */
        private final String url;

        /**
         * Constructor.
         *
         * @param url
         *            The URL to open.
         */
        LinkClickHandler(final String url)
        {
            this.url = url;
        }

        /**
         * @see MouseAdapter#mouseClicked(MouseEvent)
         */
        @Override
        public void mouseClicked(final MouseEvent event)
        {
            SwingUtils.openBrowser(this.url);
        }
    }

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
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addMouseListener(new LinkClickHandler(url));
    }
}
