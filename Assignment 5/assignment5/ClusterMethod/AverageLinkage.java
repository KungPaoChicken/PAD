package assignment5.ClusterMethod;

import assignment5.Cluster.Cluster;
import assignment5.DistanceMeasure.DistanceMeasure;

public class AverageLinkage implements ClusterMethod {
    private DistanceMeasure method;

    public AverageLinkage(DistanceMeasure distanceMeasure) {
        method = distanceMeasure;
    }

    @Override
    public double calculateDistance(Cluster cluster1, Cluster cluster2) {
        double totalDistance = 0;
        for (int i = 0; i < cluster1.getUnits().size(); i++) {
            for (int j = 0; j < cluster2.getUnits().size(); j++) {
                totalDistance += method.calculateDistance(cluster1.getUnits().unitAt(i), cluster2.getUnits().unitAt(j));
            }
        }
        return totalDistance / (cluster1.getUnits().size() * cluster2.getUnits().size());
    }
}
