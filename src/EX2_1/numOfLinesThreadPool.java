package EX2_1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 A class that implements a Callable for counting the number of lines in a text file.
 */

public class numOfLinesThreadPool implements Callable<Integer> {
    private String name;

	/**
	 * Constructor
	 * @param name the name of the file
	 */
    public numOfLinesThreadPool(String name) {
        this.name = name;
    }

	/**
	 *
	 * @return  the number of lines in the file
	 * @throws Exception if the file is not found
	 */

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







