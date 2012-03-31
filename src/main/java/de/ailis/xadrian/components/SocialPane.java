/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */
package de.ailis.xadrian.components;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JComponent;

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
       
        add(new IconButton(new TwitterAction(), Icons.TWITTER_LARGE));
        add(new IconButton(new GooglePlusAction(), Icons.GOOGLE_PLUS_LARGE));
        add(new IconButton(new GithubAction(), Icons.GITHUB_LARGE));
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
