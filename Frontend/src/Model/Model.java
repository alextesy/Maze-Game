package Model;
import Client.*;
import IO.MyDecompressorInputStream;
import Server.*;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.*;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by IL984626 on 12/06/2017.
 */
public class Model extends Observable implements IModel {
    private ExecutorService threadPool;
    private Maze guiMaze;
    private Solution guiSol;
    private int charPosRow=0;
    private int charPosCol=0;
    private Server mazeGeneratingServer;
    private Server solveSearchProblemServer ;

    public void start(){
        //ProjectProperties.setProp("My Maze Generator","DFS");
        threadPool= Executors.newCachedThreadPool();
        mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        mazeGeneratingServer.start();
        solveSearchProblemServer.start();
    }
    public void stop(){
        Platform.exit();
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();
        threadPool.shutdown();
        System.exit(0);

    }

    @Override
    public void setMaze(Maze maze) {
        guiMaze=maze;
        charPosRow=maze.getStart().getRowIndex();
        charPosCol=maze.getStart().getColumnIndex();

        setChanged();
        notifyObservers(1);
    }

    @Override
    public boolean GenerateSol() {
        if(guiMaze==null)
            return false;
        threadPool.execute(()-> {
            try {
                Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                    @Override
                    public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                        try {
                            ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                            ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                            toServer.flush();

                            toServer.writeObject(guiMaze); //send maze to server
                            toServer.flush();
                            guiSol = (Solution) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server

                           // ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                client.communicateWithServer();

                setChanged();
                notifyObservers(3);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

        });
        return true;
    }

    @Override
    public Solution getSol() {
        return guiSol;
    }

    public void GenerateMaze(int height, int width) {
        start();
       threadPool.execute(()-> {
            try {
                Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                    @Override
                    public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                        try {
                            ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                            ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                            toServer.flush();
                            int[] mazeDimensions = new int[]{height, width};
                            toServer.writeObject(mazeDimensions); //send maze dimensions to server
                            toServer.flush();
                            byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                            InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                            byte[] decompressedMaze = new byte[height*width+60 /*CHANGE SIZE ACCORDING TO YOU MAZE SIZE*/]; //allocating byte[] for the decompressed maze -
                            is.read(decompressedMaze); //Fill decompressedMaze with bytes
                            guiMaze = new Maze(decompressedMaze);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                client.communicateWithServer();
                charPosRow=guiMaze.getStart().getRowIndex();
                charPosCol=guiMaze.getStart().getColumnIndex();
                setChanged();
                notifyObservers(1);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    public Maze getMaze() {
        return guiMaze;
    }

    @Override
    public void moveCharacter(KeyCode move, int propNum) {
            if(propNum==1) {
                switch (move) {
                    case UP:
                        confirmPos(charPosRow - 1, charPosCol);
                        break;
                    case DOWN:
                        confirmPos(charPosRow + 1, charPosCol);
                        break;
                    case RIGHT:
                        confirmPos(charPosRow, charPosCol + 1);
                        break;
                    case LEFT:
                        confirmPos(charPosRow, charPosCol - 1);
                        break;

                }
            }
            else if(propNum==2){
                switch (move){
                    case W:
                        confirmPos(charPosRow - 1, charPosCol);
                        break;
                    case S:
                        confirmPos(charPosRow + 1, charPosCol);
                        break;
                    case D:
                        confirmPos(charPosRow, charPosCol + 1);
                        break;
                    case A:
                        confirmPos(charPosRow, charPosCol - 1);
                        break;
                }

            }
            else{
                switch (move){
                    case NUMPAD8:
                        confirmPos(charPosRow - 1, charPosCol);
                        break;
                    case NUMPAD2:
                        confirmPos(charPosRow + 1, charPosCol);
                        break;
                    case NUMPAD6:
                        confirmPos(charPosRow, charPosCol + 1);
                        break;
                    case NUMPAD4:
                        confirmPos(charPosRow, charPosCol - 1);
                        break;
                }
            }




        setChanged();
        notifyObservers(2);

    }

    @Override
    public int getPosRow() {
        return charPosRow;
    }

    @Override
    public int getPosCol() {
        return charPosCol;
    }

    private boolean confirmPos(int row,int col){
        if(guiMaze!=null) {
            int[][] maze = guiMaze.getMaze();
            if (row < 0 || row >= maze.length || col < 0 || col >= maze[0].length || maze[row][col] == 1)
                return false;
            charPosRow = row;
            charPosCol = col;
            return true;
        }
        return false;
    }



}
