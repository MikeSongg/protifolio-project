package model;
// represent a stock having name, category, price, exchange, price change


import org.json.JSONObject;
import persistence.Writable;

public class Stock  implements Writable {
    private String name;          // the name of the stock
    private String category;      // the category of the stock
    private double price;         // the current price of the stock
    private String exchange;      // the exchange of the stock
    private double priceChange;   // the change of the current stock price compared to the previous price



    // Construct a stock
    // EFFECTS: construct a stock with its name, price, exchange, and price change.
    public Stock(String name, String category, double price, String exchange, double priceChange) {

        this.name = name;
        this.category = category;
        this.price = price;
        this.exchange = exchange;
        this.priceChange = priceChange;


    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;

    }

    public String getExchange() {
        return exchange;
    }

    public double getPrice() {
        return price;
    }

    public double getPriceChange() {
        return priceChange;
    }









    @Override
    public JSONObject toJson() {

        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("category", category);
        json.put("price", price);
        json.put("exchange", exchange);
        json.put("priceChange", priceChange);
        return json;
    }


}
