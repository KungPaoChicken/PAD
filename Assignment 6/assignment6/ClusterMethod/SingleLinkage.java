package assignment6.ClusterMethod;

import assignment6.Cluster.Cluster;
import assignment6.DistanceMeasure.DistanceMeasure;

public class SingleLinkage implements ClusterMethod {
    private static final String NAME = "Single Linkage";
    private DistanceMeasure method;

    public SingleLinkage(DistanceMeasure distanceMeasure) {
        method = distanceMeasure;
    }

    @Override
    public double calculateDistance(Cluster cluster1, Cluster cluster2) {
        double minDistance = Double.MAX_VALUE;
        for (int i = 0; i < cluster1.getUnits().size(); i++) {
            for (int j = 0; j < cluster2.getUnits().size(); j++) {
                minDistance = Math.min(minDistance, method.calculateDistance(cluster1.getUnits().unitAt(i), cluster2.getUnits().unitAt(j)));
            }
        }
        return minDistance;
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
