package assignment5;

import assignment5.ClusterMethod.ClusterMethod;
import assignment5.Cluster.Node;

public class Clusterer {
    private ClusterRow clusters;
    private ClusterMethod clusterer;
    private int clusteringLimit;

    Clusterer(ClusterRow clusters, ClusterMethod method, int limit) {
        this.clusters = clusters;
        clusterer = method;
        clusteringLimit = limit;
    }

    public void cluster() {
        if (clusters.size() <= clusteringLimit) {
            return;
        }
        double distance, minDistance = Double.MAX_VALUE;
        int index1 = 0, index2 = 1;
        for (int i = 0; i < clusters.size() - 1; i++) {
            for (int j = i + 1; j < clusters.size(); j++) {
                distance = clusterer.calculateDistance(clusters.clusterAt(i), clusters.clusterAt(j));
                if (distance < minDistance) {
                    minDistance = distance;
                    index1 = i;
                    index2 = j;
                }
            }
        }
        Node newCluster = new Node(clusters.clusterAt(index1), clusters.clusterAt(index2));
        clusters.remove(index1, index2).add(newCluster);
    }
}
