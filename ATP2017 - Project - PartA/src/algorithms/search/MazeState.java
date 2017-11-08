package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.io.Serializable;

/**
 * Created by Tal and Alex on 05/04/2017.
 */

/**
 * A class that represents the a State of Maze
 */
public class MazeState extends AState implements Serializable {

    Position state;
    private boolean isWall;

    public MazeState(Position pos, boolean isWall, double cost){
        super("<"+pos.getRowIndex() + "," + pos.getColumnIndex() + ">", cost);
        this.state = pos;
        this.isWall=isWall;
    }

    /**
     *
     * @return True of The current State is a Wall
     */
    public boolean isWall() {
        return isWall;
    }
    /**
     *
     * @return The position of the current State
     */
    public Position getState() {
        return state;
    }

    /**
     *
     * @param other - an Object you want to compare to the State
     * @return - true if the object is a Maze State at the same Position
     */
    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof MazeState) {
            MazeState that = (MazeState) other;
            result = (this.state.getRowIndex()== that.state.getRowIndex()&& this.state.getColumnIndex()== that.state.getColumnIndex());
        }
        return result;
    }

    /**
     *
     * @return a Hash Code of the object
     */
    @Override
    public int hashCode() {
        return (41 * (41 + state.getRowIndex()) + state.getColumnIndex());
    }

    @Override
    public String toString() {
        return "<" + state.getRowIndex() + "," + state.getColumnIndex() + ">";
    }
}
