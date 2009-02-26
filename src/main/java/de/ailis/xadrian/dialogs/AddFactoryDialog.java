/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import de.ailis.xadrian.data.Factory;
import de.ailis.xadrian.models.FactoryTreeModel;
import de.ailis.xadrian.resources.Images;
import de.ailis.xadrian.support.I18N;
import de.ailis.xadrian.support.ModalDialog;


/**
 * Dialog for selecting a complex factory.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class AddFactoryDialog extends ModalDialog implements
    TreeSelectionListener
{
    /** Serial version UID */
    private static final long serialVersionUID = 4157034476842995945L;

    /** The singleton instance of this dialog */
    private final static AddFactoryDialog instance = new AddFactoryDialog();

    /** The factories to add */
    private Factory[] factories;

    /** The factories tree */
    private JTree factoriesTree;


    /**
     * Constructor
     */

    private AddFactoryDialog()
    {
        super(Result.OK, Result.CANCEL);
    }


    /**
     * Creates the UI
     */

    @Override
    protected void createUI()
    {
        setTitle(I18N.getTitle("dialog.addFactory"));
        setIconImages(Images.LOGOS);

        // Create the content controls
        this.factoriesTree = new JTree();
        this.factoriesTree.setModel(new FactoryTreeModel());
        this.factoriesTree.setShowsRootHandles(true);
        this.factoriesTree.setRootVisible(false);
        this.factoriesTree.setEditable(false);
        this.factoriesTree.addMouseListener(new MouseAdapter()
        {
            /** @see MouseAdapter#mouseClicked(MouseEvent) */
            @Override
            public void mouseClicked(final MouseEvent e)
            {
                if (e.getClickCount() == 2)
                    getRootPane().getDefaultButton().doClick(0);
            }
        });
        this.factoriesTree.getSelectionModel().setSelectionMode(
            TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        this.factoriesTree.addTreeSelectionListener(this);

        final JScrollPane factoryPane = new JScrollPane(this.factoriesTree);
        factoryPane.setPreferredSize(new Dimension(320, 256));

        // Create the content panel
        final JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(factoryPane);

        add(contentPanel, BorderLayout.CENTER);
    }


    /**
     * Returns the singleton instance
     * 
     * @return The singleton instance
     */

    public static AddFactoryDialog getInstance()
    {
        return instance;
    }


    /**
     * @see de.ailis.xadrian.support.ModalDialog#open()
     */

    @Override
    public Result open()
    {
        this.factories = null;
        this.factoriesTree.clearSelection();
        setResultEnabled(Result.OK, false);
        return super.open();
    }


    /**
     * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
     */

    @Override
    public void valueChanged(final TreeSelectionEvent e)
    {
        final TreePath[] paths = this.factoriesTree.getSelectionPaths();
        if (paths == null)
        {
            setResultEnabled(Result.OK, false);
            this.factories = null;
            return;
        }
        this.factories = new Factory[paths.length];
        int i = 0;
        for (final TreePath path : paths)
        {
            final Object factory = path.getLastPathComponent();
            if (!(factory instanceof Factory))
            {
                setResultEnabled(Result.OK, false);
                this.factories = null;
                return;
            }
            this.factories[i] = (Factory) factory;
            i++;
        }
        setResultEnabled(Result.OK, true);
    }


    /**
     * Returns the factories to add.
     * 
     * @return The factories to add
     */

    public Factory[] getFactories()
    {
        return this.factories;
    }
}
