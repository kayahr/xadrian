/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */

package de.ailis.xadrian.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.SortedSet;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.ailis.xadrian.data.Game;
import de.ailis.xadrian.data.factories.GameFactory;
import de.ailis.xadrian.frames.SplashFrame;
import de.ailis.xadrian.support.I18N;
import de.ailis.xadrian.support.ModalDialog;
import de.ailis.xadrian.utils.SwingUtils;

/**
 * Dialog for selecting the game.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class SelectGameDialog extends ModalDialog
{
    /** Serial version UID */
    private static final long serialVersionUID = 1L;

    /** The game combo box */
    private JComboBox gameComboBox;

    /** The remember checkbox. */
    private JCheckBox rememberCheckBox;

    /** The singleton instance. */
    private static final SelectGameDialog instance = new SelectGameDialog();

    /**
     * Constructor.
     */
    private SelectGameDialog()
    {
        init("selectGame", Result.OK, Result.CANCEL);
        SplashFrame.advanceProgress();
    }

    /**
     * Returns the singleton instance.
     *
     * @return The singleton instance.
     */
    public static SelectGameDialog getInstance()
    {
        return instance;
    }

    /**
     * Creates the UI
     */
    @Override
    protected void createUI()
    {
        // Create the content controls
        final JLabel gameLabel = new JLabel(I18N
            .getString("dialog.selectGame.game"));
        final SortedSet<Game> games = GameFactory.getInstance().getGames();
        this.gameComboBox =
            new JComboBox(games.toArray(new Game[games.size()]));
        this.gameComboBox.setSelectedItem(GameFactory.getInstance()
            .getDefaultGame());
        gameLabel.setLabelFor(this.gameComboBox);

        this.rememberCheckBox = new JCheckBox(I18N
            .getString("dialog.selectGame.remember"));
        this.rememberCheckBox.setIconTextGap(5);

        // Create the content panel
        final JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setLayout(new GridBagLayout());
        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        contentPanel.add(gameLabel, c);
        c.gridx = 1;
        c.insets.left = 10;
        contentPanel.add(this.gameComboBox, c);
        c.insets.left = 0;
        c.insets.top = 10;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        contentPanel.add(this.rememberCheckBox, c);

        // Add the panels to the dialog
        add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * @see de.ailis.xadrian.support.ModalDialog#open()
     */
    @Override
    public Result open()
    {
        this.rememberCheckBox.setSelected(false);
        this.gameComboBox.requestFocus();
        final Result result = super.open();
        return result;
    }

    /**
     * Returns the selected game.
     *
     * @return The selected game
     */
    public Game getGame()
    {
        return (Game) this.gameComboBox.getSelectedItem();
    }

    /**
     * Checks if the remember checkbox is selected.
     *
     * @return True if checkbox is selected, false if not.
     */
    public boolean isRemember()
    {
        return this.rememberCheckBox.isSelected();
    }

    /**
     * Test main method.
     *
     * @param args
     *            Command line arguments
     */
    public static void main(final String[] args)
    {
        SwingUtils.prepareTheme();
        new SelectGameDialog().open();
        System.exit(0);
    }
}
