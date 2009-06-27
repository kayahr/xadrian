/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.utils;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Window;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.text.JTextComponent;


/**
 * Static utility methods for common Swing tasks.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public final class SwingUtils
{
    /**
     * Private constructor to prevent instantiation
     */

    private SwingUtils()
    {
        // Empty
    }


    /**
     * Gives a component a popup menu
     * 
     * @param component
     *            The target component
     * @param popup
     *            The popup menu
     */

    public static void setPopupMenu(final JComponent component,
        final JPopupMenu popup)
    {
        component.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(final MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    popup.show(component, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(final MouseEvent e)
            {
                if (e.isPopupTrigger())
                {
                    popup.show(component, e.getX(), e.getY());
                }
            }
        });
    }


    /**
     * Installs a workaround for bug #4699955 in a JSpinner.
     * 
     * @param spinner
     *            The spinner to fix
     */

    public static void installSpinnerBugWorkaround(final JSpinner spinner)
    {
        ((DefaultEditor) spinner.getEditor()).getTextField().addFocusListener(
            new FocusAdapter()
            {
                @Override
                public void focusGained(final FocusEvent e)
                {
                    if (e.getSource() instanceof JTextComponent)
                    {
                        final JTextComponent text =
                            ((JTextComponent) e.getSource());
                        SwingUtilities.invokeLater(new Runnable()
                        {
                            public void run()
                            {
                                text.selectAll();
                            }
                        });
                    }
                }
            });
        spinner.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(final FocusEvent e)
            {
                if (e.getSource() instanceof JSpinner)
                {
                    final JTextComponent text =
                        ((DefaultEditor) ((JSpinner) e.getSource())
                            .getEditor()).getTextField();
                    SwingUtilities.invokeLater(new Runnable()
                    {
                        public void run()
                        {
                            text.requestFocus();
                        }
                    });
                }
            }
        });
    }


    /**
     * Checks if the specified window (may it be a dialog or a frame) is
     * resizable.
     * 
     * @param window
     *            The window
     * @return True if window is resizable, false if not
     */

    public static boolean isResizable(final Window window)
    {
        if (window instanceof Dialog) return ((Dialog) window).isResizable();
        if (window instanceof Frame) return ((Frame) window).isResizable();
        return false;
    }


    /**
     * Prepares the locale. The language can be overridden with the environment
     * variable XADRIAN_LANG. Set it to "de" to enforce German language for
     * example. The default is the system locale.
     */

    public static void prepareLocale()
    {
        final String language = System.getenv().get("XADRIAN_LANG");
        if (language != null) Locale.setDefault(new Locale(language));
    }


    /**
     * Prepares the theme. The theme can be overridden with the environment
     * variable XADRIAN_SYSTHEME. The default is the system look and feel.
     * 
     * @throws Exception
     */

    public static void prepareTheme() throws Exception
    {
        final String sysThemeStr = System.getenv().get("XADRIAN_SYSTHEME");
        if (sysThemeStr == null || Boolean.parseBoolean(sysThemeStr))
            UIManager
                .setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }


    /**
     * Prepares the Swing GUI.
     * 
     * @throws Exception
     */

    public static void prepareGUI() throws Exception
    {
        prepareLocale();
        prepareTheme();
    }


    /**
     * Runs the specified component in an empty test frame. This method is used
     * to test single components during development.
     * 
     * @param component
     *            The component to test
     * @throws Exception
     */

    public static void testComponent(final JComponent component)
        throws Exception
    {
        final JFrame frame = new JFrame("Test");
        frame.setName("componentTest");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(component);
        frame.pack();
        frame.setVisible(true);
    }


    /**
     * Sets the preferred height of the specified component.
     * 
     * @param component
     *            The component
     * @param height
     *            The preferred height
     */

    public static void setPreferredHeight(final JComponent component,
        final int height)
    {
        component.setPreferredSize(new Dimension(
            component.getPreferredSize().width, height));
    }


    /**
     * Sets the preferred width of the specified component.
     * 
     * @param component
     *            The component
     * @param width
     *            The preferred width
     */

    public static void setPreferredWidth(final JComponent component,
        final int width)
    {
        component.setPreferredSize(new Dimension(width, component
            .getPreferredSize().height));
    }


    /**
     * Adds a component action.
     * 
     * @param component
     *            The compoennt to add the action to
     * @param action
     *            The action to add
     */

    public static void addComponentAction(final JComponent component,
        final Action action)
    {
        final InputMap imap =
            component.getInputMap(component.isFocusable()
                ? JComponent.WHEN_FOCUSED : JComponent.WHEN_IN_FOCUSED_WINDOW);
        final ActionMap amap = component.getActionMap();
        final KeyStroke ks =
            (KeyStroke) action.getValue(Action.ACCELERATOR_KEY);
        imap.put(ks, action.getValue(Action.NAME));
        amap.put(action.getValue(Action.NAME), action);
    }
}
