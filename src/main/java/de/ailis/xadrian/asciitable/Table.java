/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */

package de.ailis.xadrian.asciitable;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

import de.ailis.xadrian.asciitable.TableCell.Align;

/**
 * A ASCII table.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public final class Table
{
    /** The table cells. */
    private final TableCell[][] cells;

    /** The rows which must be separated from the next rows. */
    private final Set<Integer> separators = new HashSet<Integer>();

    /**
     * Constructor.
     * 
     * @param rows
     *            The number of rows.
     * @param columns
     *            The number of columns.
     */
    public Table(int rows, int columns)
    {
        if (columns < 1)
            throw new IllegalArgumentException("At least 1 column must exist");
        if (rows < 1)
            throw new IllegalArgumentException("At least 1 rows must exist");
        this.cells = new TableCell[rows][columns];
    }

    /**
     * Adds a separator line between the specified row and the following one.
     * 
     * @param row
     *            The row index. Specify -1 to add a separator at the beginning
     *            of the table.
     */
    public void addSeparator(int row)
    {
        this.separators.add(row);
    }

    /**
     * Returns the number of columns.
     * 
     * @return the number of columns.
     */
    public int getColumns()
    {
        return this.cells[0].length;
    }

    /**
     * Returns the number of rows.
     * 
     * @return The number of rows.
     */
    public int getRows()
    {
        return this.cells.length;
    }

    /**
     * Sets a cell.
     * 
     * @param row
     *            The table row index.
     * @param column
     *            The table column index.
     * @param cell
     *            The cell to set. Null to unset.
     * 
     */
    public void setCell(int row, int column, TableCell cell)
    {
        this.cells[row][column] = cell;
    }

    /**
     * Returns the width of the specified column.
     * 
     * @param column
     *            The column index.
     * @return The column width.
     */
    public int getColumnWidth(int column)
    {
        int width = 0;
        for (TableCell[] cells : this.cells)
        {
            TableCell cell = cells[column];
            if (cell == null) continue;
            width = Math.max(width, cell.getWidth());
        }
        return width;
    }

    /**
     * Prints a line with the specified length.
     * 
     * @param out
     *            The print writer to print the line to.
     * @param length
     *            The length of the line.
     */
    private void printLine(PrintWriter out, int length)
    {
        for (int i = length; i > 0; i -= 1)
            out.append('-');
    }

    /**
     * Prints a row separator.
     * 
     * @param out
     *            The print writer to print the row separator to.
     */
    private void printRowSeparator(PrintWriter out)
    {
        for (int col = 0, max = getColumns(); col < max; col += 1)
        {
            if (col > 0) out.print("+");
            boolean edge = col == 0 || col == max - 1;
            printLine(out, getColumnWidth(col) + (edge ? 1 : 2));
        }
        out.println();
    }

    /**
     * Prints a cell.
     * 
     * @param out
     *            The print writer to print the cell to.
     * @param column
     *            The column index.
     * @param cell
     *            The cell to print. Null if empty cell.
     */
    private void printCell(PrintWriter out, int column, TableCell cell)
    {
        String value = cell == null ? "" : cell.getValue();
        Align align = cell == null ? Align.LEFT : cell.getAlign();
        if (align == Align.LEFT) out.print(value);
        for (int i = getColumnWidth(column) - value.length(); i > 0; i -= 1)
            out.append(' ');
        if (align == Align.RIGHT) out.print(value);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringWriter writer = new StringWriter();
        PrintWriter out = new PrintWriter(writer);
        int row = 0;
        if (this.separators.contains(-1)) printRowSeparator(out);
        for (TableCell[] cells : this.cells)
        {
            int column = 0;
            for (TableCell cell : cells)
            {
                if (column > 0)
                    out.print(" | ");
                printCell(out, column, cell);
                column += 1;
            }
            out.println();
            if (this.separators.contains(row)) printRowSeparator(out);
            row += 1;
        }
        return writer.toString();
    }

    /**
     * Demo main method.
     * 
     * @param args
     *            Command-line arguments (Ignored).
     */
    public static void main(String[] args)
    {
        Table table = new Table(3, 3);
        table.setCell(0, 0, new TableCell("First name"));
        table.setCell(0, 1, new TableCell("Last name"));
        table.setCell(0, 2, new TableCell("Exp", Align.RIGHT));
        table.setCell(1, 0, new TableCell("Faran"));
        table.setCell(1, 1, new TableCell("Brygo"));
        table.setCell(1, 2, new TableCell("133919", Align.RIGHT));
        table.setCell(2, 0, new TableCell("Freddy"));
        table.setCell(2, 1, new TableCell("Fat"));
        table.setCell(2, 2, new TableCell("121", Align.RIGHT));
        table.addSeparator(0);
        System.out.println(table.toString());
    }
}
