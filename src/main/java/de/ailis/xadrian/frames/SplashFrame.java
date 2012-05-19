/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */
package de.ailis.xadrian.frames;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import de.ailis.xadrian.components.JLinkLabel;
import de.ailis.xadrian.resources.Icons;
import de.ailis.xadrian.resources.Images;
import de.ailis.xadrian.support.I18N;

/**
 * The splash frame.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class SplashFrame extends JFrame
{
    /** Serial version UID. */
    private static final long serialVersionUID = -1;

    /** The singleton instance. */
    private static SplashFrame instance;

    /** The progress bar. */
    private JProgressBar progressBar;

    /**
     * Constructor
     */
    private SplashFrame()
    {
        setTitle(I18N.getString("title"));
        setName("splashFrame");
        setIconImages(Images.LOGOS);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);

        // Create the logo
        final JLabel logoLabel = new JLabel(Icons.LOGO_LARGE);

        // Create the labels in the content panel
        final JLabel titleLabel = new JLabel(I18N.getString("title") + " "
            + I18N.getString("version"));
        titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        final JLabel copyrightLabel =
            new JLabel(I18N.getString("copyright") + ", ");
        final String email = I18N.getString("email");
        final JLabel emailLabel = new JLinkLabel(email, "mailto:" + email);
        final String homepage = I18N.getString("homepage");
        final JLabel homepageLabel = new JLinkLabel(homepage, homepage);

        // Create the progress bar.
        this.progressBar = new JProgressBar(SwingConstants.HORIZONTAL);

        // Create the content panel
        final JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createBevelBorder(BevelBorder.RAISED),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
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
        contentPanel.add(this.progressBar, c);

        add(contentPanel, BorderLayout.CENTER);

        pack();

        // Center the frame
        setLocationRelativeTo(null);
    }

    /**
     * Returns the singleton instance.
     *
     * @return The singleton instance.
     */
    public static SplashFrame getInstance()
    {
        if (instance == null) instance = new SplashFrame();
        return instance;
    }

    /**
     * @see java.awt.Window#setVisible(boolean)
     */
    @Override
    public void setVisible(boolean visible)
    {
        super.setVisible(visible);
        int value = this.progressBar.getValue();
        int maximum = this.progressBar.getMaximum();
        if (visible == false && value != maximum)
            System.err.println("Last progress (" + value +
                ") is NOT the max progress (" + maximum + ")");
    }

    /**
     * Sets the number of games. This is needed to correctly calculate the
     * maximum loading progress.
     *
     * @param numberOfGames
     *            The number of games.
     */
    public static void setNumberOfGames(int numberOfGames)
    {
        if (instance == null) return;
        instance.progressBar.setMaximum(numberOfGames * 10 + 6);
    }
    
    /**
     * Opens the splash screen.
     */
    public static void open()
    {
        final SplashFrame instance = getInstance();
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                instance.setVisible(true);
            }
        });
    }
        
    /**
     * Closes the splash screen.
     */
    public static void close()
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {              
                getInstance().setVisible(false);
            }
        });
    }

    /**
     * Advances the progress by one step.
     */
    public static void advanceProgress()
    {
        if (instance == null) return;
        final JProgressBar progressBar = instance.progressBar;
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                progressBar.setValue(progressBar.getValue() + 1);
            }
        });
    }
}
