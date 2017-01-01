package MCDA.methods.outranking;

import pl.marczak.electreTri.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Łukasz Marczak on 2017-01-01.
 */
public class DataReader<T> {

    final ReadStrategy<T> readStrategy;
    boolean shouldSkipFirstLine;

    String firstLine = "";

    public interface ReadStrategy<T> {
        T read(String line);

    }

    public String getFirstLine() {
        return firstLine;
    }

    public DataReader(boolean _shouldSkipFirstLine, ReadStrategy<T> strategy) {
        if (strategy == null) throw new RuntimeException("Strategy not set!");
        shouldSkipFirstLine = _shouldSkipFirstLine;
        readStrategy = strategy;
    }

    public List<T> read(File file) {
        List<T> list = new ArrayList<>();

        Scanner input = null;

        try {
            input = new Scanner(file);
            //input.nextLine();

            while (input.hasNextLine()) {
                //read next line

                String nextLine = input.nextLine();

                //save record as four variables
                if (shouldSkipFirstLine) {
                    firstLine = nextLine;
                    shouldSkipFirstLine = false;
                    continue;
                }
                list.add(readStrategy.read(nextLine));
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error! " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (input != null) input.close();
        }
        return list;
    }

}
