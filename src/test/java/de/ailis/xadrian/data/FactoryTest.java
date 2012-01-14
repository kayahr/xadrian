/*
 * Copyright (C) 2010 Klaus Reimer <k@ailis.de>
 * See LICENSE.TXT for licensing information
 */

package de.ailis.xadrian.data;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.BeforeClass;
import org.junit.Test;

import de.ailis.xadrian.data.factories.FactoryFactory;
import de.ailis.xadrian.data.factories.GameFactory;
import de.ailis.xadrian.data.factories.RaceFactory;
import de.ailis.xadrian.data.factories.SunFactory;
import de.ailis.xadrian.data.factories.WareFactory;
import de.ailis.xadrian.support.Config;


/**
 * Tests the data of some factories.
 *
 * @author Klaus Reimer (k@ailis.de)
 */

public class FactoryTest
{
    /** The game. */
    private static Game game;
    
    /** The factory factory */
    private static FactoryFactory factoryFactory;

    /** The ware factory */
    private static WareFactory wareFactory;

    /** The race factory */
    private static RaceFactory raceFactory;

    /** The suns factory */
    private static SunFactory sunsFactory;

    /** Energy cells */
    private static Ware energy;

    /** Silicon wafers */
    private static Ware silicon;

    /** Ore */
    private static Ware ore;

    /** Crystals */
    private static Ware crystals;

    /** Suns with 0% */
    private static Sun P0;

    /** Suns with 100% */
    private static Sun P100;

    /** Suns with 150% */
    private static Sun P150;

    /** Suns with 300% */
    private static Sun P300;


    /**
     * Initializes the test
     */

    @BeforeClass
    public static void init()
    {
        game = GameFactory.getInstance().getGame("x3tc");
        factoryFactory = game.getFactoryFactory();
        wareFactory = game.getWareFactory();
        raceFactory = game.getRaceFactory();
        sunsFactory = game.getSunFactory();
        energy = wareFactory.getWare("energyCells");
        silicon = wareFactory.getWare("siliconWafers");
        ore = wareFactory.getWare("ore");
        crystals = wareFactory.getWare("crystals");
        P0 = sunsFactory.getSun(0);
        P100 = sunsFactory.getSun(100);
        P150 = sunsFactory.getSun(150);
        P300 = sunsFactory.getSun(300);        
        Config.getInstance().reset();
    }


    /**
     * Checks if the Teladi silicon mines are really the cheapest.
     */

    @Test
    public void testCheapestSiliconMines()
    {
        final Race terran = raceFactory.getRace("terran");
        assertEquals(terran, factoryFactory.getCheapestFactory(silicon,
            FactorySize.M).getRace());
        assertEquals(terran, factoryFactory.getCheapestFactory(silicon,
            FactorySize.L).getRace());
    }


    /**
     * Checks if the Split ore mines are really the cheapest.
     */

    @Test
    public void testCheapestOreMines()
    {
        final Race terran = raceFactory.getRace("terran");
        assertEquals(terran, factoryFactory.getCheapestFactory(ore,
            FactorySize.M).getRace());
        assertEquals(terran, factoryFactory.getCheapestFactory(ore,
            FactorySize.L).getRace());
    }


    /**
     * Checks if the Boron power plants are really the cheapest.
     */

    @Test
    public void testCheapestPowerPlants()
    {
        final Race boron = raceFactory.getRace("boron");
        assertEquals(boron, factoryFactory.getCheapestFactory(energy,
            FactorySize.M).getRace());
        assertEquals(boron, factoryFactory.getCheapestFactory(energy,
            FactorySize.L).getRace());
        assertEquals(boron, factoryFactory.getCheapestFactory(energy,
            FactorySize.XL).getRace());
    }


    /**
     * Tests the product and resources of the Silicon Mine M with yield 100.
     */

    @Test
    public void testSiliconMineM100()
    {
        final Factory mine = factoryFactory.getCheapestFactory(silicon,
            FactorySize.M);
        final Product product = mine.getProductPerHour(P150, 100);
        assertEquals(silicon, product.getWare());
        assertEquals(300, Math.round(product.getQuantity()));
        final Collection<Product> resources = mine
            .getResourcesPerHour(P150, 100);
        assertEquals(1, resources.size());
        assertEquals(energy, resources.iterator().next().getWare());
        assertEquals(7200, Math.round(resources.iterator().next().getQuantity()));
    }


