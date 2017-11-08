package algorithms.search;

import java.util.PriorityQueue;

/**
 * Created by Tal and Alex on 05/04/2017.
 */

/**
 * An abstract type class for all the Searching Algorithms.
 * All the Searching algorithms have the same implementation of the function getNumberOfNodesEvaluated however the solve function is distinct for each Searching algorithms.
 */
public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{

    private int evaluatedNodes;
    protected String name;

    public ASearchingAlgorithm() {
        evaluatedNodes=0;
    }

    /**
     * The function solves an ISearchable problem according to a specific algorithm
     * @param searchable - An object that has to implement the ISearchable Interface
     * @return returns the Solution according to a specific algorithm
     */
    @Override
    public abstract Solution solve(ISearchable searchable) ;

    /**
     * The Function returns the Number of Nodes that were evaluated during the run of the searching algorithm
     * @return int - Number of Nodes that were evaluated during the run of the searching algorithm
     */
    @Override
    public int getNumberOfNodesEvaluated() {
        return evaluatedNodes;
    }

    /**
     *
     * @return Name of the Searching Algorithm that was used to solve the Problem
     */
    public String getName(){
      return name;
    }

    /**
     *
     * @param nodes - Number of Nodes that were evaluated during the run of the searching algorithm
     */
    protected void setNumberOfNodesEvaluated(int nodes){
        this.evaluatedNodes = nodes;
    }

    /**
     * The Function gets the Start State and the End State, after the searching algorithm solved the problem, and goes from the End state to the state that he came from and so on until its gets to the start state and then returns the solution
     * @param to - End State
     * @param from - Start Date
     * @return - Solution of the Problem
     */
    protected Solution backTrace(AState to, AState from){
        Solution path = new Solution();
        path.addToSol(to);
        while(! to.getCameFrom() .equals(from) ){
            path.addToSol(to.getCameFrom());
            to = to.getCameFrom();
        }
        path.addToSol(from);
        return path;
    }

}
