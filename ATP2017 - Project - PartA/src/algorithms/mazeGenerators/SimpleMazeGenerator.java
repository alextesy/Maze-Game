package algorithms.mazeGenerators;


import java.util.Random;
    /**
     * A specific generator that can generate a Maze according to Magnificent algorithm by Alex Kremiansky.
     * First its initializes the maze with walls, then it generates a random Start Position and Goal Position, than its generates a random Path between the Start and the Goal and then it randomly breaks the walls that are not on the Path
     */
public class SimpleMazeGenerator extends AMazeGenerator {
    private Position[][] matrix;
    @Override
    public Maze generate(int rows, int columns){
        if(rows<10||columns<10){
            rows=10;
            columns=10;
        }
        matrix=new Position[rows][columns];
        initializeWithWalls(matrix);
        Position startPos=new Position(0,new Random().nextInt(matrix[0].length));
        Position endPos=new Position(matrix.length-1,new Random().nextInt(matrix[0].length));
        matrix[startPos.getRowIndex()][startPos.getColumnIndex()].setWall(false);
        matrix[endPos.getRowIndex()][endPos.getColumnIndex()].setWall(false);
        GeneratePath(matrix,startPos,endPos);
        randomZero(matrix);
        return new Maze(startPos,endPos,matrix);
    }
        /**
         * The function initializes the maze with walls
         * @param matrix - The Maze represented with two dimensional array of positions
         */
    private void initializeWithWalls(Position matrix[][]){
        for (int i=0;i<matrix.length;i++){
            for (int j=0;j<matrix[0].length;j++){
                matrix[i][j] = new Position(i,j);
            }
        }

    }
        /**
         * The Function generates a random Path between the Start and the Goal positions
         * @param matrix - The Maze represented with two dimensional array of positions
         * @param start - Start Position
         * @param end - Goal Position
         */
    private void GeneratePath(Position[][] matrix ,Position start, Position end){

        Position curr=new Position(start.getRowIndex(),start.getColumnIndex());
        int rows=matrix.length;
        int columns=matrix[0].length;
        Position nextPos=new Position(curr.getRowIndex(),curr.getColumnIndex());
        while(!nextPos.equals(end)) {
            if (curr.getRowIndex() + 1 == rows||curr.getRowIndex() + 2 == rows)
                nextPos = new Position(end.getRowIndex(), end.getColumnIndex());
            else {
                int next = new Random().nextInt(columns);
                nextPos = new Position(curr.getRowIndex() + 1, next);
            }
            getIfLast(matrix, curr, nextPos);
            if(nextPos.getRowIndex()+1==rows) {
                getIfLast(matrix, curr, end);
                break;
            }
            else {
                curr = new Position(nextPos.getRowIndex() + 1,nextPos.getColumnIndex());
                matrix[nextPos.getRowIndex()][nextPos.getColumnIndex()].setWall(false);
            }
        }


    }
        /**
         * The function breaks the walls between the curr and the end Positions
         * @param matrix - The Maze represented with two dimentional array of positions
         * @param curr - Current Position
         * @param end - The temporary Goal Position
         */
    private void getIfLast(Position[][] matrix ,Position curr,Position end){
        Position newEnd;
        if(curr.getRowIndex()<end.getRowIndex())
            newEnd=new Position(end.getRowIndex()-1,end.getColumnIndex());
        else
            newEnd=new Position(end.getRowIndex(),end.getColumnIndex());
        if(curr.getRowIndex()<matrix.length&&curr.getColumnIndex()<matrix[0].length) {
            while (!curr.equals(newEnd)) {
                matrix[curr.getRowIndex()][curr.getColumnIndex()].setWall(false);
                if (newEnd.getColumnIndex() < curr.getColumnIndex())
                    curr = new Position(curr.getRowIndex(), curr.getColumnIndex() - 1);
                else
                    curr = new Position(curr.getRowIndex(), curr.getColumnIndex() + 1);
            }
            matrix[curr.getRowIndex()][curr.getColumnIndex()].setWall(false);
        }
    }
        /**
         * The Function randomly breaks the walls that are not on the Path
         * @param matrix The Maze represented with two dimensional array of positions
         */
    private  void randomZero(Position[][] matrix){
        for(int i=0;i<matrix.length;i++)
        {
            for(int j=0;j<matrix[0].length;j++){
                if(matrix[i][j].isWall() && new Random().nextInt(2)==1)
                    matrix[i][j].setWall(false);
            }
        }
    }
}