    /**
     * Tests the product and resources of the Silicon Mine M with yield 25.
     */

    @Test
    public void testSiliconMineM25()
    {
        final Factory mine = factoryFactory.getCheapestFactory(silicon,
            FactorySize.M);
        final Product product = mine.getProductPerHour(P150, 25);
        assertEquals(silicon, product.getWare());
        assertEquals(77, Math.round(product.getQuantity()));
        final Collection<Product> resources = mine
            .getResourcesPerHour(P150, 25);
        assertEquals(1, resources.size());
        assertEquals(energy, resources.iterator().next().getWare());
        assertEquals(1858, Math.round(resources.iterator().next().getQuantity()));
    }


    /**
     * Tests the product and resources of the Silicon Mine M with yield 0.
     */

    @Test
    public void testSiliconMineM0()
    {
        final Factory mine = factoryFactory.getCheapestFactory(silicon,
            FactorySize.M);
        final Product product = mine.getProductPerHour(P150, 0);
        assertEquals(silicon, product.getWare());
        assertEquals(3, Math.round(product.getQuantity()));
        final Collection<Product> resources = mine
            .getResourcesPerHour(P150, 0);
        assertEquals(1, resources.size());
        assertEquals(energy, resources.iterator().next().getWare());
        assertEquals(72, Math.round(resources.iterator().next().getQuantity()));
    }


    /**
     * Tests the product and resources of the Silicon Mine L with yield 100.
     */

    @Test
    public void testSiliconMineL100()
    {
        final Factory mine = factoryFactory.getCheapestFactory(silicon,
            FactorySize.L);
        final Product product = mine.getProductPerHour(P150, 100);
        assertEquals(silicon, product.getWare());
        assertEquals(750, Math.round(product.getQuantity()));
        final Collection<Product> resources = mine
            .getResourcesPerHour(P150, 100);
        assertEquals(1, resources.size());
        assertEquals(energy, resources.iterator().next().getWare());
        assertEquals(18000, Math.round(resources.iterator().next().getQuantity()));
    }


    /**
     * Tests the product and resources of the Silicon Mine L with yield 25.
     */

    @Test
    public void testSiliconMineL25()
    {
        final Factory mine = factoryFactory.getCheapestFactory(silicon,
            FactorySize.L);
        final Product product = mine.getProductPerHour(P150, 25);
        assertEquals(silicon, product.getWare());
        assertEquals(194, Math.round(product.getQuantity()));
        final Collection<Product> resources = mine
            .getResourcesPerHour(P150, 25);
        assertEquals(1, resources.size());
        assertEquals(energy, resources.iterator().next().getWare());
        assertEquals(4645, Math.round(resources.iterator().next().getQuantity()));
    }


    /**
     * Tests the product and resources of the Silicon Mine L with yield 0.
     */

    @Test
    public void testSiliconMineL0()
    {
        final Factory mine = factoryFactory.getCheapestFactory(silicon,
            FactorySize.L);
        final Product product = mine.getProductPerHour(P150, 0);
        assertEquals(silicon, product.getWare());
        assertEquals(7, Math.round(product.getQuantity()));
        final Collection<Product> resources = mine
            .getResourcesPerHour(P150, 0);
        assertEquals(1, resources.size());
        assertEquals(energy, resources.iterator().next().getWare());
        assertEquals(180, Math.round(resources.iterator().next().getQuantity()));
    }


    /**
     * Tests the product and resources of the Ore Mine M with yield 100.
     */

    @Test
    public void testOreMineM100()
    {
        final Factory mine = factoryFactory.getCheapestFactory(ore,
            FactorySize.M);
        final Product product = mine.getProductPerHour(P150, 100);
        assertEquals(ore, product.getWare());
        assertEquals(1200, Math.round(product.getQuantity()));
        final Collection<Product> resources = mine
            .getResourcesPerHour(P150, 100);
        assertEquals(1, resources.size());
        assertEquals(energy, resources.iterator().next().getWare());
        assertEquals(7200, Math.round(resources.iterator().next().getQuantity()));
    }


