/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.support;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.KeyStroke;

import de.ailis.xadrian.Main;


/**
 * Simple internationalization utility class.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public final class I18N
{
    /** The messages */
    private static final ResourceBundle messages = ResourceBundle
        .getBundle(Main.class.getPackage().getName() + ".messages");


    /**
     * Private constructor to prevent instantiation
     */

    private I18N()
    {
        // Empty
    }


    /**
     * Returns the message resource with the specified key. If not found then
     * null is returned.
     * 
     * @param key
     *            The message resource key
     * @return The message resource value or null if not found
     */

    private static String get(final String key)
    {
        try
        {
            return messages.getString(key);
        }
        catch (final MissingResourceException e)
        {
            return null;
        }
    }


    /**
     * Returns the message resource with the specified key. If not found then a
     * special string is returned indicating the missing message resource.
     * 
     * @param key
     *            The message resource key
     * @return The message resource value
     */

    public static String getString(final String key)
    {
        final String value = get(key);
        if (value == null) return "???" + key + "???";
        return value;
    }


    /**
     * Returns a title message resource.
     * 
     * @param key
     *            The base key of the message resource (without .title suffix)
     * @return The title message resource value
     */

    public static String getTitle(final String key)
    {
        return getString(key + ".title");
    }


    /**
     * Returns an accelerator message resource. If message resource is not found
     * or is empty then null is returned.
     * 
     * @param key
     *            The base key of the message resource (without .accelerator
     *            suffix)
     * @return The accelerator key stroke or null if not set
     */

    public static KeyStroke getAccelerator(final String key)
    {
        final String value = get(key + ".accelerator");
        if (value == null || value.length() == 0) return null;
        return KeyStroke.getKeyStroke(value);
    }


    /**
     * Returns a mnemonic message resource. If message resource is not found or
     * is empty then null is returned.
     * 
     * @param key
     *            The base key of the message resource (without .mnemonic
     *            suffix)
     * @return The mnemonic or 0 if not set
     */

    public static int getMnemonic(final String key)
    {
        final String value = get(key + ".mnemonic");
        if (value == null || value.length() == 0) return 0;
        int vk = value.charAt(0);
        if (vk >= 'a' && vk <= 'z') vk -= ('a' - 'A');
        return vk;
    }


    /**
     * Returns a tool tip message resource. If not found or empty then null is
     * returned.
     * 
     * @param key
     *            The base key of the message resource (without .tooltip suffix)
     * @return The tooltip message resource value
     */

    public static String getToolTip(final String key)
    {
        return get(key + ".tooltip");
    }


    /**
     * Creates a new menu and configures the title, accelerator, tooltip and
     * mnemonic automatically.
     * 
     * @param menuBar
     *            The menu bar (or menu) to which the new menu should be added
     * @param name
     *            The menu name
     * @return The created menu
     */

    public static JMenu createMenu(final JComponent menuBar,
        final String name)
    {
        final String key = "menu." + name;
        final JMenu menu = new JMenu(getTitle(key));
        menu.setMnemonic(getMnemonic(key));
        menu.setToolTipText(getToolTip(key));
        menuBar.add(menu);
        return menu;
    }
}
