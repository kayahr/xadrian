/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import de.ailis.xadrian.Main;
import de.ailis.xadrian.components.FactoryTreeCellRenderer;
import de.ailis.xadrian.data.Factory;
import de.ailis.xadrian.freemarker.TemplateFactory;
import de.ailis.xadrian.models.FactoryTreeModel;
import de.ailis.xadrian.resources.Images;
import de.ailis.xadrian.support.I18N;
import de.ailis.xadrian.support.ModalDialog;
import freemarker.template.Template;


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

    /** The freemarker template for the factory info */
    private static final Template template = TemplateFactory
        .getTemplate("factory.ftl");
    
    /** The singleton instance of this dialog */
    private final static AddFactoryDialog instance = new AddFactoryDialog();

    /** The factories to add */
    private Factory[] factories;

    /** The factories tree */
    private JTree factoriesTree;

    /** The factory info text pane */
    private JTextPane textPane;


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
        this.factoriesTree.setCellRenderer(new FactoryTreeCellRenderer());
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
        this.factoriesTree.expandRow(2);

        // Create the factory pane
        final JScrollPane factoryPane = new JScrollPane(this.factoriesTree);
        factoryPane.setPreferredSize(new Dimension(320, 340));

        // Create the info pane
        this.textPane = new JTextPane();
        final JScrollPane infoPane = new JScrollPane(this.textPane);
        infoPane.setPreferredSize(new Dimension(196, 340));
        this.textPane.setEditable(false);
        this.textPane.setBackground(new Color(0xff, 0xff, 0xaa));
        this.textPane.setContentType("text/html");
        this.textPane.setDoubleBuffered(true);

        // Set the base URL of the text pane
        ((HTMLDocument) this.textPane.getDocument()).setBase(Main.class
            .getResource("templates/"));
        
        // Create the split pane housing the factory pane and info pane
        final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            factoryPane, infoPane);
        
        // Create another container for just adding some border
        final JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(splitPane);

        // Put this last panel into the window
        add(contentPanel, BorderLayout.CENTER);
        
        redraw();
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
            redraw();
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
                redraw();
                return;
            }
            this.factories[i] = (Factory) factory;
            i++;
        }
        setResultEnabled(Result.OK, true);
        redraw();
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

    
    /**
     * Redraws the freemarker template.
     */

    private void redraw()
    {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("factories", this.factories == null ? new Object[0] : this.factories);
        final String content = TemplateFactory.processTemplate(template,
            model);
        this.textPane.setText(content);
        this.textPane.setCaretPosition(0);
    }
}