    /**
     * Tests the product and resources of the Ore Mine M with yield 25.
     */

    @Test
    public void testOreMineM25()
    {
        final Factory mine = factoryFactory.getCheapestFactory(ore,
            FactorySize.M);
        final Product product = mine.getProductPerHour(P150, 25);
        assertEquals(ore, product.getWare());
        assertEquals(300, Math.round(product.getQuantity()));
        final Collection<Product> resources = mine
            .getResourcesPerHour(P150, 25);
        assertEquals(1, resources.size());
        assertEquals(energy, resources.iterator().next().getWare());
        assertEquals(1800, Math.round(resources.iterator().next().getQuantity()));
    }


    /**
     * Tests the product and resources of the Ore Mine M with yield 0.
     */

    @Test
    public void testOreMineM0()
    {
        final Factory mine = factoryFactory.getCheapestFactory(ore,
            FactorySize.M);
        final Product product = mine.getProductPerHour(P150, 0);
        assertEquals(ore, product.getWare());
        assertEquals(12, Math.round(product.getQuantity()));
        final Collection<Product> resources = mine
            .getResourcesPerHour(P150, 0);
        assertEquals(1, resources.size());
        assertEquals(energy, resources.iterator().next().getWare());
        assertEquals(72, Math.round(resources.iterator().next().getQuantity()));
    }


    /**
     * Tests the product and resources of the Ore Mine L with yield 100.
     */

    @Test
    public void testOreMineL100()
    {
        final Factory mine = factoryFactory.getCheapestFactory(ore,
            FactorySize.L);
        final Product product = mine.getProductPerHour(P150, 100);
        assertEquals(ore, product.getWare());
        assertEquals(3000, Math.round(product.getQuantity()));
        final Collection<Product> resources = mine
            .getResourcesPerHour(P150, 100);
        assertEquals(1, resources.size());
        assertEquals(energy, resources.iterator().next().getWare());
        assertEquals(18000, Math.round(resources.iterator().next().getQuantity()));
    }


    /**
     * Tests the product and resources of the Ore Mine L with yield 25.
     */

    @Test
    public void testOreMineL25()
    {
        final Factory mine = factoryFactory.getCheapestFactory(ore,
            FactorySize.L);
        final Product product = mine.getProductPerHour(P150, 25);
        assertEquals(ore, product.getWare());
        assertEquals(750, Math.round(product.getQuantity()));
        final Collection<Product> resources = mine
            .getResourcesPerHour(P150, 25);
        assertEquals(1, resources.size());
        assertEquals(energy, resources.iterator().next().getWare());
        assertEquals(4500, Math.round(resources.iterator().next().getQuantity()));
    }


    /**
     * Tests the product and resources of the Ore Mine L with yield 0.
     */

    @Test
    public void testOreMineL0()
    {
        final Factory mine = factoryFactory.getCheapestFactory(ore,
            FactorySize.L);
        final Product product = mine.getProductPerHour(P150, 0);
        assertEquals(ore, product.getWare());
        assertEquals(30, Math.round(product.getQuantity()));
        final Collection<Product> resources = mine
            .getResourcesPerHour(P150, 0);
        assertEquals(1, resources.size());
        assertEquals(energy, resources.iterator().next().getWare());
        assertEquals(180, Math.round(resources.iterator().next().getQuantity()));
    }


    /**
     * Tests the Solar Power Plant M with various suns.
     */

