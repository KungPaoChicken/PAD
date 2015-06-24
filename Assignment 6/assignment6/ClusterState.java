package assignment6;

public class ClusterState {

    private ClusterRow[] states;
    private int length;

    ClusterState(int clusteringSteps){
        length=clusteringSteps;
    }

    private void addState(ClusterRow clusters){
        states[length]=clusters;
        length+=1;
    }

    private ClusterRow getState(int step) throws ArrayIndexOutOfBoundsException{
        if(step>=length){
            throw new ArrayIndexOutOfBoundsException(step);
        }
        return states[step];
    }
}
