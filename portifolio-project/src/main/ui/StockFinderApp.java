//package ui;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//
//import exceptions.NotContainsException;
//import model.Stock;
//import model.StockCollection;
//import persistence.JsonReader;
//import persistence.JsonWriter;
//
//
//// stock finder application
//public class StockFinderApp {
//    private static final String JSON_STORE = "./data/StockCollection.json";
//    private StockCollection stockList;
//    private StockCollection dataBase;
//    private Scanner input;
//    private Stock s1 = new Stock("SNAP","Internet",27.02,"NYSE",-0.2);
//    private Stock s2 = new Stock("VXRT","Medical",7.05,"NASDAQ",-0.24);
//    private Stock s3 = new Stock("INO","Medical",12.16,"NASDAQ",-0.65);
//    private Stock s4 = new Stock("AC","Air",15.84,"TSX",+0.06);
//    private JsonWriter jsonWriter;
//    private JsonReader jsonReader;
//
//
//
//
//
//
//    // EFFECTS: runs the stock finder application
//    public StockFinderApp() throws FileNotFoundException {
//        input = new Scanner(System.in);
//        stockList = new StockCollection("Mike's stockCollection");
//        jsonWriter = new JsonWriter(JSON_STORE);
//        jsonReader = new JsonReader(JSON_STORE);
//        runFinder();
//    }
//
//
//    // MODIFIES: this
//    // EFFECTS: processes user input
//    public void runFinder() {
//        boolean keepGoing = true;
//        String command = null;
//
//
//        init();
//        while (keepGoing) {
//            displayMenu();
//            command = input.next();
//            command = command.toLowerCase();
//
//            if (command.equals("q")) {
//                keepGoing = false;
//            } else {
//                processCommand(command);
//            }
//
//        }
//        System.out.println("\nGoodbye");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: process user command
//    private void processCommand(String command) {
//        if (command.equals("a")) {
//            doAddStock();
//        } else if (command.equals("r")) {
//            doRemoveStock();
//        } else if (command.equals("f")) {
//            doFindStock();
//        } else if (command.equals("fs")) {
//            doFilterStocks();
//        } else if (command.equals("n")) {
//            doPrintNames();
//        } else if (command.equals("s")) {
//            saveStockCollection();
//        } else if (command.equals("l")) {
//            loadStockCollection();
//        } else {
//            System.out.println("Selection not valid...");
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: initialize stock collection and data base
//    private void init() {
//        stockList = new StockCollection("StockCollection");
//        dataBase = new StockCollection("dataBase");
//        input = new Scanner(System.in);
//
//        dataBase.addStock(s1);
//        dataBase.addStock(s2);
//        dataBase.addStock(s3);
//        dataBase.addStock(s4);
//
//
//    }
//
//    // EFFECTS: display menu of options to user
//    private void displayMenu() {
//        System.out.println("\nSelect from:");
//        System.out.println("\ta -> add stock");
//        System.out.println("\tr -> remove stock");
//        System.out.println("\tf -> find stock");
//        System.out.println("\tfs -> filter stock");
//        System.out.println("\tn -> stock names in the collection");
//        System.out.println("\ts -> save work room to file");
//        System.out.println("\tl -> load work room from file");
//        System.out.println("\tq -> quit");
//    }
//
//    // MODIFIES: this
//    // EFFECTS: add stock to stock list and print out the names of stocks in list
//    private void doAddStock() {
//
//        System.out.print("Enter stock name to add:");
//        String name = input.next();
//        stockList.addStock(dataBase.getStockByName(name));
//        System.out.println("My stock list: ");
//        for (Stock stock : stockList.getStockList()) {
//            System.out.println(stock.getName());
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: remove stock from stock list and print out the names of stocks in list
//    private void doRemoveStock() {
//
//        System.out.print("Enter stock name to remove:");
//        String name = input.next();
//        try {
//            stockList.removeStock(stockList.getStockByName(name));
//        } catch (NotContainsException e) {
//            e.printStackTrace();
//        }
//        System.out.println("My stock list: ");
//        for (Stock stock : stockList.getStockList()) {
//            System.out.println(stock.getName());
//        }
//
//
//    }
//
//    // MODIFIES: this
//    // EFFECTS: find a stock by name and print it
//    private void doFindStock() {
//        System.out.print("Enter stock name to find stock:");
//        String name = input.next();
//        System.out.println("Name:" + name);
//        System.out.println("Category:" + (dataBase.getStockByName(name)).getCategory());
//        System.out.println("Price:" + (dataBase.getStockByName(name)).getPrice());
//        System.out.println("Exchange:" + (dataBase.getStockByName(name)).getExchange());
//        System.out.println("PriceChange:" + (dataBase.getStockByName(name)).getPriceChange());
//
//    }
//
//    // MODIFIES: this
//    // EFFECTS: filter stocks by different features
//    private void doFilterStocks() {
//
//        String selection = null;
//
//        init();
//
//        System.out.println("\nFilter by:");
//        System.out.println("\tc -> category");
//        System.out.println("\tp -> price range");
//        System.out.println("\te -> exchange");
//        selection = input.next();
//        selection = selection.toLowerCase();
//        selectFeatures(selection);
//    }
//
//    private void doPrintNames() {
//        System.out.println("Stock names: ");
//        for (Stock stock : stockList.getStockList()) {
//            System.out.println(stock.getName());
//        }
//    }
//
//    // EFFECTS: saves the workroom to file
//
//    private void saveStockCollection() {
//        try {
//            jsonWriter.open();
//            jsonWriter.write(stockList);
//            jsonWriter.close();
//            System.out.println("Saved " + stockList.getName() + " to " + JSON_STORE);
//        } catch (FileNotFoundException e) {
//            System.out.println("Unable to write to file: " + JSON_STORE);
//        }
//    }
//
//    // MODIFIES: this
//    // EFFECTS: loads workroom from file
//    private void loadStockCollection() {
//        try {
//            stockList = jsonReader.read();
//            System.out.println("Loaded " + stockList.getName() + " from " + JSON_STORE);
//        } catch (IOException e) {
//            System.out.println("Unable to read from file: " + JSON_STORE);
//        }
//    }
//
//// EFFECTS: ask users to select different features to filter stocks
//    private void selectFeatures(String selection) {
//        if (selection.equals("c")) {
//            doCategory();
//        } else if (selection.equals("p")) {
//            doPriceRange();
//        } else if (selection.equals("e")) {
//            doExchange();
//        } else {
//            System.out.println("Selection not valid...");
//        }
//
//    }
//
//
//
//
//    private void doCategory() {
//        System.out.println("Enter category to filter stocks:");
//        String category = input.next();
//        dataBase.getStockByCategory(category);
//        System.out.println("Filter result: ");
//        for (Stock stock : dataBase.getStockList()) {
//            System.out.println(stock.getName());
//        }
//    }
//
//    private void doExchange() {
//        System.out.println("Enter the exchange to filter stocks:");
//        String exchange = input.next();
//        dataBase.getStocksByExchange(exchange);
//        System.out.println("Filter result: ");
//        for (Stock stock : dataBase.getStockList()) {
//            System.out.println(stock.getName());
//        }
//    }
//
//    private void doPriceRange() {
//        System.out.println("Enter price range to filter stocks:");
//        double low = input.nextDouble();
//        double high = input.nextDouble();
//        dataBase.getStocksByPriceRange(low,high);
//        System.out.println("Filter result: ");
//        for (Stock stock : dataBase.getStockList()) {
//            System.out.println(stock.getName());
//        }
//    }
//
//
//}
//
