package EX2_1;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class numOfLinesThreads extends Thread{

    private int count = 0;
    String name;

    public numOfLinesThreads(String name) {
        super(name);
        this.name = name;
    }

    public void run() {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(name));
                while (reader.readLine() != null) {
                    count++;
                }
            } catch (IOException e) {
                System.out.println("Error: " + e);
            }
        }


    public int getCount() {
        return this.count;
    }
}


