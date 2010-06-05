/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import de.ailis.xadrian.actions.ChangeSectorAction;
import de.ailis.xadrian.components.AsteroidsInfoPane;
import de.ailis.xadrian.data.Factory;
import de.ailis.xadrian.data.Sector;
import de.ailis.xadrian.data.factories.FactoryFactory;
import de.ailis.xadrian.support.I18N;
import de.ailis.xadrian.support.ModalDialog;
import de.ailis.xadrian.utils.SwingUtils;


/**
 * Dialog for setting the asteroids to use for a specific mine type in the
 * current complex.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class SetupAsteroidsDialog extends ModalDialog
{
    /** Serial version UID */
    private static final long serialVersionUID = 1;

    /** The split pane */
    private JSplitPane splitPane;

    /** The asteroids info pane */
    private AsteroidsInfoPane asteroidsInfoPane;

    /** The sector view */
    private JTextPane inputPane;

    /** The mine type to setup the asteroids for */
    private final Factory mineType;

    /** The label */
    private JLabel label;

    /** The yields */
    private final List<Integer> yields = new ArrayList<Integer>();


    /**
     * Constructor
     *
     * @param mineType
     *            The mine type
     */

    public SetupAsteroidsDialog(final Factory mineType)
    {
        super("setupAsteroids", Result.OK, Result.CANCEL);
        setResizable(false);
        this.label.setText(I18N.getString("dialog.setupAsteroids.yields",
            mineType.getRace().toString() + " " + mineType.toString()));
        this.mineType = mineType;
    }


    /**
     * @see de.ailis.xadrian.support.ModalDialog#init()
     */

    @Override
    protected void init()
    {
        this.asteroidsInfoPane = new AsteroidsInfoPane();
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
        final JTextPane input = this.inputPane = new JTextPane();
        input.getDocument().addDocumentListener(new DocumentListener()
        {
            @Override
            public void removeUpdate(final DocumentEvent e)
            {
                updateYields();
            }

            @Override
            public void insertUpdate(final DocumentEvent e)
            {
                updateYields();
            }

            @Override
            public void changedUpdate(final DocumentEvent e)
            {
                updateYields();
            }
        });

        // Create the factory pane
        final JScrollPane factoryPane = new JScrollPane(input);
        factoryPane.setPreferredSize(new Dimension(320, 240));

        // Create the info pane
        final AsteroidsInfoPane infoPane = this.asteroidsInfoPane;
        infoPane.setPreferredSize(new Dimension(210, 240));

        // Create the split pane housing the factory pane and info pane
        final JSplitPane splitPane = this.splitPane = new JSplitPane(
            JSplitPane.HORIZONTAL_SPLIT,
            factoryPane, infoPane);
        splitPane.setName("infoSplitPane");

        // Create another container for just adding some border
        final JPanel contentPanel = new JPanel(new BorderLayout(5, 5));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contentPanel.add(splitPane, BorderLayout.CENTER);
        this.label = new JLabel();
        contentPanel.add(this.label, BorderLayout.NORTH);

        // Put this last panel into the window
        add(contentPanel, BorderLayout.CENTER);
    }


    /**
     * Updates the yields
     */

    void updateYields()
    {
        final String text = this.inputPane.getText();
        this.yields.clear();
        for (final String part : text.split("[,\\s\\n\\r][\\s\\n\\r]*"))
        {
            try
            {
                final Integer yield = Integer.parseInt(part);
                if (yield < 0 || yield > 999)
                {
                    setResultEnabled(Result.OK, false);
                    return;
                }
                this.yields.add(yield);
            }
            catch (final NumberFormatException e)
            {
                setResultEnabled(Result.OK, false);
                return;
            }
        }
        setResultEnabled(Result.OK, this.yields.size() > 0);
    }


    /**
     * @see de.ailis.xadrian.support.ModalDialog#open()
     */

    @Override
    public Result open()
    {
        // Initialize the input pane with the yields
        final StringBuilder yields = new StringBuilder();
        for (final Integer yield : this.yields)
        {
            if (yields.length() > 0) yields.append(", ");
            yields.append(yield);
        }
        this.inputPane.setText(yields.toString());

        final Result result = super.open();
        return result;
    }


    /**
     * @see de.ailis.xadrian.support.ModalDialog#createDialogActions()
     */

    @Override
    protected List<Action> createDialogActions()
    {
        final List<Action> dialogActions = new ArrayList<Action>();
        dialogActions.add(new ChangeSectorAction(this.asteroidsInfoPane,
            "sector"));
        return dialogActions;
    }


    /**
     * Sets the yields
     *
     * @param yields
     *            The yields to set
     */

    public void setYields(final List<Integer> yields)
    {
        this.yields.clear();
        this.yields.addAll(yields);
    }


    /**
     * Returns the yields.
     *
     * @return The yields
     */

    public List<Integer> getYields()
    {
        return this.yields;
    }


    /**
     * Sets the sector.
     *
     * @param sector
     *            The sector to set
     */

    public void setSector(final Sector sector)
    {
        this.asteroidsInfoPane.setSector(sector);
    }


    /**
     * Returns the sector
     *
     * @return The sector
     */

    public Sector getSector()
    {
        return this.asteroidsInfoPane.getSector();
    }


    /**
     * Tests the component.
     *
     * @param args
     *            Command line arguments
     * @throws Exception
     *             When something goes wrong
     */

    public static void main(final String args[]) throws Exception
    {
        SwingUtils.prepareGUI();

        final Factory mineType = FactoryFactory.getInstance().getFactory(
            "siliconMineL-teladi");
        final SetupAsteroidsDialog dialog = new SetupAsteroidsDialog(mineType);
        final List<Integer> yields = new ArrayList<Integer>();
        yields.add(10);
        yields.add(25);
        yields.add(25);
        yields.add(128);
        dialog.setYields(yields);
        dialog.open();
        System.out.println(dialog.getYields());
        System.exit(0);
    }
}
