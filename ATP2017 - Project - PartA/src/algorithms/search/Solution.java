package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Tal and Alex on 05/04/2017.
 */

/**
 * Solution Class Which represents a Solution as a ArrayList of States
 */
public class Solution implements Serializable {
    private ArrayList<AState> sol;

    public Solution() {
        sol = new ArrayList<AState>();
    }

    /**
     * Adds a state to the Solution
     * @param - A state that you want to add to the Solution
     */
    public void addToSol(AState s){
        sol.add(0,s);
    }

    /**
     * Returns the Solution
     * @return - ArrayList of States
     */
    public ArrayList<AState> getSolutionPath(){ return sol; }

}
