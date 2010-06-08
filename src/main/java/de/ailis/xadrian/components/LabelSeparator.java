/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.xadrian.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSeparator;

import de.ailis.xadrian.utils.SwingUtils;


/**
 * A separator with a label
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class LabelSeparator extends JComponent
{
    /** Serial version UID */
    private static final long serialVersionUID = 1;

    /** The label */
    private final JLabel label;


    /**
     * Constructor
     *
     * @param text
     *            The text to use as separator label
     */

    public LabelSeparator(final String text)
    {
        setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        this.label = new JLabel(text);
        add(this.label, c);
        c.insets.left = 5;
        c.weightx = 1;
        c.gridx++;
        final JSeparator separator = new JSeparator();
        SwingUtils.setPreferredWidth(separator, 64);
        add(separator, c);
    }


    /**
     * Returns the label text.
     *
     * @return The label text
     */

    public String getText()
    {
        return this.label.getText();
    }


    /**
     * Sets the label text
     *
     * @param text
     *            The label text to set
     */

    public void setText(final String text)
    {
        this.label.setText(text);
    }



    /**
     * Tests the component.
     *
     * @param args
     *            Command line arguments
     * @throws Exception
     *            When something goes wrong
     */

    public static void main(final String[] args) throws Exception
    {
        SwingUtils.prepareGUI();

        final LabelSeparator component = new LabelSeparator("Used races");
        SwingUtils.testComponent(component);
    }
}
