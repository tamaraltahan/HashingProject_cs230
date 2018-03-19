import java.util.ArrayList;


public class Main {

    static ArrayList<Integer> list = new ArrayList<>();

    private static String reverse(int x) {
            int rev = 0;
            while (x != 0) {
                rev = rev * 10 + x % 10;
                x = x / 10;
            }
            return Integer.toString(rev);
    }

    //populate map with values from the Arraylist with capacity * loadfactor number of values
    private static void populateChainMap(ChainHashMap<Integer, String> map, double loadFactor){
        for(int i = 0; i < (list.size()*loadFactor); i++){
            map.put(list.get(i), reverse(list.get(i)));
        }
    }
    private static void populateProbeMap(ProbeHashMap<Integer, String> map, double loadFactor){
        for(int i = 0; i < (list.size()*loadFactor); i++){
            map.put(list.get(i), reverse(list.get(i)));
        }
    }

    //print values
    private static void printProbeMap(ProbeHashMap<Integer,String> map){
        Iterable<Entry<Integer,String>> set = map.entrySet();
        for(Entry<Integer,String> entry : set){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
    private static void printChainMap(ChainHashMap<Integer, String> map){
        Iterable<Entry<Integer,String>> set = map.entrySet();
        for(Entry<Integer,String> entry : set){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }


    public static void main(String[] args){
        ChainHashMap<Integer,String> chainmap = new ChainHashMap<>();
        ProbeHashMap<Integer,String> probeMap = new ProbeHashMap<>();
        double[] loadFactors = {0.25,0.50,0.75,0.90};


        //file io
        Reader reader = new Reader();
        reader.parseFile(); //read file
        list = reader.getList(); //populate Arraylist with all values

        //for debug
        //System.out.println("Number of values " + reader.getNumberofElements());

        //populate map with values
        populateChainMap(chainmap,loadFactors[3]);
        populateProbeMap(probeMap,loadFactors[3]);

        //print values
        System.out.println("ChainHashMap");
        printChainMap(chainmap);

        System.out.println("ProbeHashMap");
        printProbeMap(probeMap);


    }
}
