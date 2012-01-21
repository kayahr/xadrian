/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.xadrian.support;

import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.JOptionPane;

import de.ailis.xadrian.Main;

/**
 * Error handler.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */

public class ErrorHandler extends EventQueue
{
    /**
     * @see java.awt.EventQueue#dispatchEvent(java.awt.AWTEvent)
     */
    @Override
    protected void dispatchEvent(AWTEvent newEvent)
    {
        try
        {
            super.dispatchEvent(newEvent);
        }
        catch (Throwable t)
        {
            showError(t);
        }
    }

    /**
     * Displays the specified exception.
     * 
     * @param t
     *            The exception to display.
     */
    public static void showError(Throwable t)
    {
        // Dump full stack trace to STDERR
        t.printStackTrace(System.err);

        // Build an error message to display in a dialog
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        String title = t.getMessage();
        if (title == null) title = "Error";
        writer.println(title);
        writer.println();
        String packageName = Main.class.getPackage().getName() + ".";
        Throwable current = t;
        while (current != null)
        {
            writer.println(t.toString());
            for (StackTraceElement e : current.getStackTrace())
            {
                writer.print("    ");
                writer.println(e.toString());
                if (e.getClassName().startsWith(packageName)) break;
            }
            current = current.getCause(); 
        }
        writer.close();
        String message = stringWriter.toString();

        // Copy error message into the clipboard
        Toolkit.getDefaultToolkit().getSystemClipboard()
            .setContents(new StringSelection(message), null);

        // Display error message in a dialog
        JOptionPane.showMessageDialog(null, message, "Error",
            JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Installs the error handler into the Swing toolkit.
     */
    public static void install()
    {
        Toolkit.getDefaultToolkit().getSystemEventQueue()
            .push(new ErrorHandler());
    }
}
