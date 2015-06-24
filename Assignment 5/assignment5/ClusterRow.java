package assignment5;

import assignment5.Cluster.Cluster;
import assignment5.Cluster.Leaf;

import java.util.Arrays;

public class ClusterRow {
    private Cluster[] clusters;
    private int length;

    ClusterRow(Dataset data) {
        length = data.elementsSize;
        clusters = new Cluster[length];
        for (int i = 0; i < data.elementsSize; i++) {
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

    //The magical multi-shift deleting, optimizing the number of shifts
    //This comment is in memory of the massive hours spent on this feat
    //As an afterthought, maybe reverse iterative deleting is faster...
    //But I am too lazy to learn benchmarking in Java, so screw it
    //This is not proven by logic, may cause problems in debugging
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
}
