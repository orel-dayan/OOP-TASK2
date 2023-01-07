package ex2;

import java.io.FileNotFoundException;

import static EX2_1.EX2_1.getNumOfLinesThreadPool;
import static EX2_1.EX2_1.getNumOfLinesThreads;
import static ex2.EX2_1.createTextFiles;
import static ex2.EX2_1.getNumOfLines;

public class main {
    public static void main(String[] args) throws FileNotFoundException {
        long start, end;
        int numberOfFiles = 1000;
        int maxNumberOfLines = 1500;
        String[] fileNames = createTextFiles(numberOfFiles, 2, maxNumberOfLines);
        System.out.println("Checking " + numberOfFiles + " files with " + maxNumberOfLines + " maximum lines each:");
        System.out.println("---------------------------------------------------------");
        start = System.currentTimeMillis();
        int numLines = getNumOfLines(fileNames);
        end = System.currentTimeMillis();
        System.out.println("The number of lines :" + numLines +
                " The time is took : " + (end - start) + " ms");
        System.out.println("---------------------------------------------------------");

        // Measure the run time of getNumOfLinesThreads
        start = System.currentTimeMillis();
        numLines = getNumOfLinesThreads(fileNames);
        end = System.currentTimeMillis();
        System.out.println("The number of lines using Threads:" + numLines +
                " The time is took : " + (end - start) + " ms");
        System.out.println("---------------------------------------------------------");

        // Measure the run time of getNumOfLinesThreadPool
        start = System.currentTimeMillis();
        numLines = getNumOfLinesThreadPool(fileNames);
        end = System.currentTimeMillis();
        System.out.println("The number of lines using ThreadPool :" + numLines +
                " The time is took : " + (end - start) + " ms");
    }
}
