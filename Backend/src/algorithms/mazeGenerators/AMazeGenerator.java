package algorithms.mazeGenerators;


/**
 * Created by Tal and Alex on 05/04/2017.
 */

/**
 * An abstract type Class for all the MazeGenerators.
 * All the MazeGenerators have the same implementation of the function measureAlgorithmTimeMills however the Generate function is distinct for each Generator.
 */
public abstract class AMazeGenerator implements IMazeGenerator {

    @Override
    public abstract Maze generate(int rows, int columns) ;


    @Override

    public long measureAlgorithmTimeMillis(int rows, int columns) {
        long startTime=System.currentTimeMillis();
        generate(rows,columns);
        long endTime=System.currentTimeMillis();
        return endTime-startTime;
    }
}
