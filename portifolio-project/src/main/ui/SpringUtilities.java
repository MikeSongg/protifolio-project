

package ui;

import javax.swing.*;
import javax.swing.SpringLayout;
import java.awt.*;


// cite most of the method in this class from
// https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html
/**
 * A 1.4 file that provides utility methods for
 * creating form- or grid-style layouts with SpringLayout.
 * These utilities are used by several programs, such as
 * SpringBox and SpringCompactGrid.
 */
public class SpringUtilities {
    /**
     * A debugging utility that prints to stdout the component's
     * minimum, preferred, and maximum sizes.
     */
    public static void printSizes(Component c) {
        System.out.println("minimumSize = " + c.getMinimumSize());
        System.out.println("preferredSize = " + c.getPreferredSize());
        System.out.println("maximumSize = " + c.getMaximumSize());
    }

    /**
     * Aligns the first <code>rows</code> * <code>cols</code>
     * components of <code>parent</code> in
     * a grid. Each component is as big as the maximum
     * preferred width and height of the components.
     * The parent is made just big enough to fit them all.
     *
     * @param rows number of rows
     * @param cols number of columns
     * @param initialX x location to start the grid at
     * @param initialY y location to start the grid at
     * @param xpad x padding between cells
     * @param ypad y padding between cells
     */
    public static void makeGrid(Container parent, int rows, int cols,
                                int initialX, int initialY,
                                int xpad, int ypad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout)parent.getLayout();
        } catch (ClassCastException exc) {
            System.err.println("The first argument to makeGrid must use SpringLayout.");
            return;
        }

        Spring xpadspring = Spring.constant(xpad);
        Spring ypadspring = Spring.constant(ypad);
        Spring initialXSpring = Spring.constant(initialX);
        Spring initialYSpring = Spring.constant(initialY);
        int max = rows * cols;

        //Calculate Springs that are the max of the width/height so that all
        //cells have the same size.
        Spring maxWidthSpring = layout.getConstraints(parent.getComponent(0)).getWidth();
        Spring maxHeightSpring = layout.getConstraints(parent.getComponent(0)).getWidth();
        for (int i = 1; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(parent.getComponent(i));
            maxWidthSpring = Spring.max(maxWidthSpring, cons.getWidth());
            maxHeightSpring = Spring.max(maxHeightSpring, cons.getHeight());
        }
        applyNewSpring(parent, layout, max, maxWidthSpring, maxHeightSpring);
        SpringLayout.Constraints lastCons = getConstraints(parent, cols, layout, xpadspring, ypadspring,
                initialXSpring, initialYSpring, max);
        setParentSize(parent, xpad, ypad, layout, lastCons);

    }

    private static void applyNewSpring(Container parent, SpringLayout layout, int max, Spring maxWidthSpring,
                                       Spring maxHeightSpring) {
        //Apply the new width/height Spring. This forces all the
        //components to have the same size.
        for (int i = 0; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(
                    parent.getComponent(i));

            cons.setWidth(maxWidthSpring);
            cons.setHeight(maxHeightSpring);
        }
    }

    private static void setParentSize(Container parent, int xpad, int ypad, SpringLayout layout,
                                      SpringLayout.Constraints lastCons) {
        //Set the parent's size.
        SpringLayout.Constraints pcons = layout.getConstraints(parent);
        pcons.setConstraint(SpringLayout.SOUTH,
                Spring.sum(
                        Spring.constant(ypad),
                        lastCons.getConstraint(SpringLayout.SOUTH)));
        pcons.setConstraint(SpringLayout.EAST,
                Spring.sum(
                        Spring.constant(xpad),
                        lastCons.getConstraint(SpringLayout.EAST)));
    }

    private static SpringLayout
            .Constraints getConstraints(Container parent, int cols, SpringLayout layout, Spring xpadspring,
                                        Spring ypadspring, Spring initialXSpring, Spring initialYSpring, int max) {
        //Then adjust the x/y constraints of all the cells so that they
        //are aligned in a grid.
        SpringLayout.Constraints lastCons = null;
        SpringLayout.Constraints lastRowCons = null;
        for (int i = 0; i < max; i++) {
            SpringLayout.Constraints cons = layout.getConstraints(
                    parent.getComponent(i));
            if (i % cols == 0) { //start of new row
                lastRowCons = lastCons;
                cons.setX(initialXSpring);
            } else { //x position depends on previous component
                cons.setX(Spring.sum(lastCons.getConstraint(SpringLayout.EAST),
                        xpadspring));
            }

            if (i / cols == 0) { //first row
                cons.setY(initialYSpring);
            } else { //y position depends on previous row
                cons.setY(Spring.sum(lastRowCons.getConstraint(SpringLayout.SOUTH),
                        ypadspring));
            }
            lastCons = cons;
        }
        return lastCons;
    }

    /* Used by makeCompactGrid. */
    private static SpringLayout.Constraints getConstraintsForCell(
            int row, int col,
            Container parent,
            int cols) {
        SpringLayout layout = (SpringLayout) parent.getLayout();
        Component c = parent.getComponent(row * cols + col);
        return layout.getConstraints(c);
    }

    /**
     * Aligns the first <code>rows</code> * <code>cols</code>
     * components of <code>parent</code> in
     * a grid. Each component in a column is as wide as the maximum
     * preferred width of the components in that column;
     * height is similarly determined for each row.
     * The parent is made just big enough to fit them all.
     *
     * @param rows number of rows
     * @param cols number of columns
     * @param initialX x location to start the grid at
     * @param initialY y location to start the grid at
     * @param xpad x padding between cells
     * @param ypad y padding between cells
     */
    public static void makeCompactGrid(Container parent,
                                       int rows, int cols,
                                       int initialX, int initialY,
                                       int xpad, int ypad) {
        SpringLayout layout;
        try {
            layout = (SpringLayout)parent.getLayout();
        } catch (ClassCastException exc) {
            System.err.println("The first argument to makeCompactGrid must use SpringLayout.");
            return;
        }

        //Align all cells in each column and make them the same width.
        Spring x = alignCellsColumn(parent, rows, cols, initialX, xpad);
        Spring y = alignCellsRow(parent, rows, cols, initialY, ypad);

        //Set the parent's size.
        SpringLayout.Constraints pcons = layout.getConstraints(parent);
        pcons.setConstraint(SpringLayout.SOUTH, y);
        pcons.setConstraint(SpringLayout.EAST, x);
    }

    private static Spring alignCellsRow(Container parent, int rows, int cols, int initialY, int ypad) {
        //Align all cells in each row and make them the same height.
        Spring y = Spring.constant(initialY);
        for (int r = 0; r < rows; r++) {
            Spring height = Spring.constant(0);
            for (int c = 0; c < cols; c++) {
                height = Spring.max(height,
                        getConstraintsForCell(r, c, parent, cols).getHeight());
            }
            for (int c = 0; c < cols; c++) {
                SpringLayout.Constraints constraints =
                        getConstraintsForCell(r, c, parent, cols);
                constraints.setY(y);
                constraints.setHeight(height);
            }
            y = Spring.sum(y, Spring.sum(height, Spring.constant(ypad)));
        }
        return y;
    }

    private static Spring alignCellsColumn(Container parent, int rows, int cols, int initialX, int xpad) {
        Spring x = Spring.constant(initialX);
        for (int c = 0; c < cols; c++) {
            Spring width = Spring.constant(0);
            for (int r = 0; r < rows; r++) {
                width = Spring.max(width,
                        getConstraintsForCell(r, c, parent, cols).getWidth());
            }
            for (int r = 0; r < rows; r++) {
                SpringLayout.Constraints constraints =
                        getConstraintsForCell(r, c, parent, cols);
                constraints.setX(x);
                constraints.setWidth(width);
            }
            x = Spring.sum(x, Spring.sum(width, Spring.constant(xpad)));
        }
        return x;
    }
}
