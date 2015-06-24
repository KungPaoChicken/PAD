package assignment4.ClusterMethod;

import assignment4.Cluster;
import assignment4.DistanceMeasure.DistanceMeasure;

public class CompleteLinkage implements ClusterMethod {
    private DistanceMeasure method;

    public CompleteLinkage(DistanceMeasure distanceMeasure) {
        method = distanceMeasure;
    }

    @Override
    public double calculateDistance(Cluster cluster1, Cluster cluster2) {
        double maxDistance = 0;
        for (int i = 0; i < cluster1.getUnits().length(); i++) {
            for (int j = 0; j < cluster2.getUnits().length(); j++) {
                maxDistance = Math.max(maxDistance, method.calculateDistance(cluster1.getUnits().unitAt(i), cluster2.getUnits().unitAt(j)));
            }
        }
        return maxDistance;
    }
}
