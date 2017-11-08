package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

/**
 * Created by IL984626 on 12/06/2017.
 */
public interface IModel {
    void GenerateMaze(int height,int width);
    Maze getMaze();
    void moveCharacter(KeyCode move,int prop);
    boolean GenerateSol();
    Solution getSol();
    int getPosRow();
    int getPosCol();
    void start();
    void stop();
    void setMaze(Maze maze);

}
