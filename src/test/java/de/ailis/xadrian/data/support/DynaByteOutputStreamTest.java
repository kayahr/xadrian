/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.xadrian.data.support;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import de.ailis.xadrian.support.DynaByteInputStream;

/**
 * Tests the DynaByteInputStream
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public class DynaByteOutputStreamTest
{
    /**
     * Tests the read method.
     * 
     * @throws IOException
     *             When stream reported an error.
     */
    @Test
    public void testRead() throws IOException
    {
        byte[] data =
            { 0, 126, 127, (byte) 128, 127, (byte) 128, 0, (byte) 129,
                (byte) 129, (byte) 129, 1 };

        InputStream stream = new DynaByteInputStream(
            new ByteArrayInputStream(data));
        try
        {
            assertEquals(0, stream.read());
            assertEquals(126, stream.read());
            assertEquals(127, stream.read());
            assertEquals(16256, stream.read());
            assertEquals(0, stream.read());
            assertEquals(2113665, stream.read());
            assertEquals(-1, stream.read());
        }
        finally
        {
            stream.close();
        }
    }
}
