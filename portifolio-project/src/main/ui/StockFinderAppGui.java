package ui;


import model.Stock;
import model.StockCollection;

import javax.swing.*;

import static ui.FilterPanel.createAndShowGUI3;
import static ui.ListPanel.createAndShowGUI1;

import static ui.SaveLoadPanel.createAndShowGUI2;


public class StockFinderAppGui extends JFrame {


    private static StockCollection stockList;
    private static Stock s1 = new Stock("SNAP", "Internet", 27.02, "NYSE", -0.2);
    private static Stock s2 = new Stock("VXRT", "Medical", 7.05, "NASDAQ", -0.24);
    private static Stock s3 = new Stock("INO", "Medical", 12.16, "NASDAQ", -0.65);
    private static Stock s4 = new Stock("AC", "Air", 15.84, "TSX", +0.06);
    private JInternalFrame main;




    public static void main(String[] args)  {
        stockList = new StockCollection("Mike");
        stockList.addStock(s4);



//        Toolkit.getDefaultToolkit().beep();
//        int beepCount = 10;
//        for (int i = 0; i < beepCount; ++i) {
//            System.out.println("Beep : " + i);
//            // Ring the bell again using the Toolkit
//            java.awt.Toolkit.getDefaultToolkit().beep();
//            try {
//                Thread.sleep(1000); // introduce delay
//            } catch (InterruptedException e) {
//                System.out.println("fail");
//            }
//        }
//        stockList.addStock(s4);
//        stockList.addStock(s2);
//        stockList.addStock(s3);
//        stockList.addStock(s4);
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Toolkit.getDefaultToolkit().beep();


                    createAndShowGUI1(stockList);


                createAndShowGUI2(stockList);

                    createAndShowGUI3();


            }
        });
    }
}









