/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */
package de.ailis.xadrian.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;

import de.ailis.xadrian.components.JLinkLabel;
import de.ailis.xadrian.frames.SplashFrame;
import de.ailis.xadrian.resources.Icons;
import de.ailis.xadrian.support.I18N;
import de.ailis.xadrian.support.ModalDialog;

/**
 * The about frame.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class AboutDialog extends ModalDialog
{
    /** Serial version UID */
    private static final long serialVersionUID = 4157034476842995945L;

    /** The singleton instance. */
    private static final AboutDialog instance = new AboutDialog();

    /**
     * Constructor
     */

    private AboutDialog()
    {
        init("about", Result.OK);
        SplashFrame.getInstance().advanceProgress();
    }

    /**
     * @see de.ailis.xadrian.support.ModalDialog#createUI()
     */

    @Override
    protected void createUI()
    {
        // Create the logo
        final JLabel logoLabel = new JLabel(Icons.LOGO);

        // Create the labels in the content panel
        final JLabel titleLabel = new JLabel(I18N.getString("title") + " "
            + I18N.getString("version"));
        titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        final JLabel copyrightLabel = new JLabel(I18N.getString("copyright") + ", ");
        final String email = I18N.getString("email");
        final JLabel emailLabel = new JLinkLabel(email, "mailto:" + email);
        final String homepage = I18N.getString("homepage");
        final JLabel homepageLabel = new JLinkLabel(homepage, homepage);

        // Create the license pane
        final JTextPane licenseTextPane = new JTextPane();
        licenseTextPane.setEditable(false);
        licenseTextPane.setBackground(Color.WHITE);
        licenseTextPane.setText(I18N.getString("license"));
        final JScrollPane licenseScrollPane = new JScrollPane(licenseTextPane);
        licenseScrollPane.setAlignmentX(LEFT_ALIGNMENT);
        licenseScrollPane.setBorder(BorderFactory
            .createBevelBorder(BevelBorder.LOWERED));
        licenseScrollPane.setPreferredSize(new Dimension(300, 150));

        // Create the content panel
        final JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridheight = 3;
        c.anchor = GridBagConstraints.WEST;
        c.insets.right = 10;
        contentPanel.add(logoLabel, c);
        c.insets.right = 0;
        c.gridheight = 1;
        c.gridx = 1;
        c.gridy = 0;
        c.weighty = 1;
        contentPanel.add(titleLabel, c);
        c.gridy = 1;
        contentPanel.add(copyrightLabel, c);
        c.gridx = 2;
        contentPanel.add(emailLabel, c);
        c.gridy = 2;
        c.gridx = 1;
        c.gridwidth = 2;
        contentPanel.add(homepageLabel, c);
        c.weighty = 0;
        c.gridy = 3;
        c.gridx = 0;
        c.insets.top = 10;
        c.gridwidth = 3;
        contentPanel.add(licenseScrollPane, c);

        add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * Returns the singleton instance.
     *
     * @return The singleton instance.
     */
    public static AboutDialog getInstance()
    {
        return instance;
    }
}
