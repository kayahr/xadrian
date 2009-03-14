/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.support;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;


/**
 * Text Renderer
 * 
 * @author Klaus Reimer (k@ailis.de
 * @version $Revision$
 */

public class TextRenderer
{
    /** The text lines */
    private final List<TextLine> lines = new ArrayList<TextLine>();

    /** The current text line */
    private TextLine currentLine = null;

    /** The current font */
    private Font currentFont = new Font("Arial", Font.PLAIN, 12);

    /** The current color */
    private Color currentColor = Color.BLACK;


    /**
     * Sets the current font.
     * 
     * @param font
     *            The current font to set
     */

    public void setFont(final Font font)
    {
        this.currentFont = font;
    }


    /**
     * Sets the current color.
     * 
     * @param color
     *            The current color to set
     */

    public void setColor(final Color color)
    {
        this.currentColor = color;
    }


    /**
     * Adds a new text.
     * 
     * @param text
     *            The text to add
     */

    public void addText(final String text)
    {
        final TextPart part = new TextPart(this.currentFont, this.currentColor,
            text);
        if (this.currentLine == null)
        {
            this.currentLine = new TextLine();
            this.lines.add(this.currentLine);
        }
        this.currentLine.add(part);
    }


    /**
     * Inserts a line break
     */

    public void newLine()
    {
        this.currentLine = new TextLine();
        this.lines.add(this.currentLine);
    }


    /**
     * Renders the text
     * 
     * @param g
     *            The graphics context
     * @param x
     *            The x position
     * @param y
     *            The y position
     */

    public void render(final Graphics2D g, final double x, final double y)
    {
        double curY = y;
        for (final TextLine line : this.lines)
        {
            line.render(g, x, curY);
            curY += line.getBounds(g.getFontRenderContext()).getHeight();
        }
    }


    /**
     * Returns the text bounds.
     * 
     * @param context
     *            The font render context
     * @return The text bounds
     */

    public Rectangle2D getBounds(final FontRenderContext context)
    {
        double y = 0;
        double x = 0;
        double width = 0;
        double height = 0;
        boolean first = true;
        for (final TextLine line : this.lines)
        {
            final Rectangle2D bounds = line.getBounds(context);
            if (first)
            {
                y = bounds.getY();
                x = bounds.getX();
                width = bounds.getWidth();
                height = bounds.getHeight();
            }
            else
            {
                x = Math.max(x, bounds.getX());
                height += bounds.getHeight();
                width = Math.max(width, bounds.getWidth());
            }
            first = false;
        }
        return new Rectangle2D.Double(x, y, width, height);
    }


    /**
     * A single text line.
     * 
     * @author Klaus Reimer (k@ailis.de)
     * @version $Revision$
     */

    class TextLine
    {
        /** The text parts in this line */
        private final List<TextPart> parts = new ArrayList<TextPart>();


        /**
         * Adds a text part.
         * 
         * @param part
         *            The text part to add
         */

        public void add(final TextPart part)
        {
            this.parts.add(part);
        }


        /**
         * Returns the text bounds.
         * 
         * @param context
         *            The font render context
         * @return The text bounds
         */

        public Rectangle2D getBounds(final FontRenderContext context)
        {
            double y = 0;
            double x = 0;
            double width = 0;
            double height = 0;
            boolean first = true;
            for (final TextPart part : this.parts)
            {
                final Rectangle2D bounds = part.getBounds(context);
                if (first)
                {
                    y = bounds.getY();
                    x = bounds.getX();
                    width = bounds.getWidth();
                    height = bounds.getHeight();
                }
                else
                {
                    y = Math.max(y, bounds.getY());
                    width += bounds.getWidth();
                    height = Math.max(height, bounds.getHeight());
                }
                first = false;
            }
            return new Rectangle2D.Double(x, y, width, height);
        }


        /**
         * Renders the text line
         * 
         * @param g
         *            The graphics context
         * @param x
         *            The x position
         * @param y
         *            The y position
         */

        public void render(final Graphics2D g, final double x, final double y)
        {
            double curX = x;
            for (final TextPart part : this.parts)
            {
                part.render(g, curX, y);
                curX += part.getBounds(g.getFontRenderContext()).getWidth();
            }
        }
    }


    /**
     * A single text part in a line.
     * 
     * @author Klaus Reimer (k@ailis.de)
     * @version $Revision$
     */

    class TextPart
    {
        /** The font */
        private final Font font;

        /** The color */
        private final Color color;

        /** The text */
        private final String text;


        /**
         * Creates a new text part.
         * 
         * @param font
         *            The font
         * @param color
         *            The color
         * @param text
         *            The text
         */

        public TextPart(final Font font, final Color color, final String text)
        {
            this.font = font;
            this.text = text;
            this.color = color;
        }


        /**
         * Returns the text bounds.
         * 
         * @param context
         *            The font render context
         * @return The text bounds
         */

        public Rectangle2D getBounds(final FontRenderContext context)
        {
            return this.font.getStringBounds(this.text, context);
        }


        /**
         * Renders the text part
         * 
         * @param g
         *            The graphics context
         * @param x
         *            The x position
         * @param y
         *            The y position
         */

        public void render(final Graphics2D g, final double x, final double y)
        {
            final Rectangle2D bounds = getBounds(g.getFontRenderContext());
            g.setFont(this.font);
            g.setColor(this.color);
            g.drawString(this.text, (int) (x + bounds.getX()),
                (int) (y - bounds.getY()));
        }
    }
}
