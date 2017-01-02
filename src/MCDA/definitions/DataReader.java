package MCDA.definitions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Łukasz Marczak on 2017-01-01.
 */
public class DataReader<T> {


    boolean shouldSkipFirstLine;


    String firstLine = "";


    String[] propertyNames;


    public String[] getPropertyNames(String separator, int numberOfProperties) {
        return getPropertyNamesByLazy(separator, numberOfProperties);
    }

    public String[] getPropertyNamesByLazy(String separator, int numberOfProperties) {
        if (propertyNames == null) propertyNames = getPropertyNamesByLazyImpl(separator, numberOfProperties);
        return propertyNames;
    }

    String[] getPropertyNamesByLazyImpl(String separator, int numberOfProperties) {

        if (hasFirstLine()) {
            return firstLine.split(separator);
        } else {
            String[] properties = new String[numberOfProperties];
            for (int i = 0; i < numberOfProperties; i++) {
                properties[i] = generatePropertyId(i, numberOfProperties);
            }
            return properties;
        }
    }

    public static void main(String[] args) {
        DataReader a = new DataReader<String>(false);
        System.out.println("Properties length: 12");
        System.out.println("id = 3, name: " + a.generatePropertyId(3, 12));
    }

    private String generatePropertyId(int propertyIndex, int numberOfProperties) {
        int digits = String.valueOf(numberOfProperties).length();
        String numberAsString = String.valueOf(propertyIndex);
        int currentLength = numberAsString.length();
        StringBuilder stringBuilder = new StringBuilder("A");
        for (int j = 0; j < (digits - currentLength); j++) {
            stringBuilder.append("0");
        }
        stringBuilder.append(numberAsString);
        return stringBuilder.toString();
    }

    public interface ReadStrategy<T> {
        T read(String line, int lineNumber);

    }

    public boolean hasFirstLine() {
        return firstLine != null;
    }

    public String getFirstLine() {
        return firstLine;
    }

    public DataReader(boolean _shouldSkipFirstLine) {
        shouldSkipFirstLine = _shouldSkipFirstLine;
    }

    public List<T> read(File file, ReadStrategy<T> readStrategy) {
        List<T> list = new ArrayList<>();

        Scanner input = null;

        try {
            input = new Scanner(file);
            //input.nextLine();
            int newLineIndex = 0;
            while (input.hasNextLine()) {
                //read next line

                String nextLine = input.nextLine();

                //save record as four variables
                if (shouldSkipFirstLine) {
                    firstLine = nextLine;
                    shouldSkipFirstLine = false;
                    continue;
                }
                list.add(readStrategy.read(nextLine, newLineIndex));
                newLineIndex++;
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
