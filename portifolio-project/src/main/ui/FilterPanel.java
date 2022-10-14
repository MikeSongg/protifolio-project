package ui;

/*
 * TableFilterDemo.java requires SpringUtilities.java
 */
// cite most of the method in this class from
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html



import model.Stock;
import model.StockCollection;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

// cite most of the method in this class from
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html

public class FilterPanel extends JPanel {
    private boolean debug = false;
    private JTable table;
    private JTextField filterByName;
    private JTextField filterByCategory;
    private JTextField filterByExchange;
    private TableRowSorter<MyTableModel> sorter;
    private Stock s1 = new Stock("SNAP", "Internet", 27.02, "NYSE", -0.2);
    private Stock s2 = new Stock("VXRT", "Medical", 7.05, "NASDAQ", -0.24);
    private Stock s3 = new Stock("INO", "Medical", 12.16, "NASDAQ", -0.65);
    private Stock s4 = new Stock("AC", "Air", 15.84, "TSX", +0.06);

    private StockCollection dataBase = new StockCollection("dataBase");


    public FilterPanel()  {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        createTableWithSorter();


        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);

        createFormFilter();

        dataBase.addStock(s1);
        dataBase.addStock(s2);
        dataBase.addStock(s3);
        dataBase.addStock(s4);


    }


    public static void createAndShowGUI3()  {
        //Create and set up the window.
        JFrame frame = new JFrame("TableFilterDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        FilterPanel newContentPane = new FilterPanel();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }



    private void createFormFilter() {
        //Create a separate form for filterText
        JPanel form = new JPanel(new SpringLayout());
        filterByName(form);

        filterByCategory(form);

        filterByExchange(form);


        SpringUtilities.makeCompactGrid(form, 2, 3, 6, 6, 6, 6);
        add(form);
    }

    private void filterByExchange(JPanel form) {
        JLabel l3 = new JLabel("Filter By Exchange", SwingConstants.TRAILING);
        form.add(l3);
        filterByExchange = new JTextField();
        //Whenever filterText changes, invoke newFilter.
        filterByExchange.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newFilterExchange();
                    }

                    public void insertUpdate(DocumentEvent e) {
                        newFilterExchange();
                    }

                    public void removeUpdate(DocumentEvent e) {
                        newFilterExchange();
                    }
                });
        l3.setLabelFor(filterByExchange);
        form.add(filterByExchange);
    }

    private void filterByCategory(JPanel form) {
        JLabel l2 = new JLabel("Filter By Category", SwingConstants.TRAILING);
        form.add(l2);
        filterByCategory = new JTextField();
        //Whenever filterText changes, invoke newFilter.
        filterByCategory.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newFilterCategory();
                    }

                    public void insertUpdate(DocumentEvent e) {
                        newFilterCategory();
                    }

                    public void removeUpdate(DocumentEvent e) {
                        newFilterCategory();
                    }
                });
        l2.setLabelFor(filterByCategory);
        form.add(filterByCategory);
    }

    private void filterByName(JPanel form) {
        JLabel l1 = new JLabel("Filter By Name:", SwingConstants.TRAILING);
        form.add(l1);
        filterByName = new JTextField();
        filterByName.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newFilterName();
                    }

                    public void insertUpdate(DocumentEvent e) {
                        newFilterName();
                    }

                    public void removeUpdate(DocumentEvent e) {
                        newFilterName();
                    }
                });
        l1.setLabelFor(filterByName);
        form.add(filterByName);
    }

    private void createTableWithSorter() {
        //Create a table with a sorter.
        MyTableModel model = new MyTableModel();
        sorter = new TableRowSorter<MyTableModel>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        table.setPreferredScrollableViewportSize(new Dimension(700, 200));
        table.setFillsViewportHeight(true);

    }

    /**
     * Update the row filter regular expression from the expression in
     * the text box.
     */
    private void newFilterName() {
        RowFilter<MyTableModel, Object> rf1 = null;
        //If current expression doesn't parse, don't update.
        try {
            rf1 = RowFilter.regexFilter(filterByName.getText(), 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf1);
    }

    private void newFilterCategory() {
        RowFilter<MyTableModel, Object> rf2 = null;
        //If current expression doesn't parse, don't update.
        try {
            rf2 = RowFilter.regexFilter(filterByCategory.getText(), 1);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf2);
    }

    private void newFilterExchange() {
        RowFilter<MyTableModel, Object> rf3 = null;
        //If current expression doesn't parse, don't update.
        try {
            rf3 = RowFilter.regexFilter(filterByExchange.getText(), 3);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        sorter.setRowFilter(rf3);
    }

    class MyTableModel extends AbstractTableModel {
        private String[] columnNames = {"Name",
                "Category",
                "Price",
                "Exchange",
                "Price change"};


        private Object[][] data
                = {


                    {s1.getName(), s1.getCategory(),
                        s1.getPrice(), s1.getExchange(), s1.getPriceChange()},
                    {s2.getName(), s2.getCategory(),
                        s2.getPrice(), s2.getExchange(), s2.getPriceChange()},
                    {s3.getName(), s3.getCategory(),
                        s3.getPrice(), s3.getExchange(), s3.getPriceChange()},
                    {s4.getName(), s4.getCategory(),
                        s4.getPrice(), s4.getExchange(), s4.getPriceChange()},

                };




        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        /*
         * JTable uses this method to determine the default renderer/
         * editor for each cell.  If we didn't implement this method,
         * then the last column would contain text ("true"/"false"),
         * rather than a check box.
         */
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }

        /*
         * Don't need to implement this method unless your table's
         * editable.
         */
        public boolean isCellEditable(int row, int col) {
            //Note that the data/cell address is constant,
            //no matter where the cell appears onscreen.
            if (col < 2) {
                return false;
            } else {
                return true;
            }
        }

        /*
         * Don't need to implement this method unless your table's
         * data can change.
         */
        public void setValueAt(Object value, int row, int col) {
            if (debug) {
                System.out.println("Setting value at " + row + "," + col
                        + " to " + value
                        + " (an instance of "
                        + value.getClass() + ")");
            }

            data[row][col] = value;
            fireTableCellUpdated(row, col);

            if (debug) {
                System.out.println("New value of data:");
                printDebugData();
            }
        }

        private void printDebugData() {
            int numRows = getRowCount();
            int numCols = getColumnCount();

            for (int i = 0; i < numRows; i++) {
                System.out.print("    row " + i + ":");
                for (int j = 0; j < numCols; j++) {
                    System.out.print("  " + data[i][j]);
                }
                System.out.println();
            }
            System.out.println("--------------------------");
        }
    }
}
