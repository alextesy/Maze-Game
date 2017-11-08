package View;

import algorithms.mazeGenerators.Maze;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by IL984626 on 12/06/2017.
 */
public class MazeDisplayer extends Canvas {

    private int[][] maze;
    private int charPosRow=0;
    private int charPosCol=0;
    private int goalPosRow=0;
    private int goalPosCol=0;
    private StringProperty ImageFileNameCharacter = new SimpleStringProperty();
    private StringProperty ImageFileNameWall = new SimpleStringProperty();
    private StringProperty ImageFileNameWallQ = new SimpleStringProperty();
    private StringProperty ImageFileNameGoal = new SimpleStringProperty();
    private StringProperty imageFileNameluigi=new SimpleStringProperty();

    public String getImageFileNameCharacter() {
        return ImageFileNameCharacter.get();
    }
    public String getImageFileNameWall() {
        return ImageFileNameWall.get();
    }
    public String getImageFileNameWallQ() {
        return ImageFileNameWall.get();
    }
    public String getImageFileNameGoal() {
        return ImageFileNameGoal.get();
    }
    public String getImageFileNameluigi() {
        return imageFileNameluigi.get();
    }


    public void setImageFileNameluigi(String imageFileNameCharacter) {
        this.imageFileNameluigi.set(imageFileNameCharacter);
    }
    public void setImageFileNameGoal(String imageFileNameCharacter) {
        this.ImageFileNameGoal.set(imageFileNameCharacter);
    }
    public void setImageFileNameCharacter(String imageFileNameCharacter) {
        this.ImageFileNameCharacter.set(imageFileNameCharacter);
    }
    public void setImageFileNameWall(String imageFileNameCharacter) {
        this.ImageFileNameWall.set(imageFileNameCharacter);
    }
    public void setImageFileNameWallQ(String imageFileNameCharacter) {
        this.ImageFileNameWallQ.set(imageFileNameCharacter);
    }


    public void setMaze(Maze maze) {
        this.maze =maze.getMaze();
        charPosRow=maze.getStart().getRowIndex();
        charPosCol=maze.getStart().getColumnIndex();
        goalPosRow=maze.getGoal().getRowIndex();
        goalPosCol=maze.getGoal().getColumnIndex();
        redrawCharecter(charPosRow,charPosCol);
        redraw();
    }
    public boolean setCharecter(int row,int col) {
        redrawCharecter(row,col);
        charPosRow=row;
        charPosCol=col;
        if(charPosCol==goalPosCol&&charPosRow==goalPosRow&&maze!=null){
            return  true;
        }
        return false;
    }
    public void redrawCharecter(int newRow,int newCol){
        if(maze!=null) {
            Image characterImage = null;
            try {
                characterImage = new Image(new FileInputStream(ImageFileNameCharacter.get()));
                GraphicsContext gc = getGraphicsContext2D();
                double canvasHeight = getHeight();
                double canvasWidth = getWidth();
                double cellHeight = canvasHeight / maze.length;
                double cellWidth = canvasWidth / maze[0].length;
                gc.clearRect(charPosCol * cellWidth, charPosRow * cellHeight, cellWidth, cellHeight);
                gc.drawImage(characterImage, newCol * cellWidth, newRow * cellHeight, cellWidth, cellHeight);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    public void drawSolution(Solution sol){

        double canvasHeight = getHeight();
        double canvasWidth = getWidth();
        double cellHeight = canvasHeight / maze.length;
        double cellWidth = canvasWidth / maze[0].length;
        GraphicsContext gc = getGraphicsContext2D();

        Image luigi = null;
        try {
            luigi = new Image(new FileInputStream(imageFileNameluigi.get()));
            for(int i=0;i<sol.getSolutionPath().size();i++){
                MazeState temp=(MazeState)sol.getSolutionPath().get(i);
                gc.drawImage(luigi, temp.getState().getColumnIndex() * cellWidth, temp.getState().getRowIndex() * cellHeight, cellWidth, cellHeight);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void redraw() {
        if (maze != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / maze.length;
            double cellWidth = canvasWidth / maze[0].length;
            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, getWidth(), getHeight());
            try {
                Image wall = new Image(new FileInputStream(ImageFileNameWall.get()));
                Image wallQ = new Image(new FileInputStream(ImageFileNameWallQ.get()));
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[i].length; j++) {
                        int random = (int )(Math.random() * 2 );
                        if (maze[i][j] == 1)
                            if(random==1)
                                 gc.drawImage(wall, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                             else
                                gc.drawImage(wallQ, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Image goalImage =  new Image(new FileInputStream(ImageFileNameGoal.get()));
                gc.drawImage(goalImage,goalPosCol * cellWidth, goalPosRow * cellHeight, cellWidth, cellHeight);
                Image ImagecharacterImage = new Image(new FileInputStream(ImageFileNameCharacter.get()));
                 gc.drawImage(ImagecharacterImage, charPosCol * cellWidth, charPosRow * cellHeight, cellWidth, cellHeight);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

}
