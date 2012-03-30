/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */

package de.ailis.xadrian.asciitable;

/**
 * A ASCII table cell.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public final class TableCell
{
    /** The alignment type. */
    public static enum Align
    {
        /** Aligned to the left. */
        LEFT,
        
        /** Aligned to the right. */
        RIGHT
    }

    /** The cell value. */
    private final String value;

    /** The alignment. */
    private final Align align;

    /**
     * Constructs a new left-aligned table cell.
     * 
     * @param value
     *            The cell value. Must not be null.
     */
    public TableCell(String value)
    {
        this(value, Align.LEFT);
    }

    /**
     * Constructs a new table cell.
     * 
     * @param value
     *            The cell value. Must not be null.
     * @param align
     *            The alignment. Must not be null.
     */
    public TableCell(String value, Align align)
    {
        if (value == null)
            throw new IllegalArgumentException("value must be set");
        if (align == null)
            throw new IllegalArgumentException("align must be set");
        this.value = value;
        this.align = align;
    }

    /**
     * Returns the cell value.
     * 
     * @return The cell value. Never null.
     */
    public String getValue()
    {
        return this.value;
    }

    
    /**
     * Returns the cell wdith.
     * 
     * @return The cell width.
     */
    public int getWidth()
    {
        return getValue().length();
    }

    /**
     * Returns the alignment.
     * 
     * @return The alignment. Never null.
     */
    public Align getAlign()
    {
        return this.align;
    }
}
