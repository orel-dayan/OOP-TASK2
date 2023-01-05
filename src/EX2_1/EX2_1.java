package EX2_1;

import java.io.*;
import java.util.Random;
import java.util.concurrent.*;

public class EX2_1 {
    public static void main(String[] args) {
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

        deleteFiles(fileNames);
    }

    /**
     * Creates text files with random amount of lines
     *
     * @param n     number of files
     * @param seed  seed for random
     * @param bound max number of lines in a file
     * @return array of file names
     */
    public static String[] createTextFiles(int n, int seed, int bound) {
        String[] filesNames = new String[n];
        Random rand = new Random(seed);
        for (int i = 0; i < n; i++) {
            filesNames[i] = "file_" + (i + 1) + ".txt";
            File newFile = new File(filesNames[i]);
            try {
                newFile.createNewFile();
                FileWriter writer = new FileWriter(filesNames[i]);
                int numOfLines = rand.nextInt(bound);
                for (int j = 0; j < numOfLines; j++) {
                    writer.write("This is line " + i + " in file " + +(j + 1) + "\n");
                }
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        }
        return filesNames;
    }

    public static void deleteFiles(String[] filesNames) {
        for (String fileName : filesNames) {
            File file = new File(fileName);
            file.delete();
        }
    }

    // without threads
    public static int getNumOfLines(String[] fileNames) {
        int numOfLines = 0;
        for (String fileName : fileNames) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                while (reader.readLine() != null) {
                    numOfLines++;
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("Error: " + e);
            }
        }

        return numOfLines;

    }
    // threadpool
    public static int getNumOfLinesThreadPool(String[] fileNames) {
        int numLines = 0;
        // Create a fixed-size thread pool with a thread for each file
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(fileNames.length);
        numOfLinesThreadPool[] numOfLinesThreadPools = new numOfLinesThreadPool[fileNames.length];
        Future<Integer>[] futures = new Future[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            numOfLinesThreadPools[i] = new numOfLinesThreadPool(fileNames[i]);
        }
        try {
            for (int i = 0; i < fileNames.length; i++) {
                futures[i] = executor.submit(numOfLinesThreadPools[i]);
            }
            for (Future<Integer> future : futures) {
                numLines += future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

        }
        // wait for all
        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            // handle exception
            e.printStackTrace();
        }
        return numLines;
    }

    // threads
    public static int getNumOfLinesThreads(String[] filesNames) {
        int length = filesNames.length;
        int numOfLines = 0;
        numOfLinesThreads[] countLineWithThreads = new numOfLinesThreads[length];
        for (int i = 0; i < length; i++) {
            countLineWithThreads[i] = new numOfLinesThreads(filesNames[i]);
            countLineWithThreads[i].start();
        }
        for (numOfLinesThreads thread : countLineWithThreads) {
            try {
                thread.join();
                numOfLines += thread.getCount();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return numOfLines;
    }





}




