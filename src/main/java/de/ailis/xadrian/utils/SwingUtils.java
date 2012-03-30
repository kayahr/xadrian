/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */

package de.ailis.xadrian.utils;

import java.awt.Desktop;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPopupMenu;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.WString;

import de.ailis.xadrian.support.Config;

/**
 * Static utility methods for common Swing tasks.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public final class SwingUtils
{
    /** The logger. */
    private static final Log LOG = LogFactory.getLog(SwingUtils.class);

    /** If platform has a shell32 library. */
    private static boolean hasShell32;

    static
    {
        try
        {
            Native.register("shell32");
            hasShell32 = true;
        }
        catch (final Throwable e)
        {
            hasShell32 = false;
        }
    }

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
                // Ignore mouse buttons outside of the normal range. This
                // fixes problems with trackpad scrolling.
                if (e.getButton() > MouseEvent.BUTTON3) return;

                if (e.isPopupTrigger())
                {
                    popup.show(component, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(final MouseEvent e)
            {
                // Ignore mouse buttons outside of the normal range. This
                // fixes problems with trackpad scrolling.
                if (e.getButton() > MouseEvent.BUTTON3) return;

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
                            @Override
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
                        @Override
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
     * Prepares the locale. The default is the system locale.
     */
    public static void prepareLocale()
    {
        final String locale = Config.getInstance().getLocale();
        if (locale != null) Locale.setDefault(new Locale(locale));
    }

    /**
     * Prepares the theme. The theme can be overridden with the environment
     * variable XADRIAN_SYSTHEME. The default is the system look and feel.
     *
     * @throws Exception
     *             When theme could not be prepared
     */
    public static void prepareTheme() throws Exception
    {
        final String theme = Config.getInstance().getTheme();
        if (theme != null)
        {
            try
            {
                UIManager.setLookAndFeel(theme);
                return;
            }
            catch (final Exception e)
            {
                LOG.warn("Can't set theme " + theme +
                    ". Falling back to system look-and-feel. Reason: " + e, e);
            }
        }

        try
        {
            UIManager
                .setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (final Exception e)
        {
            LOG.warn("Can't set system look-and-feel. " +
                "Falling back to default. Reason: " + e, e);
        }
    }

    /**
     * Prepares the Swing GUI.
     *
     * @throws Exception
     *             When GUI could not be prepared
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
     *             When something goes wrong
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

    /**
     * Opens a URL in the browser. It first tries to do this with the Desktop
     * API. If this fails then it tries to use the FreeDesktop-API.
     *
     * @param uri
     *            The URI to open.
     */
    public static void openBrowser(final URI uri)
    {
        try
        {
            try
            {
                Desktop.getDesktop().browse(uri);
            }
            catch (final UnsupportedOperationException e)
            {
                Runtime.getRuntime().exec("xdg-open '" + uri + "'");
            }
        }
        catch (final IOException e)
        {
            LOG.error("Unable to external browser: " + e, e);
        }
    }

    /**
     * Opens a URL in the browser. It first tries to do this with the Desktop
     * API. If this fails then it tries to use the FreeDesktop-API.
     *
     * @param url
     *            The URL to open.
     */
    public static void openBrowser(final String url)
    {
        try
        {
            openBrowser(new URI(url));
        }
        catch (final URISyntaxException e)
        {
            LOG.error(e.toString(), e);
        }
    }

    /**
     * Sets the application name. There is no API for this (See
     * http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6528430) so this
     * method uses reflection to do this. This may fail if the Java
     * implementation is changed but any exception here will be ignored.
     *
     * The application name is currently only used for X11 desktops and only
     * important for some window managers like Gnome Shell.
     *
     * @param appName
     *            The application name to set.
     */
    public static void setAppName(final String appName)
    {
        final Toolkit toolkit = Toolkit.getDefaultToolkit();
        final Class<?> cls = toolkit.getClass();

        try
        {
            // When X11 toolkit is used then set the awtAppClassName field
            if (cls.getName().equals("sun.awt.X11.XToolkit"))
            {
                final Field field = cls.getDeclaredField("awtAppClassName");
                field.setAccessible(true);
                field.set(toolkit, appName);
            }
        }
        catch (final Exception e)
        {
            LOG.warn("Unable to set application name: " + e, e);
        }
    }

    /**
     * Sets the app user model id. This is needed for the Windows 7 taskbar
     * so the application is correctly associated with the starter icon.
     * The same app user model id must be set in the shortcut.
     *
     * @param appId
     *            The app user model id to set.
     */
    public static void setAppUserModelId(final String appId)
    {
        if (!hasShell32) return;
        try
        {
            final long errorCode =
                SetCurrentProcessExplicitAppUserModelID(new WString(appId))
                    .longValue();
            if (errorCode != 0)
                LOG.error("Unable to set appUserModelID. Error code " +
                    errorCode);
        }
        catch (final Throwable e)
        {
            LOG.error("Unable to set appUserModelID: " + e, e);
        }
    }

    /**
     * Native Windows function mapped via JNA.
     *
     * @param appId
     *            The app user model ID to set.
     * @return Error code.
     */
    private static native NativeLong SetCurrentProcessExplicitAppUserModelID(
        WString appId);
}
