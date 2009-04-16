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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import de.ailis.xadrian.data.factories.FactoryFactory;
import de.ailis.xadrian.data.factories.SectorFactory;
import de.ailis.xadrian.data.factories.WareFactory;
import de.ailis.xadrian.support.I18N;
import de.ailis.xadrian.support.MultiCollection;


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
    
    /** The logger */
    private static final Log log = LogFactory.getLog(Complex.class);

    /** The single price of a complex construction kit */
    public static final int KIT_PRICE = 259696;

    /** The volume of a complex construction kit */
    public static final int KIT_VOLUME = 4250;

    /** The complex counter for the complex name generator */
    private static int complexCounter = 0;

    /** The complex name */
    private String name;

    /** The factories in this complex */
    private final List<ComplexFactory> factories;

    /** Automatically added factories in this complex */
    private final List<ComplexFactory> autoFactories;

    /** The sun power in percent */
    private Suns suns = Suns.P100;

    /** The sector where this complex is build */
    private Sector sector = null;

    /** If base complex should be calculated or not */
    private boolean addBaseComplex = false;

    /** Custom buy/sell prices in this complex */
    private final Map<Ware, Integer> customPrices;


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
        this.autoFactories = new ArrayList<ComplexFactory>();
        this.customPrices = new HashMap<Ware, Integer>();
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
        for (final ComplexFactory factory : this.factories)
            quantity += factory.getQuantity();
        for (final ComplexFactory factory : this.autoFactories)
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
        for (final ComplexFactory complexFactory : this.factories)
            price += ((long) complexFactory.getQuantity())
                * complexFactory.getFactory().getPrice();
        for (final ComplexFactory complexFactory : this.autoFactories)
            price += ((long) complexFactory.getQuantity())
                * complexFactory.getFactory().getPrice();
        return price + getTotalKitPrice();
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
     * A immutable copy of the automatically added factories in this complex.
     * 
     * @return The automatically added factories in this complex
     */

    public List<ComplexFactory> getAutoFactories()
    {
        return Collections.unmodifiableList(this.autoFactories);
    }


    /**
     * @see java.lang.Object#hashCode()
     */

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder().append(this.name).append(this.factories)
            .append(this.autoFactories).hashCode();
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
            this.factories, other.factories).append(this.suns, other.suns)
            .append(this.sector, other.sector).isEquals();
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
        calculateBaseComplex();
    }


    /**
     * Accepts the automatically added factory with the given index.
     * 
     * @param index
     *            The factory index
     */

    public void acceptFactory(final int index)
    {
        addFactory(this.autoFactories.get(index));
        calculateBaseComplex();
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
        calculateBaseComplex();
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
        calculateBaseComplex();
    }


    /**
     * Sets the suns in percent.
     * 
     * @param suns
     *            The suns in percent to set
     */

    public void setSuns(final Suns suns)
    {
        this.suns = suns;
        calculateBaseComplex();
    }


    /**
     * Returns the suns in percent.
     * 
     * @return The suns in percent
     */

    public Suns getSuns()
    {
        if (this.sector != null) return this.sector.getSuns();
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
        addFactory(new ComplexFactory(factory, 1, factory.isMine() ? 25 : 0));
        calculateBaseComplex();
    }


    /**
     * Adds the specified factory/factories to the complex.
     * 
     * @param complexFactory
     *            The factory/factories to add
     */

    private void addFactory(final ComplexFactory complexFactory)
    {
        if (!complexFactory.getFactory().isMine())
        {
            for (final ComplexFactory current : this.factories)
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
        root.addAttribute("version", "2");
        root.addAttribute("suns", Integer.toString(getSuns().getPercent()));
        if (this.sector != null)
            root.addAttribute("sector", this.sector.getId());
        root.addAttribute("addBaseComplex", Boolean
            .toString(this.addBaseComplex));
        if (!this.factories.isEmpty())
        {
            final Element factoriesE = root.addElement("complexFactories");
            for (final ComplexFactory factory : this.factories)
            {
                final Element factoryE = factoriesE
                    .addElement("complexFactory");
                factoryE.addAttribute("factory", factory.getFactory().getId());
                factoryE.addAttribute("quantity", Integer.toString(factory
                    .getQuantity()));
                factoryE.addAttribute("yield", Integer.toString(factory
                    .getYield()));
            }
        }
        if (!this.customPrices.isEmpty())
        {
            final Element waresE = root.addElement("complexWares");
            for (final Map.Entry<Ware, Integer> entry : this.customPrices
                .entrySet())
            {
                final Ware ware = entry.getKey();
                final int price = entry.getValue();
                final Element wareE = waresE.addElement("complexWare");
                wareE.addAttribute("ware", ware.getId());
                wareE
                    .addAttribute("use", Boolean.valueOf(price > 0).toString());
                wareE.addAttribute("price", Integer.toString(Math.abs(price)));
            }
        }
        return document;
    }


    /**
     * Loads a complex from the specified XML document and returns it.
     * 
     * @param document
     *            The XML document
     * @return The complex
     * @throws DocumentException
     *             If XML file could not be read
     */

    public static Complex fromXML(final Document document)
        throws DocumentException
    {
        final Element root = document.getRootElement();
        final Complex complex = new Complex();
        final FactoryFactory factoryFactory = FactoryFactory.getInstance();
        final SectorFactory sectorFactory = SectorFactory.getInstance();
        final WareFactory wareFactory = WareFactory.getInstance();

        // Check the version
        final String versionStr = root.attributeValue("version");
        int version = 1;
        if (versionStr != null) version = Integer.parseInt(versionStr);
        if (version > 2) throw new DocumentException("File is too new");

        complex.setSuns(Suns.valueOf(Integer.parseInt(root
            .attributeValue("suns"))));
        complex.setSector(sectorFactory
            .getSector(root.attributeValue("sector")));
        complex.setAddBaseComplex(Boolean.parseBoolean(root
            .attributeValue("addBaseComplex")));

        // Get the factories parent element (In older version this was the root
        // node)
        Element factoriesE = root.element("complexFactories");
        if (factoriesE == null) factoriesE = root;

        // Read the complex factories
        for (final Object item : factoriesE.elements("complexFactory"))
        {
            final Element element = (Element) item;
            final Factory factory = factoryFactory.getFactory(element
                .attributeValue("factory"));
            final int yield = Integer.parseInt(element.attributeValue("yield"));
            final int quantity = Integer.parseInt(element
                .attributeValue("quantity"));
            complex.addFactory(new ComplexFactory(factory, quantity, yield));
        }
        
        // Read the complex wares
        final Element waresE = root.element("complexWares");
        if (waresE != null)
        {
            complex.customPrices.clear();
            for (final Object item: waresE.elements("complexWare"))
            {
                final Element element = (Element) item;
                final Ware ware = wareFactory.getWare(element.attributeValue("ware"));
                final boolean use = Boolean.parseBoolean(element.attributeValue("use"));
                final int price = Integer.parseInt(element.attributeValue("price"));
                complex.customPrices.put(ware, use ? price : -price);
            }
        }

        complex.calculateBaseComplex();
        return complex;
    }


    /**
     * Returns all factories (Manually and automatically added ones):
     * 
     * @return All factories
     */

    @SuppressWarnings("unchecked")
    private Collection<ComplexFactory> getAllFactories()
    {
        return new MultiCollection<ComplexFactory>(this.factories,
            this.autoFactories);
    }


    /**
     * Returns the products this complex produces in one hour.
     * 
     * @return The products per hour.
     */

    public Collection<Product> getProductsPerHour()
    {
        final Map<String, Product> products = new HashMap<String, Product>();
        for (final ComplexFactory factory : getAllFactories())
        {
            final Product product = factory.getProductPerHour(getSuns());
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
        for (final ComplexFactory factory : getAllFactories())
        {
            for (final Product resource : factory
                .getResourcesPerHour(getSuns()))
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
        final Map<String, ComplexWare> wares = new HashMap<String, ComplexWare>();

        // Add the products
        for (final Product product : getProductsPerHour())
        {
            final Ware ware = product.getWare();
            final String wareId = ware.getId();
            wares.put(wareId, new ComplexWare(ware, product.getQuantity(), 0,
                getWarePrice(ware)));
        }

        // Add the resources
        for (final Product resource : getResourcesPerHour())
        {
            final Ware ware = resource.getWare();
            final String wareId = ware.getId();
            ComplexWare complexWare = wares.get(wareId);
            if (complexWare == null)
                complexWare = new ComplexWare(ware, 0, resource.getQuantity(),
                    getWarePrice(ware));
            else
                complexWare = new ComplexWare(ware, complexWare.getProduced(),
                    resource.getQuantity(), getWarePrice(ware));
            wares.put(wareId, complexWare);
        }

        final List<ComplexWare> result = new ArrayList<ComplexWare>(wares
            .values());
        Collections.sort(result);
        return result;
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
        for (final ComplexWare complexWare : getWares())
        {
            profit += complexWare.getProfit();
        }
        return profit;
    }


    /**
     * Returns the number of needed complex construction kits in this complex.
     * 
     * @return The number of needed complex construction kits.
     */

    public int getKitQuantity()
    {
        return Math.max(0, getTotalQuantity() - 1);
    }


    /**
     * Returns the price of a single complex construction kit.
     * 
     * @return The price of a single complex construction kit
     */

    public int getKitPrice()
    {
        return KIT_PRICE;
    }


    /**
     * Returns the total price of all needed complex construction kits.
     * 
     * @return The total price of all needed complex construction kits
     */

    public int getTotalKitPrice()
    {
        return getKitQuantity() * getKitPrice();
    }


    /**
     * Calculates and adds the factories needed to keep the factories of this
     * complex running stable.
     */

    private void calculateBaseComplex()
    {
        // First of all remove all automatically added factories
        this.autoFactories.clear();

        if (!this.addBaseComplex) return;

        // Repeat adding base complex factories until all needs are fulfilled
        while (true)
        {
            if (!addBaseComplex()) break;
        }
    }


    /**
     * Updates the base complex.
     */

    public void updateBaseComplex()
    {
        this.calculateBaseComplex();
    }


    /**
     * Searches for the first unfulfilled resource need (which is not a mineral)
     * and adds the necessary factories for this. If a need was found (and
     * fixed) then this method returns true. If all needs are already fulfilled
     * then it returns false.
     * 
     * @return True if a need was found and fixed, false if everything is
     *         finished
     */

    private boolean addBaseComplex()
    {
        for (final ComplexWare ware : getWares())
        {
            // We are not going to add mines
            if (ware.getWare().getId().equals("siliconWafers")) continue;
            if (ware.getWare().getId().equals("ore")) continue;

            // If the current ware has missing units then add the necessary
            // factories for this ware and then restart the adding of factories
            if (ware.getMissing() > 0)
            {
                if (!addBaseComplexForWare(ware)) continue;
                return true;
            }

        }
        return false;
    }


    /**
     * Adds the factories needed to fulfill the need of the specified complex
     * ware.
     * 
     * @param complexWare
     *            The complex ware for which factories must be added
     * @return True if a new factories were added, false if this was not
     *         possible
     */

    private boolean addBaseComplexForWare(final ComplexWare complexWare)
    {
        final Ware ware = complexWare.getWare();
        final FactoryFactory factoryFactory = FactoryFactory.getInstance();

        // Remove all automatically added factories which produces the
        // specified ware and calculate the real need which must be
        // fulfilled.
        double need = complexWare.getMissing();
        final double oldNeed = need;
        for (final ComplexFactory complexFactory : new ArrayList<ComplexFactory>(
            this.autoFactories))
        {
            if (complexFactory.getFactory().getProduct().getWare().equals(ware))
            {
                need += complexFactory.getProductPerHour(getSuns())
                    .getQuantity();
                this.autoFactories.remove(complexFactory);
            }
        }

        // Determine the available factory sizes
        final FactorySize[] sizes = factoryFactory.getFactorySizes(ware)
            .toArray(new FactorySize[0]);

        // Abort if no factories were found
        if (sizes.length == 0) return false;

        // Get the cheapest factories for the sizes
        final Map<FactorySize, Factory> factories = new HashMap<FactorySize, Factory>();
        for (final FactorySize size : sizes)
        {
            factories.put(size, factoryFactory.getCheapestFactory(ware, size));
        }

        // Get the smallest possible production quantity
        final double minProduction = factories.get(sizes[0]).getProductPerHour(
            getSuns(), 0).getQuantity();

        // Iterate the available sizes (from largest to smallest) and add
        // the factories producing an adequate number of products
        for (int i = sizes.length - 1; i >= 0; i--)
        {
            final FactorySize size = sizes[i];
            final Factory factory = factories.get(size);
            final double product = factory.getProductPerHour(getSuns(), 0)
                .getQuantity();

            // Calculate the number of factories of the current size needed
            log.debug("Need " + need + " units of " + ware + ". Considering " + factory + " which produces " + product + " units");
            final int quantity = (int) Math.floor((need + minProduction - 0.1)
                / product);

            // Add the number of factories and decrease the need
            if (quantity > 0)
            {
                log.debug("Adding " + quantity + "x " + factory);
                this.autoFactories
                    .add(new ComplexFactory(factory, quantity, 0));
                need -= quantity * product;
            }
            else log.debug("Not adding any " + factory);
        }
        if (need == oldNeed)
        {
            log.debug("Unable to calculate best matching factory. Aborting");
            return false;
        }
        return true;
    }


    /**
     * Toggles the addition of automatically calculated base complex.
     */

    public void toggleAddBaseComplex()
    {
        this.addBaseComplex = !this.addBaseComplex;
        calculateBaseComplex();
    }


    /**
     * Enables or disabled base complex addition.
     * 
     * @param addBaseComplex
     *            True if base complex should be added, false if not
     */

    public void setAddBaseComplex(final boolean addBaseComplex)
    {
        this.addBaseComplex = addBaseComplex;
    }


    /**
     * Checks whether a base complex was added or not.
     * 
     * @return True if a base complex was added. False if not.
     */

    public boolean isAddBaseComplex()
    {
        return this.addBaseComplex;
    }


    /**
     * Returns the storage capacities.
     * 
     * @return The storage capacities.
     */

    public Collection<Capacity> getCapacities()
    {
        final Map<String, Capacity> capacities = new HashMap<String, Capacity>();
        for (final ComplexFactory factory : getAllFactories())
        {
            for (final Capacity capacity : factory.getCapacities())
            {
                final Ware ware = capacity.getWare();
                final Capacity mapCapacity = capacities.get(ware.getId());
                if (mapCapacity == null)
                    capacities.put(ware.getId(), capacity);
                else
                    capacities.put(ware.getId(), new Capacity(ware, mapCapacity
                        .getQuantity()
                        + capacity.getQuantity()));
            }
        }
        final List<Capacity> result = new ArrayList<Capacity>(capacities
            .values());
        Collections.sort(result);
        return result;
    }


    /**
     * Returns the total storage capacity
     * 
     * @return The total storage capacity;
     */

    public long getTotalCapacity()
    {
        long total = 0;
        for (final Capacity capacity : getCapacities())
            total += capacity.getQuantity();
        return total;
    }


    /**
     * Returns the total storage volume
     * 
     * @return The total storage volume;
     */

    public long getTotalStorageVolume()
    {
        long total = 0;
        for (final Capacity capacity : getCapacities())
            total += capacity.getVolume();
        return total;
    }


    /**
     * Sets the sector in which to build this complex
     * 
     * @param sector
     *            The sector to set
     */

    public void setSector(final Sector sector)
    {
        if ((sector != null && !sector.equals(this.sector))
            || (sector == null && this.sector != null))
        {
            this.sector = sector;
            calculateBaseComplex();
        }
    }


    /**
     * Returns the sector in which this complex is build.
     * 
     * @return The sector
     */

    public Sector getSector()
    {
        return this.sector;
    }


    /**
     * Returns the factory shopping list.
     * 
     * @return The factory shopping list
     */

    public ShoppingList getShoppingList()
    {
        final ShoppingList list = new ShoppingList(this.sector == null ? null
            : this.sector.getNearestShipyardSector());
        for (final ComplexFactory factory : this.factories)
        {
            list.addItem(new ShoppingListItem(factory.getFactory(), factory
                .getQuantity(), this.sector == null ? null : factory
                .getFactory().getNearestManufacturer(this.sector)));
        }
        for (final ComplexFactory factory : this.autoFactories)
        {
            list.addItem(new ShoppingListItem(factory.getFactory(), factory
                .getQuantity(), this.sector == null ? null : factory
                .getFactory().getNearestManufacturer(this.sector)));
        }
        return list;
    }


    /**
     * Returns the price for the specified ware. If the price has a custom price
     * then this one is returned. If not then the standard average price of the
     * ware is returned.
     * 
     * @param ware
     *            The ware
     * @return The price
     */

    public int getWarePrice(final Ware ware)
    {
        final Integer price = this.customPrices.get(ware);
        if (price == null) return ware.getAvgPrice();
        if (price < 0) return 0;
        return price;
    }


    /**
     * Returns the map with custom prices.
     * 
     * @return The map with custom prices
     */

    public Map<Ware, Integer> getCustomPrices()
    {
        return Collections.unmodifiableMap(this.customPrices);
    }


    /**
     * Sets a new map with custom prices.
     * 
     * @param customPrices
     *            The new map with custom prices
     */

    public void setCustomPrices(final Map<Ware, Integer> customPrices)
    {
        this.customPrices.clear();
        this.customPrices.putAll(customPrices);
    }
}
