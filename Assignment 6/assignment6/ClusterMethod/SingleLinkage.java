package assignment6.ClusterMethod;

import assignment6.Cluster.Cluster;
import assignment6.DistanceMeasure.DistanceMeasure;

public class SingleLinkage implements ClusterMethod {
    private DistanceMeasure method;

    public SingleLinkage(DistanceMeasure distanceMeasure) {
        method = distanceMeasure;
    }

    public double calculateDistance(Cluster cluster1, Cluster cluster2) {
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < cluster1.getUnits().size(); i++) {
            for (int j = 0; j < cluster2.getUnits().size(); j++) {
                minDistance = Math.min(minDistance, method.calculateDistance(cluster1.getUnits().unitAt(i), cluster2.getUnits().unitAt(j)));
            }
        }
        return minDistance;
    }
}
