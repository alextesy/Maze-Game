package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.Properties;

/**
 * Created by Tal and Alex on 19/05/2017.
 */

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    @Override
    /**
     *The Strategy of the Maze Generation
     * @param inFromClient - Input Stream  - the Dimensions of the Maze
     * @param outToClient - OutPut Stream - the Generated Maze
     */
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {

        try{
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);

            toClient.flush();
            Maze inputMaze = (Maze)fromClient.readObject();
            //directory for Solutions in Temp

            String address = System.getProperty("java.io.tmpdir")  + inputMaze.hashCode()+".sol";
            Solution mazeSol;
            if(new File(address).exists()){
                ObjectInputStream solFromFile = new ObjectInputStream(new FileInputStream(address));
                mazeSol = (Solution)solFromFile.readObject();
            }
            else{
                ObjectOutputStream solToFile = new ObjectOutputStream(new FileOutputStream(address));
                mazeSol = propertiesConfig(new SearchableMaze(inputMaze));
                solToFile.writeObject(mazeSol);
            }

            toClient.writeObject(mazeSol);
            toClient.flush();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    /**
     * The Functions solves the maze according to the configured Search algorithm
     * @param maze - the Maze to be solved
     * @return - The Solution Maze
     */
    private Solution propertiesConfig(ISearchable maze){
        Solution mazeSol;
        Properties p=new Properties();
        try {
            InputStream is = new FileInputStream("config.properties");
            p.load(is);
            if(p.getProperty("Search").equals("DFS"))
                mazeSol =new DepthFirstSearch().solve(maze);
            else if(p.getProperty("Search").equals("BestFS"))
                mazeSol =new BestFirstSearch().solve(maze);
            else
                mazeSol=new BreadthFirstSearch().solve(maze);
            return mazeSol;
        }catch (IOException io){
            return new BestFirstSearch().solve(maze);
        }

    }

}