package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Tal and Alex on 05/04/2017.
 */

/**
 * an Implementation of a Searchable Maze
 */
public class SearchableMaze implements ISearchable {

    private Maze mySearchableMaze;


   public SearchableMaze(Maze maze)
   {
       this.mySearchableMaze = maze;
   }
    /**
     *
     * @return The Initial State of the Problem
     */
    @Override
    public AState getInitialState() {
        return new MazeState(mySearchableMaze.getStartPosition(),false,0);
    }
    /**
     *
     * @return The Goal State of the Problem
     */
    @Override
    public AState getGoalState(){
        return new MazeState(mySearchableMaze.getGoalPosition(),false,Double.POSITIVE_INFINITY);
    }
    /**
     *  The Function returns all the Possible States from the Given State
     * @param - The given State
     * @return - An ArrayList of all the Possible
     */
    @Override
    public ArrayList<AState> getAllPossibleStates(AState state){
        MazeState s = (MazeState)state;
        ArrayList<AState>  moves = new ArrayList<AState> ();


        int row = s.getState().getRowIndex();
        int col = s.getState().getColumnIndex();
        int [][] matrix = mySearchableMaze.getMazeMatrix();


        if(row+1 < matrix.length){
            if( matrix[row+1][col]==0 ){
                moves.add(new MazeState(new Position(row+1,col),false,s.getCost()+1));

            }
        }
        if(row-1 >= 0  ){
            if( matrix[row-1][col]==0){
                moves.add(new MazeState(new Position(row-1,col),false,s.getCost()+1));

            }
        }
        if(col+1 < matrix[0].length ){
            if( matrix[row][col+1]==0 ){
                moves.add(new MazeState(new Position(row,col+1),false,s.getCost()+1));

            }
        }
        if(col-1 >= 0 ){
            if( matrix[row][col-1]==0 ){
                moves.add(new MazeState(new Position(row,col-1),false,s.getCost()+1));
            }
        }
        return moves;
    }
}

