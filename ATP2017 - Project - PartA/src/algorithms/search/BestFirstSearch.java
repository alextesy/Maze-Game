package algorithms.search;

import java.util.*;

/**
 * Created by Tal and Alex on 05/04/2017.
 */

/**
 * https://en.wikipedia.org/wiki/Best-first_search
 */
public class BestFirstSearch extends ASearchingAlgorithm {
    public BestFirstSearch() {
        super.name = "BestFirstSearch";
    }

    @Override
    public Solution solve(ISearchable s) {
        PriorityQueue<AState> openList = new PriorityQueue<>((o1, o2) -> Double.compare(o1.getCost(), o2.getCost()));
        HashSet<AState> closedSet = new HashSet<>();

        openList.add(s.getInitialState());
        setNumberOfNodesEvaluated(getNumberOfNodesEvaluated());
        while (openList.size()>0){
            AState n = openList.poll();
            closedSet.add(n);
            setNumberOfNodesEvaluated(getNumberOfNodesEvaluated()+1);
            if(n.equals(s.getGoalState()))
                return backTrace(n,s.getInitialState());
            ArrayList<AState> successors = s.getAllPossibleStates(n);
            for(AState state: successors){
                if(!closedSet.contains(state) && !openList.contains(state)){
                    state.setCameFrom(n);
                    openList.add(state);
                }
                else if(state.getCost() > n.getCost()+1){
                    if(!openList.contains(state))
                        openList.add(state);
                    else
                        state.setCameFrom(n);
                }

            }

        }
        return null;
    }

}
