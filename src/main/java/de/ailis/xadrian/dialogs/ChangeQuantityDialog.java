/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import de.ailis.xadrian.frames.SplashFrame;
import de.ailis.xadrian.support.I18N;
import de.ailis.xadrian.support.ModalDialog;
import de.ailis.xadrian.utils.SwingUtils;


/**
 * Dialog for selecting a quantity.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class ChangeQuantityDialog extends ModalDialog
{
    /** Serial version UID */
    private static final long serialVersionUID = 4157034476842995945L;

    /** The singleton instance of this dialog */
    private final static ChangeQuantityDialog instance = new ChangeQuantityDialog();

    /** The quantity spinner */
    private JSpinner quantitySpinner;


    /**
     * Constructor
     */

    private ChangeQuantityDialog()
    {
        init("changeQuantity", Result.OK, Result.CANCEL);
        SplashFrame.getInstance().advanceProgress();
    }


    /**
     * Creates the UI
     */

    @Override
    protected void createUI()
    {
        // Create the content controls
        final JLabel quantityLabel = new JLabel(I18N
            .getString("dialog.changeQuantity.quantity"));
        this.quantitySpinner = new JSpinner();
        final SpinnerNumberModel model = new SpinnerNumberModel();
        model.setMinimum(0);
        model.setMaximum(999);
        this.quantitySpinner.setModel(model);
        SwingUtils.installSpinnerBugWorkaround(this.quantitySpinner);
        quantityLabel.setLabelFor(this.quantitySpinner);

        // Create the content panel
        final JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        contentPanel.add(quantityLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        contentPanel.add(this.quantitySpinner);

        // Add the panels to the dialog
        add(contentPanel, BorderLayout.CENTER);
    }


    /**
     * Returns the singleton instance
     *
     * @return The singleton instance
     */

    public static ChangeQuantityDialog getInstance()
    {
        return instance;
    }


    /**
     * @see de.ailis.xadrian.support.ModalDialog#open()
     */

    @Override
    public Result open()
    {
        this.quantitySpinner.requestFocus();
        final Result result = super.open();
        return result;
    }


    /**
     * Sets the quantity.
     *
     * @param quantity
     *            The quantity to set
     */

    public void setQuantity(final int quantity)
    {
        this.quantitySpinner.setValue(quantity);
    }


    /**
     * Returns the quantity.
     *
     * @return The quantity
     */

    public int getQuantity()
    {
        return (Integer) this.quantitySpinner.getValue();
    }
    
    /**
     * Test main method.
     * 
     * @param args
     *            Command line arguments
     */
    public static void main(String[] args)
    {
        new ChangeQuantityDialog().open();
    }
}
