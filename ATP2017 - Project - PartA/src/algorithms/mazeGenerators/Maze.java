package algorithms.mazeGenerators;

/**
 * Created by Tal and Alex on 05/04/2017.
 */


import algorithms.search.BestFirstSearch;
import algorithms.search.SearchableMaze;
import algorithms.search.Solution;

import java.io.*;

/**
 * Maze class represents a maze with a two dimensional array of ints and two positions, Start Position and the Goal Positions
 */
public class Maze implements Serializable {

    private int mazeID;
    private Position start;
    private Position goal;
    private int[][] maze;
    final int numOfBits=10;

    /**
     * Constructor
     * @param start - Start Position of the Maze
     * @param goal - Goal position of the Maze
     * @param matrix - Two Dimensional array of Positions
     */
    public Maze(Position start, Position goal, Position[][] matrix) {
        this.start = start;
        this.goal = goal;
        maze = new int[matrix.length][matrix[0].length];
        for(int i=0 ; i<matrix.length; i+=1)
            for(int j=0; j<matrix[0].length; j+=1){
                if(matrix[i][j].isWall())
                    maze[i][j] = 1;
                else
                    maze[i][j] = 0;
            }
        mazeID=toByteArray().toString().hashCode();
    }
    /**
     * a Constructor with a byte Array
     */
    public Maze(byte[] arrayByte){
        start=new Position(toDecimal(0,arrayByte),toDecimal(numOfBits,arrayByte));
        goal=new Position(toDecimal(numOfBits*2,arrayByte),toDecimal(numOfBits*3,arrayByte));
        maze=new int[toDecimal(numOfBits*4,arrayByte)][toDecimal(numOfBits*5,arrayByte)];
        int counter=numOfBits*6;
        for(int i=0;i<maze.length;i++){
            for (int j=0;j<maze[0].length;j++){
                maze[i][j]=arrayByte[counter];
                counter++;
            }
        }
        mazeID=toByteArray().toString().hashCode();
    }
    /**
     * Gets the two dimensional array of ints
     * @return two dimensional array of ints
     */
    public int[][] getMazeMatrix(){ return maze;}
    /**
     * Gets the Start Position
     * @return Start Position
     */
    public Position getStartPosition() {
        return start;
    }

    /**
     * Gets the Goal Position
     * @return Goal Position
     */
    public Position getGoalPosition() { return goal; }

    /**
     * Prints the Maze on the Screen.
     * S- Start Position.
     * E- Goal Position.
     */
    public void print(){
        for(int i=0; i<maze.length;i+=1) {
            for (int j = 0; j < maze[0].length; j += 1){
                if(i == start.getRowIndex() && j == start.getColumnIndex())
                    System.out.print('S');
                else if(i == goal.getRowIndex() && j == goal.getColumnIndex())
                    System.out.print('E');
                else if(maze[i][j]==1)
                    System.out.print("█");
                else if(maze[i][j]==0)
                    System.out.print(" ");


            }
            System.out.println();
        }
    }

    /**
     * The Function makes a representation of the maze as an Array of bytes
     * @return - an Array of bytes that represents the Maze
     */
    public byte[] toByteArray(){
        int bound=numOfBits*2/*The Start Position*/+numOfBits*2/*The Goal Position*/+numOfBits/*Num of Rows*/+numOfBits/*Num of Columns*/+(maze.length*maze[0].length);
        byte[] arrayByte=new byte[bound];

        fillArrayByte(arrayByte,0,toBinary(start.getRowIndex()));
        fillArrayByte(arrayByte,numOfBits,toBinary(start.getColumnIndex()));
        fillArrayByte(arrayByte,numOfBits*2,toBinary(goal.getRowIndex()));
        fillArrayByte(arrayByte,numOfBits*3,toBinary(goal.getColumnIndex()));
        fillArrayByte(arrayByte,numOfBits*4,toBinary(maze.length));
        fillArrayByte(arrayByte,numOfBits*5,toBinary(maze[0].length));
        int counter=numOfBits*6;
        for(int i=0;i<maze.length;i++){
            for(int j=0;j<maze[0].length;j++){
                arrayByte[counter]=(byte)maze[i][j];
                counter++;
            }
        }
        return arrayByte;


    }
    @Override
    public int hashCode() {
        return mazeID;
    }

    private int toDecimal(int currPos, byte[] arrayByte){
        int answer=0;
        for(int i=0;i<numOfBits;i++){
            answer=answer+arrayByte[currPos+i]*(int)Math.pow(2,numOfBits-i-1);
        }
        return answer;
    }
    private byte[] toBinary(int num){
        byte[] binNum=new byte[numOfBits];
        for(int i=0;i<binNum.length;i++){
            binNum[numOfBits-1-i]=(byte)(num%2);
            num=num/2;
        }
        return binNum;
    }
    private void fillArrayByte(byte[] arrayByte,int currPos,byte[] content){
        for(int i=0;i<numOfBits;i++){
            arrayByte[currPos+i]=content[i];
        }
    }


    // Serialziable implementation, default CTOR , and Getters/Setters
    public Maze(){}
    public Position getStart() {
        return start;
    }
    public void setStart(Position start) {
        this.start = start;
    }
    public Position getGoal() {
        return goal;
    }
    public void setGoal(Position goal) {
        this.goal = goal;
    }
    public int[][] getMaze() {
        return maze;
    }
    public void setMaze(int[][] maze) {
        this.maze = maze;
    }
    public int getNumOfBits() {
        return numOfBits;
    }
    public int getMazeID() {
        return mazeID;
    }
}