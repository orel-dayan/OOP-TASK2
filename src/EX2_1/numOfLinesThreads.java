package EX2_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 A class that implements a thread for counting the number of lines in a text file.
 */
public class numOfLinesThreads extends Thread {

    private int count = 0;
    private String name;

	/**
	 *
	 * @param name the name of the file
	 */

    public numOfLinesThreads(String name) {
        super(name);
        this.name = name;
    }


 @Override
    public void run() {
        try {
			this.name= name;
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


