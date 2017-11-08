package algorithms.mazeGenerators;


import java.util.*;

/**
 * Created by Tal and Alex on 05/04/2017.
 */

/**
 * A specific generator that can generate a Maze according to Recursive backtracker algorithm - for more information on the algorithm: https://en.wikipedia.org/wiki/Maze_generation_algorithm
 */
public class MyMazeGenerator extends AMazeGenerator {

    private Position[][] maze;
    private boolean[][] visited;

    @Override
    public Maze generate(int rows, int columns) {
        if(rows<10||columns<10){
            rows=10;
            columns=10;
        }
        visited = new boolean[rows][columns];
        maze = new Position[rows][columns];
        for(int i=0; i<maze.length;i+=1)
            for (int j = 0; j < maze[0].length; j += 1)
                maze[i][j] = new Position(i,j);
        Stack<Position> mazeStack = new Stack<Position>();
        //starting position
        Position start = maze[0][0];
        start.setWall(false);
        visited[start.getRowIndex()][start.getColumnIndex()] = true;

        Position currCell = start;
        // indication of number of visited cells
        //the maze algorithem
        while(true){
            if(nonVistedNeighbors(currCell)){
                Position neighbor = chooseRandNeighbor(currCell);
                mazeStack.push(neighbor);
                removeWall(currCell,neighbor);
                visited[neighbor.getRowIndex()][neighbor.getColumnIndex()] = true;
                neighbor.setWall(false);
                currCell = neighbor;
            }
            else if(!mazeStack.isEmpty())
                currCell = mazeStack.pop();
            else
                break;
        }
        Position goal = maze[rows-1][columns-2];
        goal.setWall(false);
        return new Maze(start,goal,maze);
    }

    /**
     * The Function removes the wall between the current cell and the neighbor cell that is sent to the function
     * @param currCell - The Cell that you are using at this moment - Current Cell
     * @param neighbor - The neighbor Cell between which you want to remove the wall
     */
    private void removeWall(Position currCell, Position neighbor){

        if(currCell.getRowIndex() == neighbor.getRowIndex()){
            maze[currCell.getRowIndex()][(currCell.getColumnIndex()+neighbor.getColumnIndex())/2].setWall(false);
            visited[currCell.getRowIndex()][(currCell.getColumnIndex()+neighbor.getColumnIndex())/2] = true;

        }

        if(currCell.getColumnIndex() == neighbor.getColumnIndex()){
            maze[(currCell.getRowIndex()+neighbor.getRowIndex())/2][currCell.getColumnIndex()].setWall(false);
            visited[(currCell.getRowIndex()+neighbor.getRowIndex())/2][currCell.getColumnIndex()] = true;
        }

    }

    /**
     * The Function Randomly Chooses a Neighbour and returns it.
     * @param currCell - The Cell that you are using at this moment - Current Cell
     * @return Random Neighbour Cell Position, null if there are no more unvisited neighbors
     */
    private Position chooseRandNeighbor(Position currCell){
        ArrayList<Position> legalNeighbors = new ArrayList<Position>();
        if(currCell.getRowIndex()-2 >= 0){
            Position N = maze[currCell.getRowIndex()-2][currCell.getColumnIndex()];
            if(visited[currCell.getRowIndex()-2][currCell.getColumnIndex()]==false)
                legalNeighbors.add(N);
        }
        if(currCell.getRowIndex()+2 < maze.length){
            Position S = maze[currCell.getRowIndex()+2][currCell.getColumnIndex()];
            if(visited[currCell.getRowIndex()+2][currCell.getColumnIndex()]==false)
                legalNeighbors.add(S);
        }
        if(currCell.getColumnIndex()-2 >=0){
            Position E = maze[currCell.getRowIndex()][currCell.getColumnIndex()-2];
            if(visited[currCell.getRowIndex()][currCell.getColumnIndex()-2]==false )
                legalNeighbors.add(E);
        }
        if(currCell.getColumnIndex()+2 < maze[0].length) {
            Position W = maze[currCell.getRowIndex()][currCell.getColumnIndex() + 2];
            if(visited[currCell.getRowIndex()][currCell.getColumnIndex() + 2]==false)
                legalNeighbors.add(W);
        }
        if(legalNeighbors.size()>1)
            return legalNeighbors.get(new Random().nextInt(legalNeighbors.size()));
        else if(legalNeighbors.size()==1)
            return legalNeighbors.get(0);
        return null;
    }

    /**
     *
     * @param cell - a Cell that you want to check if has unvisited neihbours
     * @return - true if there are unvisited neihbours, false otherwise
     */
    private boolean nonVistedNeighbors(Position cell){
        return chooseRandNeighbor(cell)!=null ;
    }

}