import java.util.ArrayList;
public class ClusterExtractor{

    public ClusterExtractor(AbstractMap.MapEntry<Integer,String>[] arr){
        table = arr;
    }

    private static ArrayList<Integer> clusterSizes = new ArrayList<>();;

    private static AbstractMap.MapEntry<Integer,String>[] table;
    private static AbstractMap.MapEntry<Integer,String> DEFUNCT = new AbstractMap.MapEntry<>(null, null);

    private static int largestCluster = 0;
    private static int numberofClusters = 0;
    private static int averageClusterSize = 0;

    //getters
    public int getLargestCluster(){return largestCluster;}
    public int getNumberofClusters(){return numberofClusters;}
    public int getAverageClusterSize(){return averageClusterSize;}

    //for debug
    public static void printTable() {
        for (int i = 0; i < table.length; i++) {
            System.out.print(table[i] + " ");
        }
        System.out.println();
    }

    private static boolean isCluster(int i) {
        return table[i] != null && table[i+1] != null;
    }

    public static void parseClusters(){
        int clusterSize = 0;
        for(int i = 0; i < table.length-1; i++ ){
            if(isCluster(i)){
                clusterSize++;
            }
            else if(i > 0 && isCluster(i-1)){
                clusterSize++;
                numberofClusters++;
                clusterSizes.add(clusterSize);
                if(clusterSize > largestCluster) largestCluster = clusterSize;
                clusterSize = 0;
            }
        }
        if(table[0] == null &&  isCluster(table.length-2)){
            numberofClusters++;
            clusterSize++;
            clusterSizes.add(clusterSize);
            if(clusterSize > largestCluster) largestCluster = clusterSize;
            clusterSize = 0;
        }
        if(table[0] != null && table[0] != DEFUNCT &&  table[table.length-1] != null){
            clusterSize++;
            if(clusterSize > largestCluster) largestCluster = clusterSize;
            if(clusterSizes.size() != 0) {
                clusterSizes.set(0, clusterSize + clusterSizes.get(0));
            }
            else{
                clusterSizes.add(clusterSize);
            }
            if(!isCluster(0)){
                numberofClusters++;
                clusterSize++;
                clusterSizes.add(clusterSize);
                if(clusterSize > largestCluster) largestCluster = clusterSize;
                //clusterSize = 0;
            }
        }

        for (int i = 0; i < clusterSizes.size(); i++) {
            averageClusterSize += clusterSizes.get(i);
        }
        if(numberofClusters != 0) {
            averageClusterSize /= numberofClusters;
        }
        else{
            averageClusterSize = 0;
        }
    }

    //for debug
    public static void printInfo() {
        System.out.println("Largest cluster: " + largestCluster + "\nNumber of clusters " + numberofClusters
                + "\nAverage cluster size: " + averageClusterSize);
    }

}
