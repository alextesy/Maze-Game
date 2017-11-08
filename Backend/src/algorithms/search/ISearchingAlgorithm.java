package algorithms.search;

/**
 * Created by Tal and Alex on 05/04/2017.
 */
/**
 * An interface that defines the functionality of each Searching Algorithm.
 * Each object has to implement the getInitialState function and the getGoalState function and getAllPossibleStates.
 */
public interface ISearchingAlgorithm {
    /**
     *
     * @return The Name of the Searching Algorithm
     */
    String getName();
    /**
     * The function solves an ISearchable problem according to a specific algorithm
     * @param searchable - An object that has to implement the ISearchable Interface
     * @return returns the Solution according to a specific algorithm
     */
    Solution solve(ISearchable searchable);
    /**
     * The Function returns the Number of Nodes that were evaluated during the run of the searching algorithm
     * @return int - Number of Nodes that were evaluated during the run of the searching algorithm
     */
    int getNumberOfNodesEvaluated();
}
