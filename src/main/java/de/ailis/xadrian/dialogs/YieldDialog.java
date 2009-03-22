/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
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

import de.ailis.xadrian.resources.Images;
import de.ailis.xadrian.support.I18N;
import de.ailis.xadrian.support.ModalDialog;
import de.ailis.xadrian.utils.SwingUtils;


/**
 * Dialog for selecting a yield.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class YieldDialog extends ModalDialog
{
    /** Serial version UID */
    private static final long serialVersionUID = 7923240074315652594L;

    /** The singleton instance of this dialog */
    private final static YieldDialog instance = new YieldDialog();

    /** The yield spinner */
    private JSpinner yieldSpinner;

    
    /**
     * Constructor
     */

    private YieldDialog()
    {
        super(Result.OK, Result.CANCEL);
    }


    /**
     * Creates the UI
     */

    @Override
    protected void createUI()
    {
        setTitle(I18N.getTitle("dialog.setYield"));
        setIconImages(Images.LOGOS);

        // Create the content controls
        final JLabel yieldLabel = new JLabel(I18N
            .getString("dialog.setYield.yield"));
        this.yieldSpinner = new JSpinner();
        final SpinnerNumberModel model = new SpinnerNumberModel();
        model.setMinimum(0);
        model.setMaximum(200);
        this.yieldSpinner.setModel(model);
        SwingUtils.installSpinnerBugWorkaround(this.yieldSpinner);
        yieldLabel.setLabelFor(this.yieldSpinner);

        // Create the content panel
        final JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        contentPanel.add(yieldLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        contentPanel.add(this.yieldSpinner);

        // Add the panels to the dialog
        add(contentPanel, BorderLayout.CENTER);
    }


    /**
     * Returns the singleton instance
     * 
     * @return The singleton instance
     */

    public static YieldDialog getInstance()
    {
        return instance;
    }


    /**
     * @see de.ailis.xadrian.support.ModalDialog#open()
     */

    @Override
    public Result open()
    {
        this.yieldSpinner.requestFocus();
        final Result result = super.open();
        return result;
    }


    /**
     * Sets the yield.
     * 
     * @param yield
     *            The yield to set
     */

    public void setYield(final int yield)
    {
        this.yieldSpinner.setValue(yield);
    }


    /**
     * Returns the yield.
     * 
     * @return The yield
     */

    public int getYield()
    {
        return (Integer) this.yieldSpinner.getValue();
    }
}
