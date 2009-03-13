/*
 * $Id: JLinkLabel.java 767 2009-03-04 21:29:08Z k $
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.SortedSet;

import javax.swing.JComponent;

import de.ailis.xadrian.data.Sector;
import de.ailis.xadrian.data.factories.SectorFactory;


/**
 * Component which displays the sectors in a graphical way and let the user
 * select one.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 767 $
 */

public class SectorSelector extends JComponent
{
    /** Serial version UID */
    private static final long serialVersionUID = 42133575643122689L;

    /** The graphics buffer */
    private BufferedImage buffer;
    
    /** The scale factor of the map */
    private final float scale;
    

    /**
     * Constructor
     * 
     * @param maxW
     *            The maximum width
     * @param maxH
     *            The maximum height
     */

    public SectorSelector(final int maxW, final int maxH)
    {
        final SectorFactory sectorFactory = SectorFactory.getInstance();
        final int uniWidth = sectorFactory.getMaxX() * 100 + 150;
        final int uniHeight = sectorFactory.getMaxY() * 100 + 150;
        final float scaleX = (float) maxW / uniWidth;
        final float scaleY = (float) maxH / uniHeight;
        final float uniAR = (float) uniWidth / uniHeight;
        final float winAR = maxW / maxH;
        final int width, height;

        if (uniAR > winAR)
        {
            width = maxW;
            height = maxW * uniHeight / uniWidth;
            this.scale = scaleX;
        }
        else
        {
            height = maxH;
            width = maxH * uniWidth / uniHeight;
            this.scale = scaleY;
        }

        setPreferredSize(new Dimension(width, height));
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
    }


    /**
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */

    @Override
    public void paintComponent(final Graphics graphics)
    {
        super.paintComponent(graphics);

        final int width = getWidth();
        final int height = getHeight();

        if (this.buffer == null || this.buffer.getWidth() != width
            || this.buffer.getHeight() != height)
            this.buffer = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = this.buffer.createGraphics();

        final SectorFactory sectorFactory = SectorFactory.getInstance();
        final SortedSet<Sector> sectors = sectorFactory.getSectors();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        g.scale(this.scale, this.scale);
        g.translate(75, 75);
        for (final Sector sector : sectors)
        {
            final int sx = sector.getX();
            final int sy = sector.getY();

            g.setColor(sector.getRace().getColor());
            g.fillRect(sx * 100 - 40, sy * 100 - 40, 80, 80);
            
            g.setColor(new Color(0x606060));
            if (sector.getSouth() != null)
                g.fillRect(sx * 100 - 20, sy * 100 + 40, 40, 10);
            if (sector.getNorth() != null)
                g.fillRect(sx * 100 - 20, sy * 100 - 50, 40, 10);
            if (sector.getWest() != null)
                g.fillRect(sx * 100 - 50, sy * 100 - 20, 10, 40);
            if (sector.getEast() != null)
                g.fillRect(sx * 100 + 40, sy * 100 - 20, 10, 40);

            g.setColor(new Color(0x808080));
            g.drawRect(sx * 100 - 40, sy * 100 - 40, 80, 80);
        }

        g.setColor(Color.RED);
        graphics.drawImage(this.buffer, 0, 0, null);
    }
}
