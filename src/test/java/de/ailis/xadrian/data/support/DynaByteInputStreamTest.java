/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt for licensing information.
 */

package de.ailis.xadrian.data.support;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.Test;

import de.ailis.xadrian.support.DynaByteOutputStream;

/**
 * Tests the DynaByteInputStream
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public class DynaByteInputStreamTest
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
        ByteArrayOutputStream arrayStream = new ByteArrayOutputStream();
        OutputStream stream = new DynaByteOutputStream(arrayStream);
        stream.write(0);
        stream.write(126);
        stream.write(127);
        stream.write(16256);
        stream.write(0);
        stream.write(2113665);
        byte[] data = arrayStream.toByteArray();
        assertEquals(10, data.length);
        assertEquals(0, data[0]);
        assertEquals(126, data[1]);
        assertEquals(127, data[2]);
        assertEquals((byte) 128, data[3]);
        assertEquals(127, data[4]);
        assertEquals(0, data[5]);
        assertEquals((byte) 129, data[6]);
        assertEquals((byte) 129, data[7]);
        assertEquals((byte) 129, data[8]);
        assertEquals(1, data[9]);
    }
}
