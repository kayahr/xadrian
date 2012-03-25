/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.xadrian.support;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Output stream which writes dynamically sized integers. DynaByte streams
 * contains integer values which are formed by a dynamic number of bytes. 
 * Only the first 7 bits of each byte is part of the integer value. The 
 * highest bit of each byte defines if there are more bytes to add to the 
 * current integer.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public class DynaByteOutputStream extends OutputStream
{
    /** The original output stream. */
    private OutputStream stream;

    /**
     * Constructor.
     * 
     * @param stream
     *            The original output stream.
     */
    public DynaByteOutputStream(final OutputStream stream)
    {
        this.stream = stream;
    }

    /**
     * @see java.io.OutputStream#write(int)
     */
    @Override
    public void write(int b) throws IOException
    {
        do
        {
            int cur = b & 127;
            b >>>= 7;
            int v = cur;
            if (b > 0) v |= 128;
            this.stream.write(v);
        }
        while (b > 0);
    }
}
