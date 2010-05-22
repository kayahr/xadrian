/*
 * $Id$
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.components;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import de.ailis.xadrian.data.Factory;
import de.ailis.xadrian.data.Race;


/**
 * This cell renderer adds the race name to the factory name output if there
 * is no parent path which defines the race.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public class FactoryTreeCellRenderer extends DefaultTreeCellRenderer
{
    /** Serial version UID */
    private static final long serialVersionUID = 1667930854389987692L;

    
    /**
     * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree,
     *      java.lang.Object, boolean, boolean, boolean, int, boolean)
     */

    @Override
    public Component getTreeCellRendererComponent(final JTree tree,
        final Object value, final boolean sel, final boolean expanded,
        final boolean leaf, final int row, final boolean hasFocus)
    {
        final Component component = super.getTreeCellRendererComponent(tree,
            value, sel, expanded, leaf, row, hasFocus);
        TreePath path = tree.getPathForRow(row);
        if (value instanceof Factory)
        {
            while (path != null)
            {
                if (path.getLastPathComponent() instanceof Race)
                    return component;
                path = path.getParentPath();
            }
            setText(getText() + " (" + ((Factory) value).getRace().getName()
                + ")");
        }
        return component;
    }
}
