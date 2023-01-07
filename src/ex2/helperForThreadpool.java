package ex2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class helperForThreadpool implements Callable <Integer>{
    int countLine=0;
    File tempFile;
    Scanner reader;
public helperForThreadpool(String nameFile) throws FileNotFoundException {
    tempFile = new File(nameFile);
    reader = new Scanner(tempFile);
}
    @Override
    public Integer call() throws Exception {
        while (reader.hasNextLine()) { // if we got \n
            reader.nextLine();
            countLine++;
        }
        reader.close();
        return countLine;
    }
}
