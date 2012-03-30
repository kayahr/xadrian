/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */
package de.ailis.xadrian.components;

import java.awt.Cursor;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JMenuItem;

import de.ailis.xadrian.actions.GithubAction;
import de.ailis.xadrian.actions.GooglePlusAction;
import de.ailis.xadrian.actions.TwitterAction;
import de.ailis.xadrian.resources.Icons;
import de.ailis.xadrian.utils.SwingUtils;

/**
 * Component which displays social buttons.
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class SocialPane extends JComponent
{
    /** Serial version UID */
    private static final long serialVersionUID = 1;

    /**
     * Constructor
     */
    public SocialPane()
    {
        super();
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        Cursor cursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
       
        JMenuItem twitterButton = new JMenuItem(new TwitterAction());
        twitterButton.setIcon(Icons.TWITTER_LARGE);
        twitterButton.setText("");
        twitterButton.setCursor(cursor);
        twitterButton.setBorderPainted(false);
        twitterButton.setBorder(null);
        add(twitterButton);
        
        JMenuItem googlePlusButton = new JMenuItem(new GooglePlusAction());
        googlePlusButton.setIcon(Icons.GOOGLE_PLUS_LARGE);
        googlePlusButton.setText("");
        googlePlusButton.setCursor(cursor);
        googlePlusButton.setBorderPainted(false);
        googlePlusButton.setBorder(null);
        add(googlePlusButton);
        
        JMenuItem githubButton = new JMenuItem(new GithubAction());
        githubButton.setIcon(Icons.GITHUB_LARGE);
        githubButton.setText("");
        githubButton.setCursor(cursor);
        githubButton.setBorderPainted(false);
        githubButton.setBorder(null);
        add(githubButton);
    }

    /**
     * Tests the component.
     *
     * @param args
     *            Command line arguments
     * @throws Exception
     *             When something goes wrong
     */
    public static void main(final String[] args) throws Exception
    {
        SwingUtils.prepareGUI();

        final SocialPane component = new SocialPane();
        SwingUtils.testComponent(component);
    }
}
