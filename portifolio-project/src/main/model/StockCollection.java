package model;


import exceptions.NotContainsException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// represent a collection of stocks

public class StockCollection implements Writable {
    private String name;
    private  List<Stock> stockList;



    // Construct a empty collection of stocks
    // EFFECT: the collection is empty
    public StockCollection(String name) {
        this.name = name;
        stockList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public int size() {
        return stockList.size();
    }

    public List<Stock> getStockList() {
        return stockList;
    }


    // EFFECTS: Return True if stock is included in the collection
    public boolean include(Stock s) {
        return stockList.contains(s);
    }

    // REQUIRES:Stock s is not an element of stock collection
    // MODIFIES: this
    // EFFECTS: stock s is added to stock collection
    // if it is not in it
    public void addStock(Stock s)  {
        if (!include(s)) {

            stockList.add(s);

        } else {
            System.out.println("Cannot add");
        }
    }



    // MODIFIES: this
    // EFFECTS: Stock s is removed from the collection
    public void removeStock(Stock s)  throws NotContainsException {
        if (include(s)) {
            stockList.remove(s);


        } else {
            throw new NotContainsException();
        }
    }

    // EFFECTS: find the stock according to its name
    public Stock getStockByName(String s) {
        for (Stock stock : stockList) {
            if (stock.getName().equals(s)) {

                return stock;
            }
        }
        return null;
    }


    // MODIFIES: this
    // EFFECTS: get stocks by category
    public void getStockByCategory(String s) {
        List<Stock> newListCategory = new ArrayList<Stock>();
        for (Stock stock : stockList) {
            if (stock.getCategory().equals(s)) {
                newListCategory.add(stock);
            }
        }
        stockList = newListCategory;
    }

    // MODIFIES: this
    // EFFECTS: get stocks by price range
    public void getStocksByPriceRange(double d1, double d2) {
        List<Stock> newListPriceChange = new ArrayList<Stock>();
        for (Stock stock : stockList) {
            if (stock.getPrice() >= d1 && stock.getPrice() <= d2) {
                newListPriceChange.add(stock);
            }
        }
        stockList = newListPriceChange;
    }

    // MODIFIES: this
    // EFFECTS: get stocks by exchange
    public void getStocksByExchange(String s) {
        List<Stock> newListPriceChange = new ArrayList<Stock>();
        for (Stock stock : stockList) {
            if (stock.getExchange().equals(s)) {
                newListPriceChange.add(stock);
            }

        }
        stockList = newListPriceChange;
    }




    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("stockList", stockListToJson());
        return json;
    }
    // EFFECTS: returns stocks in this stock collection as a JSON array

    private JSONArray stockListToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Stock t : stockList) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }


}

