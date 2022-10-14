package model;


import exceptions.NotContainsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class StockCollectionTest {
    private StockCollection stocks;
    private Stock s1;
    private Stock s2;

    @BeforeEach
    void runBefore() {
        stocks = new StockCollection("good");
        s1 = new Stock("SNAP","Internet",27.02,"NYSE",-0.2);
        s2 = new Stock("VXRT","Medical",7.05,"NASDAQ",-0.24);
        stocks.addStock(s1);


    }



    @Test
    void testConstructor() {

        assertEquals(1,stocks.size());

    }

    @Test
    void testInclude() {
        assertFalse(stocks.include(s2));
        stocks.addStock(s2);
        assertTrue(stocks.include(s2));

    }

    @Test
    void testAddStock() {
        assertTrue(stocks.include(s1));
        stocks.addStock(s1);
        assertTrue(stocks.include(s1));
        assertFalse(stocks.include(s2));
        stocks.addStock(s2);
        assertTrue(stocks.include(s2));
    }

    @Test
    void testRemoveStockNotInclude() {

        try {
            assertFalse(stocks.include(s2));
            stocks.removeStock(s2);
            fail("No exeption was thrown");
        } catch (NotContainsException e) {
            System.out.println("cannot remove");
        }
    }

    @Test
    void testRemoveStockInclude() {

        try {
            assertTrue(stocks.include(s1));
            stocks.removeStock(s1);

        } catch (NotContainsException e) {
            fail("exception should not be thrown");
        }
    }

    @Test
    void testGetStockByName() {

        assertEquals(s1, stocks.getStockByName("SNAP"));
        assertEquals(null,stocks.getStockByName("UBER"));
    }

    @Test
    void testGetStockByCategory() {

        stocks.addStock(s2);
        stocks.getStockByCategory("Internet");
        assertTrue(stocks.include(s1));
        assertFalse(stocks.include(s2));


    }
    @Test
    void testGetStockInsidePriceRange() {

        stocks.addStock(s2);
        stocks.getStocksByPriceRange(7,8);
        assertTrue(stocks.include(s2));
        assertFalse(stocks.include(s1));


    }

    @Test
    void testGetStockOutsidePriceRange() {

        stocks.addStock(s2);
        stocks.getStocksByPriceRange(200,400);
        assertFalse(stocks.include(s2));
        assertFalse(stocks.include(s1));
    }

    @Test
    void testGetStockByExchange() {

        stocks.addStock(s2);
        stocks.getStocksByExchange("NYSE");
        assertTrue(stocks.include(s1));
        assertFalse(stocks.include(s2));


    }

    @Test
    void testGetStockList() {
        try {
            stocks.removeStock(s1);
        } catch (NotContainsException e) {
            e.printStackTrace();
        }
        assertEquals(Collections.emptyList(),stocks.getStockList());
    }



}
