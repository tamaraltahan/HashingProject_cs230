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
    private static void populateChainMap(ChainHashMap<Integer, String> map) {
        int val;
        String revVal;
        for (int i = 0; i < list.size(); i++) {
            val = list.get(i);
            revVal = reverse(val);
            map.put(val, revVal);
        }
    }

    private static void populateDoubleHashMap(DoubleHashMap<Integer, String> map) {
        int val;
        String revVal;
        for (int i = 0; i < list.size(); i++) {
            val = list.get(i);
            revVal = reverse(val);
            map.put(val, revVal);
        }
    }

    private static void populateProbeMap(ProbeHashMap<Integer, String> map) {
        int val;
        String revVal;
        for (int i = 0; i < list.size(); i++) {
            val = list.get(i);
            revVal = reverse(val);
            map.put(val, revVal);
        }
    }
    //

    //print values
    private static void printProbeMap(ProbeHashMap<Integer, String> map) {
        Iterable<Entry<Integer, String>> set = map.entrySet();
        for (Entry<Integer, String> entry : set) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }


    }

    private static void printChainMap(ChainHashMap<Integer, String> map) {
        Iterable<Entry<Integer, String>> set = map.entrySet();
        for (Entry<Integer, String> entry : set) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    private static void printDoubleHashMap(DoubleHashMap<Integer, String> map) {
        Iterable<Entry<Integer, String>> set = map.entrySet();
        for (Entry<Integer, String> entry : set) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
    //

    public static void main(String[] args) {
        ChainHashMap<Integer, String> chainMap = new ChainHashMap<>();
        ProbeHashMap<Integer, String> probeMap = new ProbeHashMap<>();
        DoubleHashMap<Integer, String> doubleHashMap = new DoubleHashMap<>();

        //file io
        Reader reader = new Reader();
        reader.parseFile(); //read file
        list = reader.getList(); //populate Arraylist with all values
        //print the arraylist - for debugging
//        System.out.println("The List");
//        for (int i = 0; i < list.size(); i++) {
//            System.out.print(list.get(i) + " ");
//        }
        System.out.println();
        //for debug
        //System.out.println("Number of values " + reader.getNumberofElements());

        //populate map with values
        populateChainMap(chainMap);
        System.out.println("CHAIN REACHED");
        populateProbeMap(probeMap);
        System.out.println("PROBE REACHED");
        populateDoubleHashMap(doubleHashMap);
        System.out.println("DBL REACHED");

        System.out.println("Load factor: 0.5\n");

        //print values
        System.out.println("Separate chaining");
        //printChainMap(chainMap);
        chainMap.parseClusters();
        chainMap.printClusterInfo();
        chainMap.printProbeInfo();
        System.out.println();

        System.out.println("Linear Probing");
        //printProbeMap(probeMap);
        probeMap.parseClusters();
        probeMap.printClusterInfo();
        probeMap.printProbeInfo();
        System.out.println();

        System.out.println("Double Hashing");
        //printDoubleHashMap(doubleHashMap);
        doubleHashMap.parseClusters();
        doubleHashMap.printClusterInfo();
        doubleHashMap.printProbeInfo();
        System.out.println();
    }
}
