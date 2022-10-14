package ui;

// cite most of the method in this class from
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html



import exceptions.NotContainsException;
import model.Stock;
import model.StockCollection;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// cite most of the method in this class from
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html

public class ListPanel extends JPanel
        implements ListSelectionListener {


    private static final String addString = "add";
    private static final String removeString = "remove";
    private JList list;
    private DefaultListModel listModel;
    //
    private Stock s1 = new Stock("SNAP", "Internet", 27.02, "NYSE", -0.2);
    private Stock s2 = new Stock("VXRT", "Medical", 7.05, "NASDAQ", -0.24);
    private Stock s3 = new Stock("INO", "Medical", 12.16, "NASDAQ", -0.65);
    private Stock s4 = new Stock("AC", "Air", 15.84, "TSX", +0.06);
    private Stock s5 = new Stock("Ge", "Electric", 9.68, "NYSE", +0.11);
    //    private StockCollection stockList = new StockCollection("Mike");
    private StockCollection stockList;
    private StockCollection dataBase = new StockCollection("dateBase");
    private JButton removeButton;
    private JButton addButton;

    private JTextField stockName;


    public ListPanel(StockCollection stockList)  {
        super(new BorderLayout());
        this.stockList = stockList;

        listModel = new DefaultListModel();
        //stockList.addStock(s4);
        dataBase.addStock(s1);
        dataBase.addStock(s2);
        dataBase.addStock(s3);
        dataBase.addStock(s4);
        dataBase.addStock(s5);
        for (Stock stock : stockList.getStockList()) {
            listModel.addElement(stock.getName());
        }



        //Create the list and put it in a scroll pane.
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

        creatButtons();

        //Create a panel that uses BoxLayout.
        creatPanelBoxLayout(listScrollPane);


    }

    private void creatPanelBoxLayout(JScrollPane listScrollPane) {
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,
                BoxLayout.LINE_AXIS));
        buttonPane.add(this.removeButton);
        buttonPane.add(Box.createHorizontalStrut(10));
        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
        buttonPane.add(Box.createHorizontalStrut(10));
        buttonPane.add(stockName);
        buttonPane.add(addButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    private void creatButtons() {
        addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);

        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());


        stockName = new JTextField(10);
        stockName.addActionListener(addListener);
        stockName.getDocument().addDocumentListener(addListener);
        String name = listModel.getElementAt(
                list.getSelectedIndex()).toString();
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI1(StockCollection stockList)  {
        //Create and set up the window.
        JFrame frame = new JFrame("ListPanel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        JComponent newContentPane = new ListPanel(stockList);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);


    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                //No selection, disable fire button.
                removeButton.setEnabled(false);

            } else {
                //Selection, enable the fire button.
                removeButton.setEnabled(true);
            }
        }
    }

    class RemoveListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            //This method can be called only if
            //there's a valid selection
            //so go ahead and remove whatever's selected.
            int index = list.getSelectedIndex();
            String name = listModel.get(index).toString();
            listModel.remove(index);


            try {
                stockList.removeStock(stockList.getStockByName(name));
            } catch (NotContainsException notContainsException) {
                System.out.println("cannot be removed");
            }


//            stockList.removeStock(stockList.getStockList().get(index));


            int size = listModel.getSize();

            if (size == 0) { //Nobody's left, disable firing.
                removeButton.setEnabled(false);

            } else { //Select an index.
                if (index == listModel.getSize()) {
                    //removed item in last position
                    index--;
                }

                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);


            }
        }
    }

    //This listener is shared by the text field and the hire button.
    class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        //Required by ActionListener.
        public void actionPerformed(ActionEvent e) {
            String name2 = stockName.getText();

            //User didn't type in a unique name...
            if (name2.equals("") || alreadyInList(name2)) {
                Toolkit.getDefaultToolkit().beep();
                stockName.requestFocusInWindow();
                stockName.selectAll();
                return;
            }

            int index = list.getSelectedIndex(); //get selected index
            if (index == -1) { //no selection, so insert at beginning
                index = 0;
            } else {           //add after the selected item
                index++;
            }


            listModel.insertElementAt(stockName.getText(), index);
            {     stockList.addStock(dataBase.getStockByName(stockName.getText())); }
            //If we just wanted to add to the end, we'd do this:
            //listModel.addElement(employeeName.getText());

            //Reset the text field.
            stockName.requestFocusInWindow();
            stockName.setText("");

            //Select the new item and make it visible.
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        //This method tests for string equality. You could certainly
        //get more sophisticated about the algorithm.  For example,
        //you might want to ignore white space and capitalization.
        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        //Required by DocumentListener.
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        //Required by DocumentListener.
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        //Required by DocumentListener.
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }


//    public static void main(String[] args) {
//        //Schedule a job for the event-dispatching thread:
//        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGUI1();
//            }
//        });
//    }
}
