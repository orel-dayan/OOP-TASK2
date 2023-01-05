package EX2_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;


public class numOfLinesThreadPool implements Callable<Integer> {
    private String name;

    public numOfLinesThreadPool(String name) {
        this.name = name;
    }

    @Override
    public Integer call() throws Exception {
        String name = this.name;
        int numLines = 0;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(name));
            while (reader.readLine() != null) {
                numLines++;
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);

        }

        return numLines;
    }


}







