/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.support;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import de.ailis.xadrian.resources.Images;


/**
 * Base class for modal dialogs
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public abstract class ModalDialog extends JDialog
{
    /** Serial version UID */
    private static final long serialVersionUID = -7549471473035295150L;


    /**
     * The modal result enum.
     */

    public enum Result
    {
        /** The OK modal result */
        OK,

        /** The CANCEL modal result */
        CANCEL,

        /** The YES modal result */
        YES,

        /** The NO modal result */
        NO
    }

    /** Listens for closing the window and cancels the dialog. */
    private final WindowAdapter windowListener = new WindowAdapter()
    {
        /** @see WindowAdapter#windowClosing(WindowEvent) */
        @Override
        public void windowClosing(final WindowEvent e)
        {
            cancel();
        }
    };

    /** Listens for escape key press and cancels the dialog */
    private final ActionListener escKeyListener = new ActionListener()
    {
        /** @see ActionListener#actionPerformed(ActionEvent) */
        @Override
        public void actionPerformed(final ActionEvent e)
        {
            cancel();
        }
    };

    /** The dialog actions */
    private final Map<Result, Action> actions = new HashMap<Result, Action>();

    /** The dialog buttons */
    private final Map<Result, JButton> buttons =
        new HashMap<Result, JButton>();

    /**
     * List with dialog actions which are displayed as buttons in the button
     * panel
     */
    private List<Action> dialogActions;

    /** The cancel button */
    private JButton cancelButton;

    /** The modal result */
    private Result result;


    /**
     * Constructor
     * 
     * @param id
     *            The dialog id. The component name and the title is
     *            automatically derived from it.
     * @param results
     *            The available results to create
     */

    public void init(final String id, final Result... results)
    {
        // Initialize the non-visual parts of the dialog
        init();

        setTitle(I18N.getTitle("dialog." + id));
        setName(id + "Dialog");
        setIconImages(Images.LOGOS);

        // Modal dialogs should not be resizable
        setResizable(false);

        // Do no auto-close. We close it ourself
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // Modal dialogs should be modal at application level
        setModalityType(ModalityType.APPLICATION_MODAL);

        // Closing the modal dialog with the X button must cancel the dialog
        addWindowListener(this.windowListener);

        // Pressing the escape button in a modal dialog must cancel the dialog
        getRootPane().registerKeyboardAction(this.escKeyListener,
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW);

        // Create the dialog actions
        this.dialogActions = createDialogActions();

        // Create the default UI
        createDefaultUI(results);

        // Determine the default buttons (for enter and escape)
        setDefaultButtons();

        // Create the custom UO
        createUI();

        // Pack the dialog
        pack();

        // Focus the default button
        getRootPane().getDefaultButton().requestFocus();

        // Center the dialog
        setLocationRelativeTo(null);
    }
    
    
    /**
     * Initializes the non-visual parts of the dialog. This is the first
     * method called by the constructor so this is a good place to initialized
     * models and stuff like that.
     */
    
    protected void init()
    {
        // Empty
    }


    /**
     * Create the dialog actions which are displayed in form of buttons in the
     * button panel (left side). Returning null means using no dialog actions.
     * 
     * @return The list of dialog actions or null for none.
     */

    protected List<Action> createDialogActions()
    {
        return null;
    }


    /**
     * Sets the default buttons (for Enter and Escape).
     */

    private void setDefaultButtons()
    {
        Result cancelResult = null;
        Result defaultResult = null;
        for (final Result result: this.buttons.keySet())
        {
            if (cancelResult == null || result == Result.CANCEL
                || result == Result.NO) cancelResult = result;
            if (defaultResult == null || result == Result.OK
                || result == Result.YES) defaultResult = result;
        }
        getRootPane().setDefaultButton(this.buttons.get(defaultResult));
        this.cancelButton = this.buttons.get(cancelResult);
    }


    /**
     * Create the default UI elements of a modal dialog.
     * 
     * @param results
     *            The available results to create
     */

    private void createDefaultUI(final Result[] results)
    {
        boolean first;

        // Create the button panel
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        first = true;
        if (this.dialogActions != null)
        {
            for (final Action action: this.dialogActions)
            {
                if (first)
                    first = false;
                else
                    buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
                final JButton button = new JButton(action);
                buttonPanel.add(button);
                
                
                KeyStroke ks = null;
                final InputMap imap = button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
                final ActionMap amap = button.getActionMap();
         
                ks = (KeyStroke) action.getValue(Action.ACCELERATOR_KEY);
                imap.put(ks, action.getValue(Action.NAME));
                amap.put(action.getValue(Action.NAME), action);

            }
        }

        buttonPanel.add(Box.createHorizontalGlue());

        first = true;
        for (final Result result: results)
        {
            final Action action = new ModalDialogAction(this, result);
            final JButton button = new JButton(action);
            if (first)
                first = false;
            else
                buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            buttonPanel.add(button);
            this.actions.put(result, action);
            this.buttons.put(result, button);
        }

        // Add button panel to dialog
        add(buttonPanel, BorderLayout.SOUTH);
    }


    /**
     * Creates the UI.
     */

    protected abstract void createUI();


    /**
     * Opens the modal dialog.
     * 
     * @return The modal result
     */

    public Result open()
    {
        try
        {
            Config.restoreWindowState(this);
            setVisible(true);
            return this.result;
        }
        finally
        {
            Config.saveWindowState(this);
        }
    }


    /**
     * Closes the modal dialog with the specified result.
     * 
     * @param result
     *            The modal result
     */

    public void close(final Result result)
    {
        this.result = result;
        setVisible(false);
    }


    /**
     * Cancels the dialog.
     */

    public void cancel()
    {
        this.cancelButton.doClick(0);
    }


    /**
     * Enables or disables the specified modal result.
     * 
     * @param result
     *            The modal result to enable/disable
     * @param enabled
     *            True for enable, false for disable
     */

    public void setResultEnabled(final Result result, final boolean enabled)
    {
        this.actions.get(result).setEnabled(enabled);
    }


    /**
     * Returns true if specified result is enabled or false if it is disabled.
     * 
     * @param result
     *            The modal result to query
     * @return True if enabled, false if disabled
     */

    public boolean isResultEnabled(final Result result)
    {
        return this.actions.get(result).isEnabled();
    }
}
