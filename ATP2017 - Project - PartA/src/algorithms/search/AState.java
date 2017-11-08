package algorithms.search;

/**
 * An abstract type class for all kinds of States.
 */
public abstract class AState {
    private String name;
    private AState cameFrom;
    protected double cost;

    public AState(){this.name = "default";};
    public AState(String name, double cost){
        this.name = name;
        this.cost = cost;
        this.cameFrom = null;
    }

    public String getName() {
        return name;
    }
    public AState getCameFrom(){
        return cameFrom;
    }

    public void setCameFrom(AState cameFrom) {
        this.cameFrom = cameFrom;
        this.cost = cameFrom.getCost() + 1;
    }
    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return name ;
    }

    @Override
    public boolean equals(Object other) {
        boolean result = false;
        if (other instanceof AState) {
            AState that = (AState) other;
            result = this.name == ((AState) other).name;
        }
        return result;
    }
}
