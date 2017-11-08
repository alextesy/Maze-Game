package algorithms.mazeGenerators;

import java.io.Serializable;

/**
 * Created by Tal and Alex on 05/04/2017.
 */

public class Position implements Serializable {
    private int row;
    private int column;

    private boolean wall;

    public boolean isWall() {
        return wall;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }



    public Position(int row, int column) {
        this.row = row;
        this.column = column;
        this.wall = true;
    }

    public int getRowIndex() {
        return row;
    }

    public int getColumnIndex() {
        return column;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        return (column == position.column && row == position.row);
    }

    @Override
    public int hashCode() {
        return  (31 * row + column);
    }

    @Override
    public String toString() {
        return "{"+row+","+column+'}';
    }
}
