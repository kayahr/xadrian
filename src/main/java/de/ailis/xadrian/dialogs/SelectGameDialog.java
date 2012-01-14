/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.ailis.xadrian.data.Game;
import de.ailis.xadrian.data.factories.GameFactory;
import de.ailis.xadrian.frames.SplashFrame;
import de.ailis.xadrian.support.I18N;
import de.ailis.xadrian.support.ModalDialog;

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
    
    /** The singleton instance. */
    private static final SelectGameDialog instance = new SelectGameDialog();

    /**
     * Constructor.
     */
    private SelectGameDialog()
    {
        init("selectGame", Result.OK, Result.CANCEL);
        SplashFrame.getInstance().advanceProgress();
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
        this.gameComboBox =
            new JComboBox(GameFactory.getInstance().getGames()
                .toArray(new Game[0]));
        gameLabel.setLabelFor(this.gameComboBox);

        // Create the content panel
        final JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        contentPanel.add(gameLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        contentPanel.add(this.gameComboBox);

        // Add the panels to the dialog
        add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * @see de.ailis.xadrian.support.ModalDialog#open()
     */
    @Override
    public Result open()
    {
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
     * Test main method.
     * 
     * @param args
     *            Command line arguments
     */
    public static void main(String[] args)
    {
        new SelectGameDialog().open();
        System.exit(0);
    }
}
