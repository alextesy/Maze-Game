package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.SimpleMazeGenerator;

import java.io.*;
import java.util.Properties;

/**
 * Created by Tal and Alex on 19/05/2017.
 */
public class ServerStrategyGenerateMaze implements IServerStrategy {
    /**
     *The Strategy of the Maze Generation
     * @param inFromClient - Input Stream  - the Dimensions of the Maze
     * @param outToClient - OutPut Stream - the Generated Maze
     */
    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            int[] dimensions = (int[]) fromClient.readObject();
            Maze clientMaze=propertiesConfig(dimensions);
            ByteArrayOutputStream byteStream=new ByteArrayOutputStream();
            MyCompressorOutputStream compressOut=new MyCompressorOutputStream(byteStream);
            compressOut.write(clientMaze.toByteArray());
            toClient.writeObject(byteStream.toByteArray());
            toClient.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e){e.printStackTrace();}
    }

    /**
     * The Functions generates the maze according to the configured algorithm
     * @param dimensions - the dimensions of the Maze
     * @return - The generated Maze
     */
    private Maze propertiesConfig(int[] dimensions){
        Maze clientMaze;
        Properties p=new Properties();
        try {
            InputStream is = new FileInputStream("config.properties");
            p.load(is);
            if(p.getProperty("Generator").equals("SimpleMazeGenerator"))
                clientMaze =new SimpleMazeGenerator().generate(dimensions[0],dimensions[1]);
            else
                clientMaze =new MyMazeGenerator().generate(dimensions[0],dimensions[1]);
            return clientMaze;
        }catch (IOException io){
            return new MyMazeGenerator().generate(dimensions[0],dimensions[1]);
        }

    }




    }

