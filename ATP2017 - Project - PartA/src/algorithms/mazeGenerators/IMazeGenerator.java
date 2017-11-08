package algorithms.mazeGenerators;

/**
 * Created by Tal and Alex on 05/04/2017.
 */
/**
 * An interface that defines the functionality of each Maze Generator.
 * Each Generator has to implement the generate function and the measureAlgorithmTimeMills function.
 */
public interface IMazeGenerator {
    /**
     * The Function creates a Maze according to specific Algorithm.

     * @param rows-int Number of Rows of the Maze
     * @param columns-int Number of Columns of the Maze
     * @return Maze - The Maze that created according to a specific Algorithm
     */
    Maze generate(int rows,int columns);
    /**
     * The Function measures Algorithm Time in Mill Seconds.

     * @param rows-int Number of Rows of the Maze
     * @param columns-int Number of Columns of the Maze
     * @return long - Time it took to build the Maze according to a specific algorithm in Mill Second
     */
    long measureAlgorithmTimeMillis(int rows,int columns);
}
