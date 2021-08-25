/**
 * @author Khanh An Do
 * @version 1.0
 * @since 01.05.2021
 *
 * Bildet eine ModelPosition ein einem Gitter oder 2 dimensionalen Array ab.
 */
package main;

public class ModelPosition {
    private int row, column;

    public ModelPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Standardimplementierung von equals
     * @param o das zu pruefende Objekt
     * @return Ob es dem Objekt gleich ist
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ModelPosition modelPosition = (ModelPosition) o;

        if (row != modelPosition.row) return false;
        return column == modelPosition.column;
    }

    /**
     * Standardimplementierung von hashCode
     * @return Haswert
     */
    @Override
    public int hashCode() {
        int result = row;
        result = 31 * result + column;
        return result;
    }

    /**
     * Standardimplementierung von toString
     * @return
     */
    @Override
    public String toString() {
        return "ModelPosition{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
