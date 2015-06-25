package assignment6.ClusterMethod;

import assignment6.Cluster.Cluster;
import assignment6.DistanceMeasure.DistanceMeasure;

public class AverageLinkage implements ClusterMethod {
    private static final String NAME = "Average Linkage";
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

    @Override
    public DistanceMeasure getDistanceMeasure() {
        return method;
    }

    @Override
    public void setDistanceMeasure(DistanceMeasure distanceMeasure) {
        method = distanceMeasure;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