    @Test
    public void testSolarPowerPlantM()
    {
        Product product;
        Collection<Product> resources;
        final Factory plant = factoryFactory.getCheapestFactory(energy,
            FactorySize.M);

        // Test with suns 150%
        product = plant.getProductPerHour(P150, 0);
        assertEquals(energy, product.getWare());
        assertEquals(18519, Math.round(product.getQuantity()));
        resources = plant.getResourcesPerHour(P150, 0);
        assertEquals(1, resources.size());
        assertEquals(crystals, resources.iterator().next().getWare());
        assertEquals(134, Math.round(resources.iterator().next().getQuantity()));

        // Test with suns 100%
        product = plant.getProductPerHour(P100, 0);
        assertEquals(16659, Math.round(product.getQuantity()));
        resources = plant.getResourcesPerHour(P100, 0);
        assertEquals(121, Math.round(resources.iterator().next().getQuantity()));

        // Test with suns 0%
        product = plant.getProductPerHour(P0, 0);
        assertEquals(13054, Math.round(product.getQuantity()));
        resources = plant.getResourcesPerHour(P0, 0);
        assertEquals(94, Math.round(resources.iterator().next().getQuantity()));

        // Test with suns 300%
        product = plant.getProductPerHour(P300, 0);
        assertEquals(23986, Math.round(product.getQuantity()));
        resources = plant.getResourcesPerHour(P300, 0);
        assertEquals(173, Math.round(resources.iterator().next().getQuantity()));
    }


    /**
     * Tests the Solar Power Plant L with various suns.
     */

    @Test
    public void testSolarPowerPlantL()
    {
        Product product;
        Collection<Product> resources;
        final Factory plant = factoryFactory.getCheapestFactory(energy,
            FactorySize.L);

        // Test with suns 150%
        product = plant.getProductPerHour(P150, 0);
        assertEquals(energy, product.getWare());
        assertEquals(46298, Math.round(product.getQuantity()));
        resources = plant.getResourcesPerHour(P150, 0);
        assertEquals(1, resources.size());
        assertEquals(crystals, resources.iterator().next().getWare());
        assertEquals(335, Math.round(resources.iterator().next().getQuantity()));

        // Test with suns 100%
        product = plant.getProductPerHour(P100, 0);
        assertEquals(41649, Math.round(product.getQuantity()));
        resources = plant.getResourcesPerHour(P100, 0);
        assertEquals(301, Math.round(resources.iterator().next().getQuantity()));

        // Test with suns 0%
        product = plant.getProductPerHour(P0, 0);
        assertEquals(32636, Math.round(product.getQuantity()));
        resources = plant.getResourcesPerHour(P0, 0);
        assertEquals(236, Math.round(resources.iterator().next().getQuantity()));

        // Test with suns 300%
        product = plant.getProductPerHour(P300, 0);
        assertEquals(59964, Math.round(product.getQuantity()));
        resources = plant.getResourcesPerHour(P300, 0);
        assertEquals(434, Math.round(resources.iterator().next().getQuantity()));
    }


    /**
     * Tests the Solar Power Plant XL with various suns.
     */

    @Test
    public void testSolarPowerPlantXL()
    {
        Product product;
        Collection<Product> resources;
        final Factory plant = factoryFactory.getCheapestFactory(energy,
            FactorySize.XL);

        // Test with suns 150%
        product = plant.getProductPerHour(P150, 0);
        assertEquals(energy, product.getWare());
        assertEquals(92595, Math.round(product.getQuantity()));
        resources = plant.getResourcesPerHour(P150, 0);
        assertEquals(1, resources.size());
        assertEquals(crystals, resources.iterator().next().getWare());
        assertEquals(670, Math.round(resources.iterator().next().getQuantity()));

        // Test with suns 100%
        product = plant.getProductPerHour(P100, 0);
        assertEquals(83297, Math.round(product.getQuantity()));
        resources = plant.getResourcesPerHour(P100, 0);
        assertEquals(603, Math.round(resources.iterator().next().getQuantity()));

        // Test with suns 0%
        product = plant.getProductPerHour(P0, 0);
        assertEquals(65272, Math.round(product.getQuantity()));
        resources = plant.getResourcesPerHour(P0, 0);
        assertEquals(472, Math.round(resources.iterator().next().getQuantity()));

        // Test with suns 300%
        product = plant.getProductPerHour(P300, 0);
        assertEquals(119928, Math.round(product.getQuantity()));
        resources = plant.getResourcesPerHour(P300, 0);
        assertEquals(867, Math.round(resources.iterator().next().getQuantity()));
    }
}
