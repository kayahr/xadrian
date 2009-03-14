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
 * TextRenderer
 * 
 * @author k
 * @version $Revision$
 */

public class TextRenderer
{
    private final List<TextLine> lines = new ArrayList<TextLine>();

    private TextLine current = null;
    
    private Font currentFont = new Font("Arial", Font.PLAIN, 12);
    
    private Color currentColor = Color.BLACK;

    public TextRenderer()
    {
    }

    public void setFont(final Font font)
    {
        this.currentFont = font;
    }
    
    public void setColor(final Color color)
    {
        this.currentColor = color;
    }
    
    public void addText(final String text)
    {
        final TextPart part = new TextPart(this.currentFont, this.currentColor, text);
        if (current == null)
        {
            current = new TextLine();
            lines.add(current);
        }
        current.add(part);
    }

    public void newLine()
    {
        current = new TextLine();
        lines.add(current);
    }

    public void render(final Graphics2D g, final double x, final double y)
    {
        double curY = y;
        for (final TextLine line: this.lines)
        {
            line.render(g, x, curY);
            curY += line.getBounds(g.getFontRenderContext()).getHeight();
        }
    }

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

    private class TextLine
    {
        private final List<TextPart> parts = new ArrayList<TextPart>();

        public void add(final TextPart part)
        {
            this.parts.add(part);
        }

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

        public void render(final Graphics2D g, final double x, final double y)
        {
            double curX = x;
            for (final TextPart part: this.parts)
            {
                part.render(g, curX, y);
                curX += part.getBounds(g.getFontRenderContext()).getWidth();
            }
        }
    }

    private class TextPart
    {
        private final Font font;
        
        private final Color color;

        private final String text;

        public TextPart(final Font font, final Color color, final String text)
        {
            this.font = font;
            this.text = text;
            this.color = color;
        }

        public Rectangle2D getBounds(final FontRenderContext context)
        {
            return this.font.getStringBounds(this.text, context);
        }
        
        public void render(final Graphics2D g, final double x, final double y)
        {
            final Rectangle2D bounds = getBounds(g.getFontRenderContext());
            g.setFont(this.font);
            g.setColor(this.color);
            g.drawString(this.text, (int) (x + bounds.getX()), (int) (y - bounds.getY())); 
        }
    }    
}
