package algorithms.search;

import java.util.*;

/**
 * Created by Tal and Alex on 05/04/2017.
 */

/**
 *https://en.wikipedia.org/wiki/Breadth-first_search
 */
public class BreadthFirstSearch extends ASearchingAlgorithm {

    public BreadthFirstSearch() {
        super.name = "BreadthFirstSearch";
    }

    @Override
    public Solution solve(ISearchable s) {
        HashSet<AState> set = new HashSet<AState>();
        Queue<AState> queue = new LinkedList<>();

        setNumberOfNodesEvaluated(1);
        set.add(s.getInitialState());
        queue.add(s.getInitialState());

        while(!queue.isEmpty()){
            setNumberOfNodesEvaluated(getNumberOfNodesEvaluated()+1);
            AState current = queue.poll();
            if(current.equals(s.getGoalState()))
                return backTrace(current,s.getInitialState());
            ArrayList<AState> adjacent = s.getAllPossibleStates(current);
            for(AState state : adjacent){
                if(!set.contains(state)){
                    set.add(state);
                    state.setCameFrom(current);
                    queue.add(state);
                }
            }

        }
        return null;


    }
}

