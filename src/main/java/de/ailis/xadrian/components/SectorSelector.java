/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */
package de.ailis.xadrian.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Locale;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;

import javax.swing.JComponent;
import javax.swing.UIManager;

import de.ailis.xadrian.data.Game;
import de.ailis.xadrian.data.Sector;
import de.ailis.xadrian.data.factories.GameFactory;
import de.ailis.xadrian.data.factories.SectorFactory;
import de.ailis.xadrian.listeners.SectorSelectorStateListener;
import de.ailis.xadrian.support.I18N;
import de.ailis.xadrian.support.TextRenderer;
import de.ailis.xadrian.utils.SwingUtils;

/**
 * Component which displays the sectors in a graphical way and let the user
 * select one.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class SectorSelector extends JComponent implements MouseMotionListener,
    MouseListener
{
    /**
     * View mode
     */
    public enum Mode
    {
        /** Political view mode */
        POLITICAL,

        /** Sun view mode */
        SUNS,

        /** Silicon view mode */
        SILICON,

        /** Ore view mode */
        ORE,

        /** Nividium view mode */
        NIVIDIUM,

        /** Ice view mode */
        ICE;

        /**
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString()
        {
            return I18N.getString("component.sectorSelector.viewMode."
                + name().toLowerCase(Locale.getDefault()));
        }
    }

    /** Serial version UID */
    private static final long serialVersionUID = 42133575643122689L;

    /** The graphics buffer */
    private transient BufferedImage buffer;

    /** The scale factor of the map */
    private float scale;

    /** The game. */
    private final Game game;

    /** The sector over which the mouse cursor hovers */
    private Sector overSector;

    /** The selected sector */
    private Sector selectedSector;

    /** The filter string */
    private String filter;

    /** The view mode */
    private Mode mode = Mode.POLITICAL;

    /**
     * Constructor
     *
     * @param game
     *            The game.
     */
    public SectorSelector(final Game game)
    {
        this.game = game;
        setPreferredSize(new Dimension(650, 512));
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    /**
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    public void paintComponent(final Graphics graphics)
    {
        super.paintComponent(graphics);
        
        final int maxW = getWidth();
        final int maxH = getHeight();
        final SectorFactory sectorFactory = this.game.getSectorFactory();
        final int uniWidth = sectorFactory.getMaxX() * 100 + 150;
        final int uniHeight = sectorFactory.getMaxY() * 100 + 150;
        final float scaleX = (float) maxW / uniWidth;
        final float scaleY = (float) maxH / uniHeight;
        final float uniAR = (float) uniWidth / uniHeight;
        final float winAR = maxW / (float) maxH;
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
        
        if (width <= 0 || height <= 0) return;

        if (this.buffer == null || this.buffer.getWidth() != width
            || this.buffer.getHeight() != height)
            this.buffer = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = this.buffer.createGraphics();

        final SortedSet<Sector> sectors = sectorFactory.getSectors();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setStroke(new BasicStroke(1.0f / this.scale));

        g.scale(this.scale, this.scale);
        g.translate(75, 75);
        for (final Sector sector : sectors)
        {
            final int sx = sector.getX();
            final int sy = sector.getY();

            Color sectorColor;
            Color borderColor = new Color(0x606060);
            switch (this.mode)
            {
                case SUNS:
                    sectorColor = sector.getSuns().getColor();
                    break;

                case SILICON:
                    sectorColor = sector.getSiliconColor();
                    break;

                case ORE:
                    sectorColor = sector.getOreColor();
                    break;

                case NIVIDIUM:
                    sectorColor = sector.getNividiumColor();
                    break;

                case ICE:
                    sectorColor = sector.getIceColor();
                    break;

                default:
                    sectorColor = sector.getRace().getColor();
            }

            if (this.filter != null && !this.filter.isEmpty())
            {
                if (sector.getName().toLowerCase(Locale.getDefault()).contains(
                    this.filter.toLowerCase(Locale.getDefault())))
                    borderColor = Color.GREEN;
                else
                    sectorColor = getAlphaColor(sectorColor, 32);
            }

            g.setColor(sectorColor);
            g.fillRect(sx * 100 - 40, sy * 100 - 40, 80, 80);

            g.setColor(new Color(0x606060));
            if (sector.getSouth() != null)
                g.fillRect(sx * 100 - 20, sy * 100 + 40, 40, 11);
            if (sector.getNorth() != null)
                g.fillRect(sx * 100 - 20, sy * 100 - 51, 40, 11);
            if (sector.getWest() != null)
                g.fillRect(sx * 100 - 51, sy * 100 - 20, 11, 40);
            if (sector.getEast() != null)
                g.fillRect(sx * 100 + 40, sy * 100 - 20, 11, 40);

            g.setColor(borderColor);
            g.drawRect(sx * 100 - 40, sy * 100 - 40, 80, 80);
        }

        if (this.selectedSector != null)
        {
            final int sx = this.selectedSector.getX();
            final int sy = this.selectedSector.getY();
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(5.0f / this.scale));
            g.drawRoundRect(sx * 100 - 60, sy * 100 - 60, 120, 120, 20, 20);
            g.setColor(Color.GREEN);
            g.setStroke(new BasicStroke(3.0f / this.scale));
            g.drawRoundRect(sx * 100 - 60, sy * 100 - 60, 120, 120, 20, 20);
        }

        // Emphasize the hovered sector
        if (this.overSector != null)
        {
            final int sx = this.overSector.getX();
            final int sy = this.overSector.getY();
            if (!this.overSector.equals(this.selectedSector))
            {
                g.setColor(Color.BLACK);
                g.setStroke(new BasicStroke(5.0f / this.scale));
                g.drawRoundRect(sx * 100 - 60, sy * 100 - 60, 120, 120, 20, 20);
                g.setColor(Color.RED);
                g.setStroke(new BasicStroke(3.0f / this.scale));
                g.drawRoundRect(sx * 100 - 60, sy * 100 - 60, 120, 120, 20, 20);
            }

            // Create the sector info text
            final Color titleColor = new Color(0x40, 0x40, 0x40);
            final Color detailColor = new Color(0x50, 0x50, 0x50);
            final TextRenderer textRenderer = new TextRenderer();
            Font font = UIManager.getFont("Label.font");
            if (font == null) font = new Font("Arial", Font.PLAIN, 12);
            textRenderer.setFont(font.deriveFont(Font.BOLD, font.getSize2D() * 1.2f / this.scale));
            textRenderer.setColor(titleColor);
            textRenderer.addText(this.overSector.getName());
            textRenderer.newLine();
            textRenderer.setFont(font.deriveFont(Font.PLAIN, font.getSize2D() / this.scale));
            textRenderer.setColor(detailColor);
            textRenderer.addText(String.format("%s: %d; %d", I18N
                .getString("component.sectorSelector.location"), this.overSector.getX(),
                this.overSector.getY()));
            textRenderer.newLine();
            textRenderer.addText(String.format("%s: %s", I18N
                .getString("component.sectorSelector.race"), this.overSector.getRace()
                .toString()));
            textRenderer.newLine();
            textRenderer.addText(String.format("%s: %s", I18N
                .getString("component.sectorSelector.suns"), this.overSector.getSuns()
                .toString()));

            addYieldInfo(textRenderer, this.overSector, "siliconWafers");
            addYieldInfo(textRenderer, this.overSector, "ore");
            addYieldInfo(textRenderer, this.overSector, "nividium");
            addYieldInfo(textRenderer, this.overSector, "ice");

            // Position the sector info text
            final Rectangle2D bounds = textRenderer.getBounds(g
                .getFontRenderContext());
            final int borderX = (int) (10 / this.scale);
            final int borderY = (int) (5 / this.scale);
            final int infoWidth = (int) (bounds.getWidth() + borderX * 2);
            final int infoHeight = (int) (bounds.getHeight() + borderY * 2);
            int infoLeft = sx * 100 - infoWidth / 2;
            int infoTop = sy * 100 + 100;
            if (infoTop + infoHeight > height / this.scale - 100)
                infoTop = sy * 100 - 100 - infoHeight;
            if (infoLeft < -50) infoLeft = -50;
            if (infoLeft + infoWidth > width / this.scale - 100)
                infoLeft = (int) (width / this.scale) - 100 - infoWidth;

            // Render the sector info text
            g.setColor(new Color(255, 255, 255, 200));
            g.fillRoundRect(infoLeft, infoTop, infoWidth, infoHeight, 30, 30);
            textRenderer.render(g, infoLeft + borderX, infoTop + borderY);
        }

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.drawImage(this.buffer, (getWidth() - width) / 2, 
            (getHeight() - height) / 2, null);
    }

    /**
     * Outputs the yield info.
     *
     * @param renderer
     *            The text renderer
     * @param sector
     *            The sector
     * @param wareId
     *            The id of the asteroid ware
     */
    private void addYieldInfo(final TextRenderer renderer, final Sector sector,
        final String wareId)
    {
        final SortedMap<Integer, Integer> yields = sector.getYieldsMap(wareId);

        // If yield map is empty then do nothing
        if (yields.isEmpty()) return;

        // Output the title
        renderer.newLine();
        renderer.addText(I18N.getString("component.sectorSelector." + wareId) + ": ");
        renderer.newLine();

        // Output the yields
        String text = "    ";
        for (final Map.Entry<Integer, Integer> entry : yields.entrySet())
        {
            final int yield = entry.getKey();
            final int quantity = entry.getValue();
            if (text.length() > 40)
            {
                renderer.addText(text);
                renderer.newLine();
                text = "    ";

            }
            if (quantity > 1)
                text = text + String.format("%dx%d, ", quantity, yield);
            else
                text = text + yield + ", ";
        }
        renderer.addText(text.substring(0, text.length() - 2));
    }

    /**
     * @see java.awt.event.MouseMotionListener#mouseDragged(MouseEvent)
     */
    @Override
    public void mouseDragged(final MouseEvent e)
    {
        // Ignored
    }

    /**
     * @see java.awt.event.MouseMotionListener#mouseMoved(MouseEvent)
     */
    @Override
    public void mouseMoved(final MouseEvent e)
    {
        if (this.buffer == null) return;
        
        final int x = e.getX() - (this.getWidth() - this.buffer.getWidth()) / 2;
        final int y = e.getY() - (this.getHeight() - this.buffer.getHeight()) / 2;
        final int sx = Math.round(((x / this.scale) - 75) / 100);
        final int sy = Math.round(((y / this.scale) - 75) / 100);

        final Sector sector = this.game.getSectorFactory().getSector(sx, sy);
        if (sector != this.overSector)
        {
            this.overSector = sector;
            repaint();
        }
    }

    /**
     * @see MouseListener#mouseClicked(MouseEvent)
     */
    @Override
    public void mouseClicked(final MouseEvent e)
    {
        // Not used
    }

    /**
     * @see MouseListener#mouseEntered(MouseEvent)
     */
    @Override
    public void mouseEntered(final MouseEvent e)
    {
        // Not used
    }

    /**
     * @see MouseListener#mouseExited(MouseEvent)
     */
    @Override
    public void mouseExited(final MouseEvent e)
    {
        // Not used
    }

    /**
     * @see MouseListener#mousePressed(MouseEvent)
     */
    @Override
    public void mousePressed(final MouseEvent e)
    {
        setSelected(this.overSector);
    }

    /**
     * @see MouseListener#mouseReleased(MouseEvent)
     */
    @Override
    public void mouseReleased(final MouseEvent e)
    {
        // Not used
    }

    /**
     * Sets the filter.
     *
     * @param filter
     *            The filter to set
     */
    public void setFilter(final String filter)
    {
        this.filter = filter;
        repaint();
        fireSectorSelectorState();
    }

    /**
     * Adds alpha to the specified color.
     *
     * @param color
     *            The original color
     * @param alpha
     *            The alpha value
     * @return The color with added alpha
     */
    private Color getAlphaColor(final Color color, final int alpha)
    {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(),
            alpha);
    }

    /**
     * Sets the view mode.
     *
     * @param mode
     *            The view mode to set.
     */
    public void setMode(final Mode mode)
    {
        this.mode = mode;
        repaint();
        fireSectorSelectorState();
    }

    /**
     * Returns the current view mode.
     *
     * @return The view mode
     */
    public Mode getMode()
    {
        return this.mode;
    }

    /**
     * Returns the selected sector.
     *
     * @return The selected sector
     */
    public Sector getSelected()
    {
        return this.selectedSector;
    }

    /**
     * Sets the selected sector. null deselectes the currently selected sector.
     *
     * @param sector
     *            The sector to select
     */
    public void setSelected(final Sector sector)
    {
        if ((sector != null && !sector.equals(this.selectedSector))
            || (sector == null && this.selectedSector != null))
        {
            this.selectedSector = sector;
            repaint();
            fireSectorSelectorState();
        }
    }

    /**
     * Adds a sector selector state listener.
     *
     * @param listener
     *            The listener to add
     */
    public void addSectorSelectorStateListener(
        final SectorSelectorStateListener listener)
    {
        this.listenerList.add(SectorSelectorStateListener.class, listener);
    }

    /**
     * Removes a sector selector state listener.
     *
     * @param listener
     *            The listener to remove
     */
    public void removeSectorSelectorStateListener(
        final SectorSelectorStateListener listener)
    {
        this.listenerList.remove(SectorSelectorStateListener.class, listener);
    }

    /**
     * Fire the sector selector state event.
     */
    private void fireSectorSelectorState()
    {
        final Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2)
            if (listeners[i] == SectorSelectorStateListener.class)
                ((SectorSelectorStateListener) listeners[i + 1])
                    .sectorSelectorChanged(this);
    }

    /**
     * Tests the component.
     *
     * @param args
     *            Command line arguments
     * @throws Exception
     *             When something goes wrong
     */
    public static void main(final String[] args) throws Exception
    {
        SwingUtils.prepareGUI();

        Game game = GameFactory.getInstance().getGame("x3tc");
        final SectorSelector component = new SectorSelector(game);
        SwingUtils.testComponent(component);
    }
}
