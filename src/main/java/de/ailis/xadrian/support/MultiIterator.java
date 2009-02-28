/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */

package de.ailis.xadrian.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * Iterator which iterates over multiple collections
 * 
 * @param <T>
 *            The collection type
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class MultiIterator<T> implements Iterator<T>
{
    /** Iterator over all iterators */
    private final Iterator<Iterator<T>> iterators;

    /** Current iterator. Null if end of wrapped collections is reached. */
    private Iterator<T> current;


    /**
     * Creates iterator which iterates over all specified collections
     * 
     * @param collections
     *            The collections to iterate
     */

    public MultiIterator(final Collection<T>[] collections)
    {
        final List<Iterator<T>> iterators =
            new ArrayList<Iterator<T>>(collections.length);
        for (final Collection<T> collection: collections)
        {
            iterators.add(collection.iterator());
        }
        this.iterators = iterators.iterator();
        if (this.iterators.hasNext())
            this.current = this.iterators.next();
        else
            this.current = null;
    }


    /**
     * @see java.util.Iterator#hasNext()
     */

    public boolean hasNext()
    {
        if (this.current == null) return false;
        return this.current.hasNext();

    }


    /**
     * @see java.util.Iterator#next()
     */

    public T next()
    {
        if (this.current == null)
            throw new NoSuchElementException("No more elements");

        final T next = this.current.next();
        while (this.current != null && !this.current.hasNext())
            if (this.iterators.hasNext())
                this.current = this.iterators.next();
            else
                this.current = null;
        return next;
    }


    /**
     * @see java.util.Iterator#remove()
     */

    public void remove()
    {
        throw new UnsupportedOperationException("Read-only iterator");
    }
}
