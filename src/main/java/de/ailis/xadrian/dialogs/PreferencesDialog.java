/*
 * $Id$
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.dialogs;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import de.ailis.xadrian.data.Race;
import de.ailis.xadrian.data.factories.RaceFactory;
import de.ailis.xadrian.support.Config;
import de.ailis.xadrian.support.I18N;
import de.ailis.xadrian.support.ModalDialog;


/**
 * Dialog for setting up the application preferences.
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class PreferencesDialog extends ModalDialog
{
    /** Serial version UID */
    private static final long serialVersionUID = 6796446567309230958L;

    /** The singleton instance of this dialog */
    private final static PreferencesDialog instance = new PreferencesDialog();

    /** The checkboxes for allowed manufacturer races */
    private Map<Race, JCheckBox> racesCheckboxes;


    /**
     * Constructor
     */

    private PreferencesDialog()
    {
        super("preferences", Result.OK, Result.CANCEL);
    }


    /**
     * Creates the UI
     */

    @Override
    protected void createUI()
    {
        // Create the content panel
        final JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel
            .setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        final GridBagConstraints c = new GridBagConstraints();

        // Create the races headline
        final JLabel racesLabel =
            new JLabel(I18N.getString("dialog.preferences.usedRaces"));
        racesLabel.setToolTipText(I18N.getToolTip("dialog.preferences.usedRaces"));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        contentPanel.add(racesLabel, c);
        c.gridx = 1;
        c.weightx = 1;
        c.insets.left = 5;
        final JSeparator racesSeparator = new JSeparator();
        contentPanel.add(racesSeparator, c);
        c.weightx = 0;
        c.insets.left = 0;

        // Create the race checkboxes
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        final JPanel racePanel = new JPanel();
        racePanel.setLayout(new GridBagLayout());
        final GridBagConstraints c2 = new GridBagConstraints();
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.anchor = GridBagConstraints.WEST;
        c2.gridx = 0;
        c2.gridy = 0;
        this.racesCheckboxes = new HashMap<Race, JCheckBox>();
        for (final Race race: RaceFactory.getInstance().getManufacturerRaces())
        {
            final JCheckBox raceCheckBox = new JCheckBox(race.getName());
            if (c2.gridx == 3)
            {
                c2.gridx = 0;
                c2.gridy++;
            }
            racePanel.add(raceCheckBox, c2);
            this.racesCheckboxes.put(race, raceCheckBox);
            c2.gridx++;
        }
        c.insets.left = 20;
        c.insets.top = 5;
        contentPanel.add(racePanel, c);

        // Put this last panel into the window
        add(contentPanel, BorderLayout.CENTER);
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
        for (final Map.Entry<Race, JCheckBox> entry: this.racesCheckboxes
            .entrySet())
        {
            final Race race = entry.getKey();
            final JCheckBox checkBox = entry.getValue();
            checkBox.setSelected(!config.isRaceIgnored(race));
        }
        final Result result = super.open();
        if (result == Result.OK)
        {
            for (final Map.Entry<Race, JCheckBox> entry: this.racesCheckboxes
                .entrySet())
            {
                final Race race = entry.getKey();
                final JCheckBox checkBox = entry.getValue();
                config
                    .setRaceIgnored(race, !checkBox.isSelected());
            }
        }
        return result;
    }
}
