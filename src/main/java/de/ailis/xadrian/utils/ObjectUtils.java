/*
 * Copyright (C) 2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information.
 */

package de.ailis.xadrian.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.xml.bind.DatatypeConverter;

/**
 * Static object utility methods.
 * 
 * @author Klaus Reimer (k@ailis.de)
 */
public final class ObjectUtils
{
    /**
     * Private constructor to prevent instantiation
     */
    private ObjectUtils()
    {
        // Empty
    }

    /**
     * Deserializes an object from the specified base64 encoded string.
     * 
     * @param s
     *            The base64 encoded string.
     * @return The deserialized object.
     */
    public static Object fromString(String s)
    {
        try
        {

            return new ObjectInputStream(new ByteArrayInputStream(
                DatatypeConverter.parseBase64Binary(s)))
                .readObject();
        }
        catch (Exception e)
        {
            // Can't happen
            throw new RuntimeException(e.toString(), e);
        }
    }

    /**
     * Converts the specified object into a base64 encoded string.
     * 
     * @param o
     *            The object to convert
     * @return The object as a base64 encoded string.
     */
    public static String toString(Serializable o)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try
        {
            ObjectOutputStream objectStream = new ObjectOutputStream(stream);
            objectStream.writeObject(o);
            objectStream.close();
            return DatatypeConverter.printBase64Binary(stream.toByteArray());
        }
        catch (IOException e)
        {
            // Can't happen
            throw new RuntimeException(e.toString(), e);
        }
    }
}
