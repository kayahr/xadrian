/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */
package de.ailis.xadrian.resources;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import de.ailis.xadrian.exceptions.ResourceException;

/**
 * The images used in this application.
 *
 * @author Klaus Reimer (k@ailis.de
 */
public final class Images
{
    /** The 16x16 logo */
    public static final Image LOGO_16 = createImage("xadrian-16.png");

    /** The 32x32 logo */
    public static final Image LOGO_32 = createImage("xadrian-32.png");

    /** The 48x48 logo */
    public static final Image LOGO_48 = createImage("xadrian-48.png");

    /** The 64x64 logo */
    public static final Image LOGO_64 = createImage("xadrian-64.png");

    /** The 128x128 logo */
    public static final Image LOGO_128 = createImage("xadrian-128.png");

    /** The logos */
    public static final List<Image> LOGOS = Arrays.asList(LOGO_16, LOGO_32,
        LOGO_48, LOGO_64, LOGO_128);

    /**
     * Private constructor to prevent instantiation.
     */
    private Images()
    {
        // Empty
    }

    /**
     * Creates the icon for the specified filename and returns it.
     *
     * @param filename
     *            The icon name (just the filename without extension)
     * @return The icon
     */
    private static Image createImage(final String filename)
    {
        final URL url = Images.class.getResource("/de/ailis/xadrian/images/"
            + filename);
        if (url != null)
        {
            try
            {
                return ImageIO.read(url);
            }
            catch (final IOException e)
            {
                throw new ResourceException("Unable to load image " + filename
                    + ": " + e, e);
            }
        }
        else
        {
            throw new ResourceException("Couldn't find image " + filename);
        }
    }
}
