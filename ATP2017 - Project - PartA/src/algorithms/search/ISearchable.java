package algorithms.search;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Tal and Alex on 05/04/2017.
 */

/**
 * An interface that defines the functionality of each Searchable object.
 * Each object has to implement the getInitialState function and the getGoalState function and getAllPossibleStates.
 */
public interface ISearchable {
    /**
     *
     * @return The Initial State of the Problem
     */
    AState getInitialState();
    /**
     *
     * @return The Goal State of the Problem
     */
    AState getGoalState();

    /**
     *  The Function returns all the Possible States from the Given State
     * @param - The given State
     * @return - An ArrayList of all the Possible
     */
    ArrayList<AState> getAllPossibleStates(AState state);
}
