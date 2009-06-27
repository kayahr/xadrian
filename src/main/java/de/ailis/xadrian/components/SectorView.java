/*
 * $Id: SectorSelector.java 839 2009-03-22 14:21:30Z k $
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.components;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.event.EventListenerList;

import de.ailis.xadrian.actions.ChangeSectorAction;
import de.ailis.xadrian.actions.ResetSectorViewAction;
import de.ailis.xadrian.data.Asteroid;
import de.ailis.xadrian.data.Sector;
import de.ailis.xadrian.data.factories.SectorFactory;
import de.ailis.xadrian.interfaces.SectorProvider;
import de.ailis.xadrian.interfaces.StateProvider;
import de.ailis.xadrian.listeners.SectorListener;
import de.ailis.xadrian.listeners.StateListener;
import de.ailis.xadrian.support.TextRenderer;
import de.ailis.xadrian.utils.SwingUtils;


/**
 * Component which displays a sector.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision: 839 $
 */

public class SectorView extends JComponent implements SectorProvider,
    StateProvider
{
    /** Serial version UID */
    private static final long serialVersionUID = -1232035187510492730L;

    /** The event listener list */
    private final EventListenerList listenerList = new EventListenerList();

    /** The graphics buffer */
    private BufferedImage buffer;

    /** The scale factor of the map */
    private float scale;

    /** The map X offset */
    private final Point offset = new Point(0, 0);

    /** The maximum scale factor */
    private float maxScale;

    /** The minimum scale factor */
    private float minScale;

    /** The sector to display */
    private Sector sector;

    /** If sector should be displayed from the front */
    private boolean frontView = false;

    /** The asteroid the mouse is currently over */
    private Asteroid focusesAsteroid;


    /**
     * Constructor
     */

    public SectorView()
    {
        super();

        setMinimumSize(new Dimension(64, 64));
        setPreferredSize(new Dimension(512, 512));
        setFocusable(false);

        setupListeners();

        final JPopupMenu popupMenu = new JPopupMenu();

        final Action changeSectorAction =
            new ChangeSectorAction(this, "sector");
        popupMenu.add(changeSectorAction);
        SwingUtils.addComponentAction(this, changeSectorAction);

        final Action resetAction = new ResetSectorViewAction(this);
        popupMenu.add(resetAction);
        SwingUtils.addComponentAction(this, resetAction);

        SwingUtils.setPopupMenu(this, popupMenu);
    }


    /**
     * Setup event listeners.
     */

    private void setupListeners()
    {
        // Connect event listeners
        final MouseAdapter mouseAdapter = new MouseAdapter()
        {
            /** The drag point */
            private Point dragPoint;


            /**
             * Start drag operation by remembering the mouse position.
             */

            @Override
            public void mousePressed(final MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON1)
                    this.dragPoint = e.getPoint();
            }


            /**
             * Stops the dragging operation.
             */

            @Override
            public void mouseReleased(final MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON1)
                    this.dragPoint = null;
            }


            /**
             * Handles drag operations
             */

            @Override
            public void mouseDragged(final MouseEvent e)
            {
                // Handle drag operation.
                if (this.dragPoint != null)
                {
                    final float dx = e.getX() - this.dragPoint.x;
                    final float dy = e.getY() - this.dragPoint.y;
                    final float scale = getScale();
                    final Point offset = getOffset();
                    offset.translate((int) (dx / scale), (int) (dy / scale));
                    setOffset(offset);
                    this.dragPoint = e.getPoint();
                }
            }


            /**
             * Handles the mouse movement to highlight asteroids and setting the
             * mouse cursor.
             */

            @Override
            public void mouseMoved(final MouseEvent e)
            {
                final Sector sector = getSector();
                if (sector == null) return;

                final float scale = getScale();
                final Point offset = getOffset();
                final int mx =
                    Math.round((e.getX() - getWidth() / 2) / scale - offset.x);
                final int my =
                    Math
                        .round((e.getY() - getHeight() / 2) / scale - offset.y);
                final int w2 = Math.round(11f / scale / 2);
                final int h2 = Math.round(9f / scale / 2);
                // System.out.println(w2 + "x" + h2);

                Asteroid focused = null;
                for (final Asteroid asteroid: sector.getAsteroids())
                {
                    final int ax = asteroid.getX();
                    final int ay = isFrontView() ? -asteroid.getY() : -asteroid.getZ();
                    if (mx + w2 >= ax && mx - w2 <= ax && my + h2 >= ay
                        && my - h2 <= ay)
                    {
                        focused = asteroid;
                        break;
                    }
                }
                focusAsteroid(focused);
            }


            /**
             * Mouse wheel zooms the sector view.
             */

            @Override
            public void mouseWheelMoved(final MouseWheelEvent e)
            {
                if (e.getUnitsToScroll() < 0)
                    scale(1.1f);
                else
                    scale(1f / 1.1f);
            }


            /**
             * Double-clicking toggles the view between front view and top view.
             */

            @Override
            public void mouseClicked(final MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON1
                    && e.getClickCount() == 2) toggleView();
            }
        };
        addMouseWheelListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        addMouseListener(mouseAdapter);
        addComponentListener(new ComponentAdapter()
        {
            @Override
            public void componentResized(final ComponentEvent e)
            {
                reset();
            }
        });
    }

    /**
     * Returns the current scale factor.
     * 
     * @return The current scale factor
     */

    public float getScale()
    {
        return this.scale;
    }


    /**
     * Sets a new scale factor. It is automatically corrected to be in the range
     * between the calculated minimum and maximum scale factor.
     * 
     * @param scale
     *            The scale factor to set
     */

    public void setScale(final float scale)
    {
        this.scale = Math.max(this.minScale, Math.min(this.maxScale, scale));
        repaint();
    }


    /**
     * Multiplies the current scale factor with the specified factor. The
     * resulting scale factor is automatically corrected to be in the range
     * between the calculated minimum and maximum scale factor.
     * 
     * @param factor
     *            The factor to multiply the current scale factor with
     */

    public void scale(final float factor)
    {
        setScale(this.scale * factor);
    }


    /**
     * Returns the current offset.
     * 
     * @return The current offset
     */

    public Point getOffset()
    {
        return new Point(this.offset);
    }


    /**
     * Sets a new offset.
     * 
     * @param offset
     *            The offset to set
     */

    public void setOffset(final Point offset)
    {
        this.offset.setLocation(offset);
        this.repaint();
    }


    /**
     * Resets the sector view.
     */

    public void reset()
    {
        if (this.sector != null)
        {
            final Dimension viewSize = getSize();
            if (viewSize.width > 0 && viewSize.height > 0)
            {
                final int size = this.sector.getSize();
                this.scale =
                    (float) Math.min(viewSize.width, viewSize.height) / size;
                this.minScale = this.scale / 10;
                this.maxScale = this.scale * 20;
            }
        }
        this.offset.setLocation(0, 0);
        repaint();
    }


    /**
     * @see SectorProvider#canChangeSector()
     */

    @Override
    public boolean canChangeSector()
    {
        return true;
    }


    /**
     * @see SectorProvider#getSector()
     */

    @Override
    public Sector getSector()
    {
        return this.sector;
    }


    /**
     * @see SectorProvider#setSector(Sector)
     */

    @Override
    public void setSector(final Sector sector)
    {
        if (sector != this.sector)
        {
            this.sector = sector;
            reset();
            fireSectorChanged();
        }
    }


    /**
     * Toggles between top and front view.
     */

    public void toggleView()
    {
        this.frontView = !this.frontView;
        this.repaint();
    }


    /**
     * Checks if front view is currently selected.
     * 
     * @return True if front view, false if top view
     */

    public boolean isFrontView()
    {
        return this.frontView;
    }


    /**
     * Focuses a specific asteroid (or none if null is specified)
     * 
     * @param asteroid
     *            The asteroid to focus (null for none)
     */

    private void focusAsteroid(final Asteroid asteroid)
    {
        if (asteroid != this.focusesAsteroid)
        {
            this.focusesAsteroid = asteroid;
            System.out.println(asteroid);
            if (asteroid != null)
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            repaint();
        }
    }


    /**
     * Fires the sectorChanged event.
     */

    protected void fireSectorChanged()
    {
        final Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2)
            if (listeners[i] == SectorListener.class)
                ((SectorListener) listeners[i + 1]).sectorChanged(this.sector);
    }


    /**
     * Adds a new sector listener.
     * 
     * @param listener
     *            The listener to add
     */

    public void addSectorListener(final SectorListener listener)
    {
        this.listenerList.add(SectorListener.class, listener);
    }


    /**
     * Removes a sector listener.
     * 
     * @param listener
     *            The listener to remove
     */

    public void removeSectorListener(final StateListener listener)
    {
        this.listenerList.remove(StateListener.class, listener);
    }


    /**
     * Fires the stateChanged event.
     */

    protected void fireStateChanged()
    {
        final Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2)
            if (listeners[i] == StateListener.class)
                ((StateListener) listeners[i + 1]).stateChanged();
    }


    /**
     * @see StateProvider#addStateListener(StateListener)
     */

    @Override
    public void addStateListener(final StateListener listener)
    {
        this.listenerList.add(StateListener.class, listener);
    }


    /**
     * @see StateProvider#removeStateListener(StateListener)
     */

    @Override
    public void removeStateListener(final StateListener listener)
    {
        this.listenerList.remove(StateListener.class, listener);
    }


    /**
     * @see JComponent#paintComponent(Graphics)
     */

    @Override
    public void paintComponent(final Graphics graphics)
    {
        super.paintComponent(graphics);

        // If no sector is set yet then do nothing
        if (this.sector == null) return;

        final int width = getWidth();
        final int height = getHeight();

        if (this.buffer == null || this.buffer.getWidth() != width
            || this.buffer.getHeight() != height)
            this.buffer =
                new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        final Graphics2D g = this.buffer.createGraphics();

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setStroke(new BasicStroke(1.0f / this.scale));


        final int sizeX = (int) (width / this.scale);// this.sector.getSize();
        final int sizeY = (int) (height / this.scale);// this.sector.getSize();
        final AffineTransform oldTransform = g.getTransform();
        g.translate(width / 2, height / 2);
        g.scale(this.scale, this.scale);
        g.translate(this.offset.x, this.offset.y);

        // Find a good grid size
        int gridSize = 2500;
        while (gridSize * this.scale < 10)
            gridSize *= 2;

        // Draw the grid lines
        g.setColor(new Color(0x32, 0x2f, 0x4c));
        for (int i = -((sizeY / 2 + this.offset.y) / gridSize) * gridSize; i <= ((sizeY / 2 - this.offset.y) / gridSize)
            * gridSize; i += gridSize)
            g.drawLine(-sizeX / 2 - this.offset.x, i, sizeX / 2
                - this.offset.x, i);
        for (int i = -((sizeX / 2 + this.offset.x) / gridSize) * gridSize; i <= ((sizeX / 2 - this.offset.x) / gridSize)
            * gridSize; i += gridSize)
            g.drawLine(i, -sizeY / 2 - this.offset.y, i, sizeY / 2
                - this.offset.y);

        // Draw the 0 grid lines
        g.setColor(new Color(0x52, 0x4f, 0x6c));
        g
            .drawLine(-sizeX / 2 - this.offset.x, 0,
                sizeX / 2 - this.offset.x, 0);
        g
            .drawLine(0, -sizeY / 2 - this.offset.y, 0, sizeY / 2
                - this.offset.y);

        // Draw axis labels
        g.setColor(Color.LIGHT_GRAY);
        g.setFont(new Font("Arial", Font.BOLD, (int) (12 / this.scale)));
        g.drawString("-X", Math.min(-25 / this.scale, -sizeX / 2
            - this.offset.x + 5 / this.scale), 17 / this.scale);
        g.drawString("+X", Math.max(5 / this.scale, sizeX / 2 - this.offset.x
            - 25 / this.scale), -5 / this.scale);
        final char label = this.frontView ? 'Y' : 'Z';
        g.drawString("+" + label, 5 / this.scale, Math.min(-5 / this.scale,
            -sizeY / 2 - this.offset.y + 17 / this.scale));
        g.drawString("-" + label, -25 / this.scale, Math.max(17 / this.scale,
            sizeY / 2 - this.offset.y - 5 / this.scale));

        for (final Asteroid asteroid: this.sector.getAsteroids())
        {
            final int w = (int) (9f / this.scale);
            final int h = (int) (7f / this.scale);
            final int x = asteroid.getX() - w / 2;
            final int y =
                (this.frontView ? asteroid.getY() : asteroid.getZ()) + h / 2;

            g.setColor(new Color(0x7b, 0x91, 0xbd));
            g.fillOval(x, -y, w, h);
            g.setColor(Color.BLACK);
            g.drawOval(x, -y, w, h);
        }

        // Reset the original transformation
        g.setTransform(oldTransform);

        final TextRenderer text = new TextRenderer();
        text.setColor(Color.YELLOW);
        text.setFont(new Font("Arial", Font.PLAIN, 12));
        text.addText(String.format("Grid size: %.1f km",
            (float) gridSize / 1000));
        final Rectangle2D rect = text.getBounds(g.getFontRenderContext());
        text.render(g, width - rect.getWidth() - 5, height - rect.getHeight()
            - 5);

        graphics.drawImage(this.buffer, 0, 0, null);
    }


    /**
     * Tests the component.
     * 
     * @param args
     *            Command line arguments
     * @throws Exception
     */

    public static void main(final String[] args) throws Exception
    {
        SwingUtils.prepareGUI();

        final Sector sector = SectorFactory.getInstance().getSector(14, 7);
        final SectorView component = new SectorView();
        component.setSector(sector);
        SwingUtils.testComponent(component);
    }
}
