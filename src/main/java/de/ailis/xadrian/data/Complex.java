/*
 * $Id$
 * Copyright (C) 2009 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import de.ailis.xadrian.data.Factory.Type;
import de.ailis.xadrian.data.factories.FactoryFactory;
import de.ailis.xadrian.support.I18N;


/**
 * A complex
 * 
 * @author Klaus Reimer (k@ailis.de)
 * @version $Revision$
 */

public class Complex implements Serializable
{
    /** Serial version UID */
    private static final long serialVersionUID = 2128684141345704703L;

    /** The complex counter for the complex name generator */
    private static int complexCounter = 0;

    /** The complex name */
    private String name;

    /** The factories in this complex */
    private final List<ComplexFactory> factories;

    /** The sun power in percent */
    private int suns = 100;


    /**
     * Constructor
     */

    public Complex()
    {
        this(createComplexName());
    }


    /**
     * Constructor
     * 
     * @param name
     *            The complex name
     */

    public Complex(final String name)
    {
        this.name = name;
        this.factories = new ArrayList<ComplexFactory>();
    }


    /**
     * Returns a new name for a complex.
     * 
     * @return A new complex name
     */

    private static String createComplexName()
    {
        complexCounter++;
        return String.format(I18N.getString("complex.nameTemplate"),
            complexCounter);
    }


    /**
     * Returns the name.
     * 
     * @return The name
     */

    public String getName()
    {
        return this.name;
    }


    /**
     * Sets the complex name.
     * 
     * @param name
     *            The complex name to set
     */

    public void setName(final String name)
    {
        this.name = name;
    }

    /**
     * Returns the total number of factories in the complex
     * 
     * @return The total number of factories in the complex
     */

    public int getTotalQuantity()
    {
        int quantity = 0;
        for (final ComplexFactory factory: this.factories)
            quantity += factory.getQuantity();
        return quantity;
    }


    /**
     * Returns the total factory price.
     * 
     * @return The total factory price
     */

    public long getTotalPrice()
    {
        long price = 0;
        for (final ComplexFactory complexFactory: this.factories)
            price +=
                ((long) complexFactory.getQuantity())
                    * complexFactory.getFactory().getPrice();
        return price;
    }


    /**
     * A immutable copy of the factories in this complex.
     * 
     * @return The factories in this complex
     */

    public List<ComplexFactory> getFactories()
    {
        return Collections.unmodifiableList(this.factories);
    }


    /**
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.name).append(this.factories)
            .hashCode();
    }


    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */

    @Override
    public boolean equals(final Object obj)
    {
        if (obj == null) return false;
        if (obj == this) return true;
        if (obj.getClass() != getClass()) return false;
        final Complex other = (Complex) obj;
        return new EqualsBuilder().append(this.name, other.name).append(
            this.factories, other.factories).isEquals();
    }


    /**
     * Removes the factory with the given index.
     * 
     * @param index
     *            The factory index
     */

    public void removeFactory(final int index)
    {
        this.factories.remove(index);
    }


    /**
     * Returns the quantity of the factory with the given index.
     * 
     * @param index
     *            The factory index
     * @return The quantity
     */

    public int getQuantity(final int index)
    {
        return this.factories.get(index).getQuantity();
    }


    /**
     * Sets the quantity of the factory with the given index to the specified
     * quantity.
     * 
     * @param index
     *            The factory index
     * @param quantity
     *            The quantity to set
     */

    public void setQuantity(final int index, final int quantity)
    {
        this.factories.get(index).setQuantity(quantity);
    }


    /**
     * Returns the yield of the factory with the given index.
     * 
     * @param index
     *            The factory index
     * @return The yield
     */

    public int getYield(final int index)
    {
        return this.factories.get(index).getYield();
    }


    /**
     * Sets the yield of the factory with the given index to the specified
     * yield.
     * 
     * @param index
     *            The factory index
     * @param yield
     *            The yield to set
     */

    public void setYield(final int index, final int yield)
    {
        this.factories.get(index).setYield(yield);
    }


    /**
     * Sets the suns in percent.
     * 
     * @param suns
     *            The suns in percent to set
     */

    public void setSuns(final int suns)
    {
        this.suns = suns;
    }


    /**
     * Returns the suns in percent.
     * 
     * @return The suns in percent
     */

    public int getSuns()
    {
        return this.suns;
    }


    /**
     * Adds a factory to the complex.
     * 
     * @param factory
     *            The factory to add
     */

    public void addFactory(final Factory factory)
    {
        addFactory(new ComplexFactory(factory, 1,
            factory.getType() == Type.MINE ? 25 : 0));
    }


    /**
     * Adds the specified factory/factories to the complex.
     * 
     * @param complexFactory
     *            The factory/factories to add
     */

