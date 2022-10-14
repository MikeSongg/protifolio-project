package persistence;

import model.Stock;
import model.StockCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest{
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            StockCollection sc = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyStockCollection() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyStockCollection.json");
        try {
            StockCollection sc = reader.read();
            assertEquals("My Stock Collection", sc.getName());
            assertEquals(0, sc.size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralStockCollection() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStockCollection.json");
        try {
            StockCollection sc = reader.read();
            assertEquals("My Stock Collection", sc.getName());
            List<Stock> stocks = sc.getStockList();
            assertEquals(2, stocks.size());
            checkStock("SNAP","Internet",27.02,"NYSE",-0.2, stocks.get(0));
            checkStock("VXRT","Medical",7.05,"NASDAQ",-0.24, stocks.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
