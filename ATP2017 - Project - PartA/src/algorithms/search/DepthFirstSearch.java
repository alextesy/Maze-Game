package algorithms.search;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Tal and Alex on 05/04/2017.
 */

/**
 * https://en.wikipedia.org/wiki/Depth-first_search
 */
public class DepthFirstSearch extends ASearchingAlgorithm {

    public DepthFirstSearch() {
        super.name = "DepthFirstSearch";
    }

    @Override
    public Solution solve(ISearchable s) {
        ArrayList<AState> visited = new ArrayList<AState>();
        Stack<AState> stack = new Stack<AState>();
        setNumberOfNodesEvaluated(1);
        stack.push(s.getInitialState());
        while(!stack.isEmpty()){
            AState v = stack.pop();
            setNumberOfNodesEvaluated(getNumberOfNodesEvaluated()+1);
            if(v.equals(s.getGoalState()))
                return backTrace(v,s.getInitialState());
            if(!visited.contains(v)){
                visited.add(v);
                ArrayList<AState> adjacentV = s.getAllPossibleStates(v);
                for(AState u: adjacentV){
                    if(!visited.contains(u)){
                        u.setCameFrom(v);
                        stack.push(u);
                    }

                }
            }
        }
        return null;
    }
}
