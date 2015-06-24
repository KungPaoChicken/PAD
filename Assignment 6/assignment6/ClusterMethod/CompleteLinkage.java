package assignment6.ClusterMethod;

import assignment6.Cluster.Cluster;
import assignment6.DistanceMeasure.DistanceMeasure;

public class CompleteLinkage implements ClusterMethod {
    private DistanceMeasure method;

    public CompleteLinkage(DistanceMeasure distanceMeasure) {
        method = distanceMeasure;
    }

    public double calculateDistance(Cluster cluster1, Cluster cluster2) {
        double maxDistance = 0;
        for (int i = 0; i < cluster1.getUnits().size(); i++) {
            for (int j = 0; j < cluster2.getUnits().size(); j++) {
                maxDistance = Math.max(maxDistance, method.calculateDistance(cluster1.getUnits().unitAt(i), cluster2.getUnits().unitAt(j)));
            }
        }
        return maxDistance;
    }
}
