package ui;

import model.StockCollection;
import persistence.JsonReader;
import persistence.JsonWriter;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import static ui.ListPanel.createAndShowGUI1;


// cite most of the method in this class from
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html

public class SaveLoadPanel extends JPanel implements ActionListener {
    private static final String JSON_STORE = "./data/StockCollection2.json";
    private JButton saveButton;
    private JButton loadButton;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private StockCollection stockList;

    public SaveLoadPanel(StockCollection stockList) throws FileNotFoundException {
        this.stockList = stockList;
        saveButton = new JButton("save file");
        saveButton.setVerticalTextPosition(AbstractButton.CENTER);
        saveButton.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
        saveButton.setMnemonic(KeyEvent.VK_D);
        saveButton.setActionCommand("save");

        loadButton = new JButton("load file");
        loadButton.setVerticalTextPosition(AbstractButton.BOTTOM);
        loadButton.setHorizontalTextPosition(AbstractButton.CENTER);
        loadButton.setMnemonic(KeyEvent.VK_M);
        loadButton.setActionCommand("load");

        saveButton.addActionListener(this);
        loadButton.addActionListener(this);

        saveButton.setToolTipText("Click this button to save file.");
        loadButton.setToolTipText("Click this button to load file.");
        add(saveButton);
        add(loadButton);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    public static void music() {
        AudioPlayer mgp = AudioPlayer.player;
        AudioStream bgm;
        AudioData md;

        ContinuousAudioDataStream loop = null;

        try {
            InputStream test = new FileInputStream("./data/alarm.wav");
            bgm = new AudioStream(test);
            AudioPlayer.player.start(bgm);
            md = bgm.getData();
            loop = new ContinuousAudioDataStream(md);

        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }
        mgp.start(loop);
    }

    public static void createAndShowGUI2(StockCollection stockList) {
        //Create and set up the window.
        JFrame frame = new JFrame("SaveLoadPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = null;
        try {
            newContentPane = new SaveLoadPanel(stockList);
        } catch (FileNotFoundException e) {
            System.out.println("catch Exception");
        }
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if ("save".equals(e.getActionCommand())) {
            saveStockCollection();
            music();
        } else {

            loadStockCollection();


            createAndShowGUI1(stockList);


        }
    }

    private void saveStockCollection() {
        try {

            jsonWriter.open();
            jsonWriter.write(stockList);
            jsonWriter.close();
            System.out.println("Saved " + stockList.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("fail to save");
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadStockCollection()  {
        try {
            stockList = jsonReader.read();
            System.out.println("Loaded " + stockList.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

//    public static void main(String[] args) {
//        //Schedule a job for the event-dispatching thread:
//        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGUI2();
//            }
//        });
//    }
}


