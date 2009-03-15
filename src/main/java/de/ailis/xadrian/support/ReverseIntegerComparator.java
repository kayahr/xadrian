/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.support;

import java.util.Comparator;


/**
 * Reverse integer comparator
 *
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class ReverseIntegerComparator implements Comparator<Integer>
{
    /**
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    
    @Override
    public int compare(final Integer o1, final Integer o2)
    {
        return o2.compareTo(o1);
    }
}
