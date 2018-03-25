import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Reader {

    //"C:\\Users\\Tamar\\Downloads\\p1large.txt";
    private static final String file = "C:\\Users\\Tamar\\Downloads\\p1large.txt";
    //private static final String file = "C:\\Users\\Tamar\\Desktop\\p1small-1.txt";
    //private static final String file = "C:\\Users\\tamar\\Desktop\\stuff\\CS stuff\\p1small-1.txt";

    private ArrayList<Integer> list = new ArrayList<>();
    private int totalNumbers;

    public void parseFile() {
        BufferedReader reader = null;
        try {
            String text;
            reader = new BufferedReader(new FileReader(file));
            totalNumbers = Integer.parseInt(reader.readLine());
            while ((text = reader.readLine()) != null) {
                String numLine = reader.readLine();
                String[] numbers = numLine.split("\\s+"); //split by space
                for (int i = 0; i < numbers.length; i++) {
                    list.add(Integer.parseInt(numbers[i]));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
        }
    }

    public int getNumberofElements() {
        return totalNumbers;
    }

    public ArrayList<Integer> getList() {
        return list;
    }

    public void printList() { //for debugging
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

}
