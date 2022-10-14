//package ui;
//
//import model.Stock;
//
//import javax.swing.table.AbstractTableModel;
//
//class MyTableModel extends AbstractTableModel {
//    private Stock s1 = new Stock("SNAP", "Internet", 27.02, "NYSE", -0.2);
//    private Stock s2 = new Stock("VXRT", "Medical", 7.05, "NASDAQ", -0.24);
//    private Stock s3 = new Stock("INO", "Medical", 12.16, "NASDAQ", -0.65);
//    private Stock s4 = new Stock("AC", "Air", 15.84, "TSX", +0.06);
//    private boolean debug = false;
//    private String[] columnNames = {"Name",
//            "Category",
//            "Price",
//            "Exchange",
//            "Price change"};
//
//
//    private Object[][] data
//            = {
//
//
//            {s1.getName(), s1.getCategory(),
//                    s1.getPrice(), s1.getExchange(), s1.getPriceChange()},
//            {s2.getName(), s2.getCategory(),
//                    s2.getPrice(), s2.getExchange(), s2.getPriceChange()},
//            {s3.getName(), s3.getCategory(),
//                    s3.getPrice(), s3.getExchange(), s3.getPriceChange()},
//            {s4.getName(), s4.getCategory(),
//                    s4.getPrice(), s4.getExchange(), s4.getPriceChange()},
//
//    };
//
//
//
//
//    public int getColumnCount() {
//        return columnNames.length;
//    }
//
//    public int getRowCount() {
//        return data.length;
//    }
//
//    public String getColumnName(int col) {
//        return columnNames[col];
//    }
//
//    public Object getValueAt(int row, int col) {
//        return data[row][col];
//    }
//
//    /*
//     * JTable uses this method to determine the default renderer/
//     * editor for each cell.  If we didn't implement this method,
//     * then the last column would contain text ("true"/"false"),
//     * rather than a check box.
//     */
//    public Class getColumnClass(int c) {
//        return getValueAt(0, c).getClass();
//    }
//
//    /*
//     * Don't need to implement this method unless your table's
//     * editable.
//     */
//    public boolean isCellEditable(int row, int col) {
//        //Note that the data/cell address is constant,
//        //no matter where the cell appears onscreen.
//        if (col < 2) {
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    /*
//     * Don't need to implement this method unless your table's
//     * data can change.
//     */
//    public void setValueAt(Object value, int row, int col) {
//        if (debug) {
//            System.out.println("Setting value at " + row + "," + col
//                    + " to " + value
//                    + " (an instance of "
//                    + value.getClass() + ")");
//        }
//
//        data[row][col] = value;
//        fireTableCellUpdated(row, col);
//
//        if (debug) {
//            System.out.println("New value of data:");
//            printDebugData();
//        }
//    }
//
//    private void printDebugData() {
//        int numRows = getRowCount();
//        int numCols = getColumnCount();
//
//        for (int i = 0; i < numRows; i++) {
//            System.out.print("    row " + i + ":");
//            for (int j = 0; j < numCols; j++) {
//                System.out.print("  " + data[i][j]);
//            }
//            System.out.println();
//        }
//        System.out.println("--------------------------");
//    }
//}
