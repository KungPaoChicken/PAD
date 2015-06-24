package assignment4;

public class ClusterRow {
    private Cluster[] clusters;
    private int length;

    ClusterRow(Dataset data){
        length = data.elementsSize;
        clusters = new Cluster[length];
        for(int i=0;i<data.elementsSize;i++){
            clusters[i]=new Leaf(data.unitAt(i));
        }
    }

    ClusterRow(Cluster[] clusters){
        length=clusters.length;
        int i=0;
        for(Cluster cluster:clusters){
            this.clusters[i]=cluster;
            i++;
        }
    }

    int size(){
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

}
