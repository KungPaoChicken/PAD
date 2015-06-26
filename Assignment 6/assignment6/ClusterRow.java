package assignment6;

import assignment6.Cluster.Cluster;
import assignment6.Cluster.Leaf;

import java.util.Arrays;

public class ClusterRow {
    private Cluster[] clusters;
    private int length;

    ClusterRow(Dataset data) {
        length = data.getElementsSize();
        clusters = new Cluster[length];
        for (int i = 0; i < length; i++) {
            clusters[i] = new Leaf(data.unitAt(i));
        }
    }

    public ClusterRow(Cluster[] clusters) {
        length = clusters.length;
        this.clusters = new Cluster[length];
        int i = 0;
        for (Cluster cluster : clusters) {
            this.clusters[i] = cluster;
            i++;
        }
    }

    public int size() {
        return length;
    }

    public ClusterRow add(Cluster cluster) {
        clusters[length] = cluster;
        length++;
        return this;
    }

    public Cluster clusterAt(int index) {
        if (index >= length) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        return clusters[index];
    }

    //Multi-shift deleting, optimizing the number of shifts
    //Maybe reverse iterative deleting is faster...
    public ClusterRow remove(int... indexes) {
        Arrays.sort(indexes);
        length -= indexes.length;
        int shiftOffset = 1;
        for (int i = indexes[0]; i < length; i++) {
            while (i + shiftOffset == indexes[Math.min(shiftOffset, indexes.length - 1)]) {
                shiftOffset++;
            }
            clusters[i] = clusters[i + shiftOffset];
        }
        return this;
    }

    public ClusterRow copy() {
        Cluster[] clustersArray = new Cluster[length];
        for (int i = 0; i < clusters.length; i++) {
            clustersArray[i] = clusters[i];
        }
        return new ClusterRow(clustersArray);
    }
}
