package assignment6;

import assignment6.Cluster.Node;
import assignment6.ClusterMethod.ClusterMethod;

public class Clusterer {
    private ClusterRow originalClusters;
    private ClusterRow clusters;
    private ClusterMethod clusterer;
    private int clusteringLimit;

    Clusterer(ClusterRow clusters, ClusterMethod method, int limit) {
        originalClusters = clusters.copy();
        this.clusters = clusters;
        clusterer = method;
        clusteringLimit = limit;
    }

    public ClusterMethod getClusterMethod() {
        return clusterer;
    }

    public Clusterer setClusterMethod(ClusterMethod method) {
        clusterer = method;
        return this;
    }

    public Clusterer reset() {
        clusters = originalClusters.copy();
        return this;
    }

    public ClusterRow getClusters() {
        return clusters;
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
