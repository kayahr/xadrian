/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */

package de.ailis.xadrian.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import de.ailis.xadrian.components.LabelSeparator;
import de.ailis.xadrian.data.Game;
import de.ailis.xadrian.data.Race;
import de.ailis.xadrian.data.Theme;
import de.ailis.xadrian.data.factories.GameFactory;
import de.ailis.xadrian.data.factories.ThemeFactory;
import de.ailis.xadrian.frames.SplashFrame;
import de.ailis.xadrian.support.Config;
import de.ailis.xadrian.support.I18N;
import de.ailis.xadrian.support.ModalDialog;
import de.ailis.xadrian.utils.SwingUtils;

/**
 * Dialog for setting up the application preferences.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public class PreferencesDialog extends ModalDialog
{
    /** Serial version UID */
    private static final long serialVersionUID = 6796446567309230958L;

    /** The singleton instance of this dialog */
    private final static PreferencesDialog instance = new PreferencesDialog();

    /** The checkboxes for allowed manufacturer races */
    private Map<Race, JCheckBox> racesCheckBoxes;

    /**
     * The checkbox for enable Factory description (resources needed) in Complex
     * table
     */
    private JCheckBox showFactoryResourcesCheckBox;

    /** The player sector combo box */
    private JComboBox playerSectorComboBox;

    /** The games combo box */
    private JComboBox gamesComboBox;

    /** The theme combo box. */
    private JComboBox themeComboBox;

    /**
     * Constructor
     */
    private PreferencesDialog()
    {
        init("preferences", Result.OK, Result.CANCEL);
        SplashFrame.getInstance().advanceProgress();
    }

    /**
     * Creates the UI
     */
    @Override
    protected void createUI()
    {
        // Create the content panel
        final JPanel contentPanel = new JPanel();
        contentPanel
            .setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        contentPanel
            .setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        contentPanel.add(createGeneralSettings());
        contentPanel.add(createUsedRaces());
        contentPanel.add(createX3TCSettings());
        contentPanel.add(createViewSettings());

        add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * Creates and returns the panel with the races settings.
     * 
     * @return The created panel
     */
    private JPanel createUsedRaces()
    {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        // Create the races separator
        final LabelSeparator separator =
            new LabelSeparator(I18N.getString("dialog.preferences.usedRaces"));
        separator.setToolTipText(I18N
            .getToolTip("dialog.preferences.usedRaces"));
        panel.add(separator);

        // Create the race checkboxes
        final JPanel racePanel = new JPanel();
        racePanel.setLayout(new GridBagLayout());
        racePanel.setBorder(new EmptyBorder(2, 20, 5, 0));

        final GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        this.racesCheckBoxes = new HashMap<Race, JCheckBox>();
        for (final Race race : getIgnorableRaces())
        {
            final JCheckBox raceCheckBox = new JCheckBox(race.getName());
            if (c.gridx == 3)
            {
                c.gridx = 0;
                c.gridy++;
            }
            racePanel.add(raceCheckBox, c);
            this.racesCheckBoxes.put(race, raceCheckBox);
            c.gridx++;
        }
        panel.add(racePanel);
        return panel;
    }

    /**
     * Creates and returns the list of ignorable races.
     * 
     * @return The list of ignorable races.
     */
    private SortedSet<Race> getIgnorableRaces()
    {
        SortedSet<Race> races = new TreeSet<Race>();
        for (Game game : GameFactory.getInstance().getGames())
        {
            for (Race race : game.getRaceFactory().getManufacturerRaces())
            {
                races.add(race);
            }
        }
        return races;
    }

    /**
     * Creates and returns the panel with the game settings.
     * 
     * @return The created panel
     */
    private JPanel createX3TCSettings()
    {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        // Create the separator
        final LabelSeparator separator =
            new LabelSeparator(I18N.getString("game.x3tc"));
        panel.add(separator);

        // Create the control panel
        final JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        controlPanel.setBorder(new EmptyBorder(2, 20, 5, 0));
        final GridBagConstraints c = new GridBagConstraints();
        final JLabel label =
            new JLabel(I18N.getString("dialog.preferences.playerSector"));
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;
        controlPanel.add(label, c);
        c.weightx = 1;
        c.gridx = 1;
        c.insets.left = 5;
        this.playerSectorComboBox = new JComboBox();
        Game x3tc = GameFactory.getInstance().getGame("x3tc");
        for (int i = 0; i < 5; i++)
        {
            this.playerSectorComboBox.addItem(I18N.getString(x3tc,
                "sector.sec-20-2-" + i));
        }
        controlPanel.add(this.playerSectorComboBox, c);
        panel.add(controlPanel);
        return panel;
    }

    /**
     * Creates and returns the panel with the general settings.
     * 
     * @return The created panel
     */
    private JPanel createGeneralSettings()
    {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        // Create the separator
        final LabelSeparator separator =
            new LabelSeparator(I18N.getString("dialog.preferences.general"));
        panel.add(separator);

        // Create the control panel
        final JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        controlPanel.setBorder(new EmptyBorder(2, 20, 5, 0));
        final GridBagConstraints c = new GridBagConstraints();
        final JLabel label =
            new JLabel(I18N.getString("dialog.preferences.game"));
        c.anchor = GridBagConstraints.WEST;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;
        controlPanel.add(label, c);
        c.weightx = 1;
        c.gridx = 1;
        c.insets.left = 5;
        this.gamesComboBox = new JComboBox();
        this.gamesComboBox.addItem(I18N
            .getString("dialog.preferences.alwaysAsk"));
        for (Game game : GameFactory.getInstance().getGames())
        {
            this.gamesComboBox.addItem(game);
        }
        controlPanel.add(this.gamesComboBox, c);
        panel.add(controlPanel);
        return panel;
    }

    /**
     * Creates and returns the panel with the view settings.
     * 
     * @return The created panel
     */
    private JPanel createViewSettings()
    {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        // Create the separator
        final LabelSeparator separator =
            new LabelSeparator(
                I18N.getString("dialog.preferences.viewSettings"));
        panel.add(separator);

        // Create the control panel
        final JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        controlPanel.setBorder(new EmptyBorder(2, 20, 5, 0));
        final GridBagConstraints c = new GridBagConstraints();
        final JLabel label =
            new JLabel(I18N.getString("dialog.preferences.theme"));
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        controlPanel.add(label, c);
        c.gridx = 1;
        c.insets.left = 5;
        this.themeComboBox = new JComboBox();
        for (Theme theme : ThemeFactory.getInstance().getThemes())
        {
            this.themeComboBox.addItem(theme);
        }
        controlPanel.add(this.themeComboBox, c);
        panel.add(controlPanel);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        this.showFactoryResourcesCheckBox = new JCheckBox(
            I18N.getString("dialog.preferences.showFactoryResources"));
        this.showFactoryResourcesCheckBox.setToolTipText(
            I18N.getToolTip("dialog.preferences.showFactoryResources"));
        c.weightx = 1;
        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        controlPanel.add(this.showFactoryResourcesCheckBox, c);

        panel.add(controlPanel);
        return panel;
    }

    /**
     * Returns the singleton instance
     * 
     * @return The singleton instance
     */
    public static PreferencesDialog getInstance()
    {
        return instance;
    }

    /**
     * @see de.ailis.xadrian.support.ModalDialog#open()
     */
    @Override
    public Result open()
    {
        final Config config = Config.getInstance();

        // Load preference values
        for (final Map.Entry<Race, JCheckBox> entry : this.racesCheckBoxes
            .entrySet())
        {
            final Race race = entry.getKey();
            final JCheckBox checkBox = entry.getValue();
            checkBox.setSelected(!config.isRaceIgnored(race));
        }
        this.showFactoryResourcesCheckBox.setSelected(config
            .isShowFactoryResources());
        this.themeComboBox.setSelectedItem(ThemeFactory.getInstance().getTheme(
            UIManager.getLookAndFeel().getClass().getName()));
        this.playerSectorComboBox.setSelectedIndex(config.getPlayerSector());

        final String defaultGameId = config.getDefaultGame();
        final GameFactory gameFactory = GameFactory.getInstance();
        if (defaultGameId == null || !gameFactory.hasGame(defaultGameId))
            this.gamesComboBox.setSelectedIndex(0);
        else
            this.gamesComboBox.setSelectedItem(GameFactory.getInstance()
                .getGame(defaultGameId));

        final Result result = super.open();
        if (result == Result.OK)
        {
            // Save preference values
            for (final Map.Entry<Race, JCheckBox> entry : this.racesCheckBoxes
                .entrySet())
            {
                final Race race = entry.getKey();
                final JCheckBox checkBox = entry.getValue();
                config.setRaceIgnored(race, !checkBox.isSelected());
            }
            config.setShowFactoryResources(this.showFactoryResourcesCheckBox
                .isSelected());
            config
                .setPlayerSector(this.playerSectorComboBox.getSelectedIndex());
            config.setTheme(((Theme) this.themeComboBox.getSelectedItem())
                .getClassName());
            if (this.gamesComboBox.getSelectedIndex() == 0)
                config.setDefaultGame(null);
            else
                config.setDefaultGame(((Game) this.gamesComboBox
                    .getSelectedItem()).getId());
        }
        return result;
    }

    /**
     * Test main method.
     * 
     * @param args
     *            Command line arguments
     * @throws Exception
     *             When something goes wrong.
     */
    public static void main(String[] args) throws Exception
    {
        SwingUtils.prepareGUI();
        new PreferencesDialog().open();
        Config.getInstance().save();
        System.exit(0);
    }
}
