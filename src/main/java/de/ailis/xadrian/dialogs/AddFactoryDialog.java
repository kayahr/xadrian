/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */
package de.ailis.xadrian.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.UIManager;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.html.HTMLDocument;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import de.ailis.xadrian.Main;
import de.ailis.xadrian.components.FactoryTreeCellRenderer;
import de.ailis.xadrian.data.Factory;
import de.ailis.xadrian.data.Game;
import de.ailis.xadrian.data.factories.GameFactory;
import de.ailis.xadrian.frames.SplashFrame;
import de.ailis.xadrian.freemarker.TemplateFactory;
import de.ailis.xadrian.models.FactoryTreeModel;
import de.ailis.xadrian.support.Config;
import de.ailis.xadrian.support.ModalDialog;
import freemarker.template.Template;

/**
 * Dialog for selecting a complex factory.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class AddFactoryDialog extends ModalDialog implements
    TreeSelectionListener
{
    /** Serial version UID */
    private static final long serialVersionUID = 4157034476842995945L;

    /** The freemarker template for the factory info */
    private static final Template template = TemplateFactory
        .getTemplate("factory.ftl");

    /** The factories to add */
    private Factory[] factories;

    /** The factories tree */
    private JTree factoriesTree;

    /** The factory info text pane */
    private JTextPane textPane;

    /** The split pane between tree and info panel */
    private JSplitPane splitPane;

    /** The game. */
    private final Game game;

    /**
     * Constructor.
     *
     * @param game
     *            The game.
     */
    public AddFactoryDialog(final Game game)
    {
        this.game = game;
        init("addFactory", Result.OK, Result.CANCEL);
        SplashFrame.getInstance().advanceProgress();
    }

    /**
     * Creates the UI
     */
    @Override
    protected void createUI()
    {
        // Enable resizing
        setResizable(true);

        // Create the content controls
        this.factoriesTree = new JTree();
        this.factoriesTree.setModel(new FactoryTreeModel(this.game));
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
        factoryPane.setPreferredSize(new Dimension(320, 420));

        // Create the info pane
        this.textPane = new JTextPane();
        final JScrollPane infoPane = new JScrollPane(this.textPane);
        infoPane.setPreferredSize(new Dimension(210, 420));
        this.textPane.setBorder(null);
        this.textPane.setEditable(false);
        this.textPane.setContentType("text/html");
        this.textPane.setDoubleBuffered(true);

        final HTMLDocument document = (HTMLDocument) this.textPane.getDocument();
        
        // Set the base URL of the text pane
        document.setBase(Main.class.getResource("templates/"));
        
        // Modify the body style so it matches the system font
        final Font font = UIManager.getFont("Label.font");
        String bodyRule = "body { font-family: " + font.getFamily() + 
            "; font-size: " + font.getSize() + "pt; }";
        document.getStyleSheet().addRule(bodyRule);                
        
        // Create the split pane housing the factory pane and info pane
        this.splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
            factoryPane, infoPane);
        this.splitPane.setName("factoryInfoSplitPane");

        // Create another container for just adding some border
        final JPanel contentPanel = new JPanel();
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(this.splitPane);

        // Put this last panel into the window
        add(contentPanel, BorderLayout.CENTER);

        redraw();
    }

    /**
     * @see de.ailis.xadrian.support.ModalDialog#open()
     */
    @Override
    public Result open()
    {
        Config.restoreSplitPaneState(this.splitPane);
        try
        {
            this.factories = null;
            this.factoriesTree.clearSelection();
            setResultEnabled(Result.OK, false);
            return super.open();
        }
        finally
        {
            Config.saveSplitPaneState(this.splitPane);
        }
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
        return this.factories.clone();
    }

    /**
     * Redraws the freemarker template.
     */
    private void redraw()
    {
        final Map<String, Object> model = new HashMap<String, Object>();
        model.put("factories", this.factories == null ? new Object[0]
            : this.factories);
        final String content = TemplateFactory.processTemplate(template,
            model);
        this.textPane.setText(content);
        this.textPane.setCaretPosition(0);
    }

    /**
     * Test main method.
     *
     * @param args
     *            Command line arguments
     */
    public static void main(final String args[])
    {
        Game game = GameFactory.getInstance().getGame("x3tc");
        System.out.println(new AddFactoryDialog(game).open());
    }
}
