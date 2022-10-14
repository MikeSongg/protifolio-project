package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {
    private Stock testStock;
    private Stock s2;

    @BeforeEach
    void runBefore() {

        testStock = new Stock("Snap","Internet",27.02,"NYSE",-0.2);
    }

    @Test
    void testConstructor() {
        assertEquals("Snap",testStock.getName());
        assertEquals("Internet",testStock.getCategory());
        assertEquals(27.02,testStock.getPrice());
        assertEquals("NYSE",testStock.getExchange());
        assertEquals(-0.2,testStock.getPriceChange());
    }

    @Test
    void testGetName() {
        assertEquals("Snap", testStock.getName());
    }

    @Test
    void testGetCategory() {
        assertEquals("Internet",testStock.getCategory());
    }

    @Test
    void testGetPrice() {
        assertEquals(27.02,testStock.getPrice());
    }

    @Test
    void testGetExchange() {
        assertEquals("NYSE",testStock.getExchange());
    }

    @Test
    void testGetPriceChange() {
        assertEquals(-0.2,testStock.getPriceChange());

    }



}