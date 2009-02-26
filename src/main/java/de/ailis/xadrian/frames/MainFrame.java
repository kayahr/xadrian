/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.frames;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

import de.ailis.xadrian.actions.AboutAction;
import de.ailis.xadrian.actions.AddFactoryAction;
import de.ailis.xadrian.actions.CloseAction;
import de.ailis.xadrian.actions.CloseAllAction;
import de.ailis.xadrian.actions.ExitAction;
import de.ailis.xadrian.actions.NewAction;
import de.ailis.xadrian.actions.OpenAction;
import de.ailis.xadrian.actions.PrintAction;
import de.ailis.xadrian.actions.SaveAction;
import de.ailis.xadrian.actions.SaveAllAction;
import de.ailis.xadrian.actions.SaveAsAction;
import de.ailis.xadrian.actions.SetSunsAction;
import de.ailis.xadrian.components.ComplexEditor;
import de.ailis.xadrian.data.Complex;
import de.ailis.xadrian.dialogs.AboutDialog;
import de.ailis.xadrian.listeners.EditorStateListener;
import de.ailis.xadrian.listeners.MainStateListener;
import de.ailis.xadrian.resources.Images;
import de.ailis.xadrian.support.I18N;


/**
 * The main frame.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class MainFrame extends JFrame implements EditorStateListener,
    ChangeListener
{
    /** Serial version UID */
    private static final long serialVersionUID = 7989554637240491666L;

    /** The event listener list */
    private final EventListenerList listenerList = new EventListenerList();

    /** The "exit" action */
    private final Action exitAction = new ExitAction(this);

    /** The "new" action */
    private final Action newAction = new NewAction(this);

    /** The "open" action */
    private final Action openAction = new OpenAction(this);

    /** The "close" action */
    private final Action closeAction = new CloseAction(this);

    /** The "close all" action */
    private final Action closeAllAction = new CloseAllAction(this);

    /** The "save" action */
    private final Action saveAction = new SaveAction(this);

    /** The "save as" action */
    private final Action saveAsAction = new SaveAsAction(this);

    /** The "save all" action */
    private final Action saveAllAction = new SaveAllAction(this);

    /** The "print" action */
    private final Action printAction = new PrintAction(this);

    /** The "about" action */
    private final Action aboutAction = new AboutAction(this);

    /** The "addFactory" action */
    private final Action addFactoryAction = new AddFactoryAction(this);

    /** The "changeSuns" action */
    private final Action setSunsAction = new SetSunsAction(this);
    
    /** The about dialog */
    private final AboutDialog aboutDialog = new AboutDialog();


    /** The tabbed pane */
    private JTabbedPane tabs;


    /**
     * Constructor
     */

    public MainFrame()
    {
        setTitle(I18N.getString("title"));
        setIconImages(Images.LOGOS);

        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(final WindowEvent e)
            {
                exit();
            }});
        setLocationRelativeTo(null);

        createMenuBar();
        createToolBar();
        createContent();
        createStatusBar();

        pack();

        this.tabs.requestFocus();
    }


    /**
     * Creates the menu.
     */

    private void createMenuBar()
    {
        // Create the menu bar
        final JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Create the 'File' menu
        final JMenu fileMenu = I18N.createMenu(menuBar, "file");
        fileMenu.add(this.newAction);
        fileMenu.add(this.openAction);
        fileMenu.addSeparator();
        fileMenu.add(this.closeAction);
        fileMenu.add(this.closeAllAction);
        fileMenu.addSeparator();
        fileMenu.add(this.saveAction);
        fileMenu.add(this.saveAsAction);
        fileMenu.add(this.saveAllAction);
        fileMenu.addSeparator();
        fileMenu.add(this.printAction);
        fileMenu.addSeparator();
        fileMenu.add(this.exitAction);

        // Create the 'Complex' menu
        final JMenu complexMenu = I18N.createMenu(menuBar, "complex");
        complexMenu.add(this.addFactoryAction);
        complexMenu.add(this.setSunsAction);

        // Create the 'Help' menu
        final JMenu helpMenu = I18N.createMenu(menuBar, "help");
        helpMenu.add(this.aboutAction);
    }


    /**
     * Creates the tool bar.
     */

    private void createToolBar()
    {
        final JToolBar toolBar = new JToolBar(SwingConstants.HORIZONTAL);
        toolBar.setFloatable(false);
        toolBar.setRollover(true);
        toolBar.add(this.exitAction);
        toolBar.addSeparator();
        toolBar.add(this.newAction);
        toolBar.add(this.openAction);
        toolBar.add(this.closeAction);
        toolBar.add(this.saveAction);
        toolBar.add(this.printAction);
        toolBar.addSeparator();
        toolBar.add(this.addFactoryAction);
        add(toolBar, BorderLayout.NORTH);
    }


    /**
     * Creates the content.
     */

    private void createContent()
    {
        this.tabs = new JTabbedPane();
        add(this.tabs, BorderLayout.CENTER);
        this.tabs.setPreferredSize(new Dimension(640, 480));
        this.tabs.addChangeListener(this);
    }


    /**
     * Creates the status bar.
     */

    private void createStatusBar()
    {
        final JLabel statusBar = new JLabel(" ");
        statusBar.setBorder(new EmptyBorder(2, 5, 2, 5));
        add(statusBar, BorderLayout.SOUTH);
    }


    /**
     * Adds a state listener.
     * 
     * @param listener
     *            The state listener to add
     */

    public void addStateListener(final MainStateListener listener)
    {
        this.listenerList.add(MainStateListener.class, listener);
    }


    /**
     * Removes a state listener.
     * 
     * @param listener
     *            The state listener to remove
     */

    public void removeStateListener(final MainStateListener listener)
    {
        this.listenerList.remove(MainStateListener.class, listener);
    }


    /**
     * Fire the state change event.
     */

    private void fireChange()
    {
        final Object[] listeners = this.listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2)
            if (listeners[i] == MainStateListener.class)
                ((MainStateListener) listeners[i + 1]).mainStateChanged(this);
    }


    /**
     * Creates a new factory complex tab
     */

    public void createComplexTab()
    {
        createComplexTab(new ComplexEditor(new Complex()));
    }


    /**
     * Creates a new factory complex tab with the specified complex editor in
     * it.
     * 
     * @param editor
     *            The complex editor
     */

    public void createComplexTab(final ComplexEditor editor)
    {
        this.tabs.addTab(editor.getComplex().getName(), editor);
        this.tabs.setSelectedComponent(editor);
        editor.addStateListener(this);
        this.fireChange();
    }


    /**
     * Returns the current tab component.
     * 
     * @return The current tab component
     */

    public Component getCurrentTab()
    {
        return this.tabs.getSelectedComponent();
    }


    /**
     * Closes the current tab. Prompts for saving unsaved changes before
     * closing. Returns true if the tab was closed or false if it
     * was not closed.
     * 
     * @return True if tab was closed, false if not
     */

    public boolean closeCurrentTab()
    {
        final Component current = getCurrentTab();
        if (current != null)
        {
            final ComplexEditor editor = (ComplexEditor) current;
            if (editor.isChanged())
            {
                final int answer = JOptionPane.showConfirmDialog(null, String
                    .format(I18N.getString("confirm.saveChanges"), editor
                        .getComplex().getName()), I18N
                    .getTitle("confirm.saveChanges"),
                    JOptionPane.YES_NO_CANCEL_OPTION);
                if (answer == JOptionPane.CANCEL_OPTION) return false;
                if (answer == JOptionPane.YES_OPTION)
                {
                    editor.save();
                    if (editor.isChanged()) return false;
                }
            }

            this.tabs.remove(current);
            this.fireChange();
            return true;
        }
        return false;
    }


    /**
     * Closes all open tabs. Prompts for unsaved changes. Returns true if
     * all tabs have been closed or false if at least one tab was not closed.
     * 
     * @return True if all tabs were closed, false if not.
     */

    public boolean closeAllTabs()
    {
        while (this.tabs.getComponentCount() > 0)
            if (!closeCurrentTab()) return false;
        this.fireChange();
        return true;
    }


    /**
     * Shows the about dialog.
     */

    public void about()
    {
        this.aboutDialog.open();
    }


    /**
     * Returns the tabs.
     * 
     * @return The tabs
     */

    public JTabbedPane getTabs()
    {
        return this.tabs;
    }


    /**
     * @see de.ailis.xadrian.listeners.EditorStateListener#editorStateChanged(de.ailis.xadrian.components.ComplexEditor)
     */

    @Override
    public void editorStateChanged(final ComplexEditor sender)
    {
        final int index = this.tabs.indexOfComponent(sender);
        this.tabs.setTitleAt(index, sender.getComplex().getName()
            + (sender.isChanged() ? "*" : ""));
        fireChange();
    }


    /**
     * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
     */

    @Override
    public void stateChanged(final ChangeEvent e)
    {
        fireChange();
    }
    
    
    /**
     * Exits the application. Prompts for saving unsaved changes before that.
     */
    
    public void exit()
    {
        if (closeAllTabs()) System.exit(0);
    }
}
