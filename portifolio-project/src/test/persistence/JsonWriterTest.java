package persistence;

import model.Stock;
import model.StockCollection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest{
    @Test
    void testWriterInvalidFile() {
        try {
            StockCollection sc = new StockCollection("My Stock Collection");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            StockCollection sc = new StockCollection("My Stock Collection");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyStockCollection.json");
            writer.open();
            writer.write(sc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyStockCollection.json");
            sc = reader.read();
            assertEquals("My Stock Collection", sc.getName());
            assertEquals(0, sc.size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            StockCollection sc = new StockCollection("My Stock Collection");
            sc.addStock(new Stock("SNAP","Internet",27.02,"NYSE",-0.2));
            sc.addStock(new Stock("VXRT","Medical",7.05,"NASDAQ",-0.24));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStockCollection.json");
            writer.open();
            writer.write(sc);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralStockCollection.json");
            sc = reader.read();
            assertEquals("My Stock Collection", sc.getName());
            List<Stock> stockList = sc.getStockList();
            assertEquals(2, stockList.size());
            checkStock("SNAP","Internet",27.02,"NYSE",-0.2, stockList.get(0));
            checkStock("VXRT","Medical",7.05,"NASDAQ",-0.24, stockList.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
