package ViewModel;

import Model.IModel;
import Model.Model;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.beans.property.IntegerProperty;
import javafx.scene.input.KeyCode;

import java.util.Observable;
import java.util.Observer;


public class ViewModel extends Observable implements Observer {

    public IModel myModel;
    //public IntegerProperty rowPos;
    //public IntegerProperty colPos;



    public ViewModel(IModel myModel) {
        this.myModel = myModel;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(o==myModel){
            setChanged();
            notifyObservers(arg);
        }
    }
    public void generateMaze(int height,int width){
        myModel.GenerateMaze(height,width);
    }
    public boolean generateSol(){
        return myModel.GenerateSol();
    }

    public void moveCharacter(KeyCode move,int prop){
        myModel.moveCharacter(move,prop);
    }
    public int[] getPos(){
        int[] returnArr={myModel.getPosRow(),myModel.getPosCol()};
        return returnArr;
    }
    public Maze getMaze(){
        return myModel.getMaze();
    }
    public Solution getSol(){return myModel.getSol();}
    public void exitProgram(){
        myModel.stop();
    }
    public void setMaze(Maze maze){
        myModel.setMaze(maze);
    }
}
