package persistence;

import model.Stock;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkStock(String name, String category, double price, String exchange,  double priceChange, Stock stock) {
        assertEquals(name, stock.getName());
        assertEquals(category, stock.getCategory());
        assertEquals(priceChange, stock.getPriceChange());
        assertEquals(exchange, stock.getExchange());
        assertEquals(price, stock.getPrice());
}
}
