/*
 * Copyright (C) 2010-2012 Klaus Reimer <k@ailis.de>
 * See LICENSE.txt file for licensing information.
 */
package de.ailis.xadrian.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * A wrapper collection around multiple other collections.
 *
 * @param <T>
 *            The collection type
 *
 * @author Klaus Reimer (k@ailis.de)
 */
public class MultiCollection<T> implements Collection<T>
{
    /** The wrapped collections */
    private final Collection<T>[] collections;

    /**
     * Constructor
     *
     * @param collections
     *            The collections to wrap
     */
    public MultiCollection(final Collection<T>... collections)
    {
        this.collections = collections;
    }

    /**
     * @see java.util.Collection#add(java.lang.Object)
     */
    @Override
    public boolean add(final T e)
    {
        throw new UnsupportedOperationException("Read-only collection");
    }

    /**
     * @see java.util.Collection#addAll(java.util.Collection)
     */
    @Override
    public boolean addAll(final Collection<? extends T> c)
    {
        throw new UnsupportedOperationException("Read-only collection");
    }

    /**
     * @see java.util.Collection#clear()
     */
    @Override
    public void clear()
    {
        throw new UnsupportedOperationException("Read-only collection");
    }

    /**
     * @see java.util.Collection#contains(java.lang.Object)
     */
    @Override
    public boolean contains(final Object o)
    {
        for (final Collection<T> collection: this.collections)
            if (collection.contains(o)) return true;
        return false;
    }

    /**
     * @see java.util.Collection#containsAll(java.util.Collection)
     */
    @Override
    public boolean containsAll(final Collection<?> c)
    {
        for (final Object object: c)
            if (!this.contains(object)) return false;
        return true;
    }

    /**
     * @see java.util.Collection#isEmpty()
     */
    @Override
    public boolean isEmpty()
    {
        for (final Collection<T> collection: this.collections)
        {
            if (!collection.isEmpty()) return false;
        }
        return true;
    }

    /**
     * @see java.util.Collection#iterator()
     */
    @Override
    public Iterator<T> iterator()
    {
        return new MultiIterator<T>(this.collections);
    }

    /**
     * @see java.util.Collection#remove(java.lang.Object)
     */
    @Override
    public boolean remove(final Object o)
    {
        throw new UnsupportedOperationException("Read-only collection");
    }

    /**
     * @see java.util.Collection#removeAll(java.util.Collection)
     */
    @Override
    public boolean removeAll(final Collection<?> c)
    {
        throw new UnsupportedOperationException("Read-only collection");
    }

    /**
     * @see java.util.Collection#retainAll(java.util.Collection)
     */
    @Override
    public boolean retainAll(final Collection<?> c)
    {
        throw new UnsupportedOperationException("Read-only collection");
    }

    /**
     * @see java.util.Collection#size()
     */
    @Override
    public int size()
    {
        int size = 0;
        for (final Collection<T> collection: this.collections)
            size += collection.size();
        return size;
    }

    /**
     * @see java.util.Collection#toArray()
     */
    @Override
    public Object[] toArray()
    {
        return this.toArray(new Object[size()]);
    }

    /**
     * @see java.util.Collection#toArray(Object[])
     */
    @Override
    public <E> E[] toArray(final E[] a)
    {
        final ArrayList<T> list = new ArrayList<T>(this.size());
        for (final Collection<T> collection: this.collections)
        {
            list.addAll(collection);
        }
        return list.toArray(a);
    }
}
