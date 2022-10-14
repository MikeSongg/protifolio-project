package persistence;



import exceptions.NotContainsException;
import model.Stock;
import model.StockCollection;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.json.*;

// Represents a reader that reads stock collection from JSON data stored in file.
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads stock collection from file and returns it;
    // throws IOException if an error occurs reading data from file
    public StockCollection read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStockCollection(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses Stock Collection from JSON object and returns it
    private StockCollection parseStockCollection(JSONObject jsonObject)  {
        String name = jsonObject.getString("name");
        StockCollection sc = new StockCollection(name);
        addStocks(sc, jsonObject);
        return sc;
    }

    // MODIFIES: sc
    // EFFECTS: parses stocks from JSON object and adds them to stock collection
    private void addStocks(StockCollection sc, JSONObject jsonObject)  {
        JSONArray jsonArray = jsonObject.getJSONArray("stockList");
        for (Object json : jsonArray) {
            JSONObject nextStock = (JSONObject) json;
            addStock(sc, nextStock);
        }
    }

    // MODIFIES: sc
    // EFFECTS: parses stock from JSON object and adds it to stock collection
    private void addStock(StockCollection sc, JSONObject jsonObject)  {
        String name = jsonObject.getString("name");
        String category = jsonObject.getString("category");
        String exchange = jsonObject.getString("exchange");
        Double price = jsonObject.getDouble("price");
        Double priceChange = jsonObject.getDouble("priceChange");
        Stock stock = new Stock(name,category,price,exchange,priceChange);
        sc.addStock(stock);
    }





}


