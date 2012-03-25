/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.xadrian.support;

import java.io.IOException;
import java.io.InputStream;

/**
 * Input stream which reads dynamically sized integers. DynaByte streams
 * contains integer values which are formed by a dynamic number of bytes. 
 * Only the first 7 bits of each byte is part of the integer value. The 
 * highest bit of each byte defines if there are more bytes to add to the 
 * current integer.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public class DynaByteInputStream extends InputStream
{
    /** The original input stream. */
    private InputStream stream;

    /**
     * Constructor.
     * 
     * @param stream
     *            The original input stream.
     */
    public DynaByteInputStream(final InputStream stream)
    {
        this.stream = stream;
    }

    /**
     * @see java.io.InputStream#read()
     */
    @Override
    public int read() throws IOException
    {
        int b, value, bits;
        value = 0;
        bits = 0;
        do
        {
            b = this.stream.read();
            if (b == -1) return -1;
            value |= (b & 127) << bits;
            bits += 7;
        }
        while ((b & 128) != 0);
        return value;
    }
}