    private void addFactory(final ComplexFactory complexFactory)
    {
        if (complexFactory.getFactory().getType() != Type.MINE)
        {
            for (final ComplexFactory current: this.factories)
            {
                if (current.getFactory().equals(complexFactory.getFactory())
                    && current.getYield() == complexFactory.getYield())
                {
                    current.addQuantity(complexFactory.getQuantity());
                    return;
                }
            }
        }
        this.factories.add(complexFactory);
        Collections.sort(this.factories);
    }


    /**
     * Converts the complex into XML and returns it.
     * 
     * @return The complex as XML
     */

    public Document toXML()
    {
        final Document document = DocumentHelper.createDocument();
        final Element root = document.addElement("complex");
        root.addAttribute("suns", Integer.toString(this.suns));
        for (final ComplexFactory factory: this.factories)
        {
            final Element factoryE = root.addElement("complexFactory");
            factoryE.addAttribute("factory", factory.getFactory().getId());
            factoryE.addAttribute("quantity", Integer.toString(factory
                .getQuantity()));
            factoryE.addAttribute("yield", Integer
                .toString(factory.getYield()));
        }
        return document;
    }


    /**
     * Loads a complex from the specified XML document and returns it.
     * 
     * @param document
     *            The XML document
     * @return The complex
     */

    public static Complex fromXML(final Document document)
    {
        final Element root = document.getRootElement();
        final Complex complex = new Complex();
        final FactoryFactory factoryFactory = FactoryFactory.getInstance();
        complex.setSuns(Integer.parseInt(root.attributeValue("suns")));
        for (final Object item: root.elements("complexFactory"))
        {
            final Element element = (Element) item;
            final Factory factory =
                factoryFactory.getFactory(element.attributeValue("factory"));
            final int yield =
                Integer.parseInt(element.attributeValue("yield"));
            final int quantity =
                Integer.parseInt(element.attributeValue("quantity"));
            complex.addFactory(new ComplexFactory(factory, quantity, yield));
        }
        return complex;
    }


    /**
     * Returns the products this complex produces in one hour.
     * 
     * @return The products per hour.
     */

    public Collection<Product> getProductsPerHour()
    {
        final Map<String, Product> products = new HashMap<String, Product>();
        for (final ComplexFactory factory: this.factories)
        {
            final Product product = factory.getProductPerHour(this.suns);
            final Ware ware = product.getWare();
            final Product mapProduct = products.get(ware.getId());
            if (mapProduct == null)
                products.put(ware.getId(), product);
            else
                products.put(ware.getId(), new Product(ware, mapProduct
                    .getQuantity()
                    + product.getQuantity()));
        }
        return products.values();
    }


    /**
     * Returns the resources this complex needs in one hour.
     * 
     * @return The needed resources per hour.
     */

    public Collection<Product> getResourcesPerHour()
    {
        final Map<String, Product> resources = new HashMap<String, Product>();
        for (final ComplexFactory factory: this.factories)
        {
            for (final Product resource: factory
                .getResourcesPerHour(this.suns))
            {
                final Ware ware = resource.getWare();
                final Product mapResource = resources.get(ware.getId());
                if (mapResource == null)
                    resources.put(ware.getId(), resource);
                else
                    resources.put(ware.getId(), new Product(ware, mapResource
                        .getQuantity()
                        + resource.getQuantity()));
            }
        }
        return resources.values();
    }

    
    /**
     * Returns the list of complex wares (Produced and needed).
     * 
     * @return The list of complex wares
     */
    
    public Collection<ComplexWare> getWares()
    {
        final Map<String, ComplexWare> wares =
            new HashMap<String, ComplexWare>();

        // Add the products
        for (final Product product: getProductsPerHour())
        {
            final String wareId = product.getWare().getId();
            wares.put(wareId, new ComplexWare(product.getWare(), product
                .getQuantity(), 0));
        }

        // Add the resources
        for (final Product resource: getResourcesPerHour())
        {
            final String wareId = resource.getWare().getId();
            ComplexWare complexWare = wares.get(wareId);
            if (complexWare == null)
                complexWare =
                    new ComplexWare(resource.getWare(), 0, resource
                        .getQuantity());
            else
                complexWare =
                    new ComplexWare(resource.getWare(), complexWare
                        .getProduced(), resource.getQuantity());
            wares.put(wareId, complexWare);
        }

        return wares.values();
    }
    
    
    /**
     * Returns the profit of this complex.
     * 
     * @return The profit
     */
    
    public double getProfit()
    {
        double profit;
        
        profit = 0;
        for (final ComplexWare complexWare: getWares())
        {
            profit += complexWare.getProfit();
        }
        return profit;
    }
}
