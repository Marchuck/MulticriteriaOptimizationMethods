package MCDA.definitions;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Łukasz Marczak on 2016-12-30.
 */
public class MCDACommons {

//    public static <T> boolean isEmpty(Collection<T> collection) {
//        return collection == null || collection.isEmpty();
//    }

    public static <T> boolean isEmpty(Collection<T> collection) {
        return collection == null || collection.isEmpty();
    }

    public static <T> boolean isEmpty(T[] collection) {
        return collection == null || collection.length == (0);
    }
    public static  boolean isEmpty(double[] collection) {
        return collection == null || collection.length == (0);
    }


    public static <T> String printCollection(Collection<T> collection) {
        if (isEmpty(collection)) return "[]";

        StringBuilder stringBuilder = new StringBuilder();
        Iterator<T> it = collection.iterator();
        if (it.hasNext()) {
            stringBuilder.append("[").append(String.valueOf(it.next()));
        }

        while (it.hasNext()) {
            stringBuilder.append(",").append(String.valueOf(it.next()));
        }

        return stringBuilder.append("]").toString();
    }

    public static String fillWithZeros(int fullLen, int lineNumber) {
        int len = String.valueOf(lineNumber).length();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < fullLen - len; i++) sb.append("0");

        return sb.append(lineNumber).toString();
    }

    public interface PrintStrategy<T> {
        String print(T t);
    }

    public static <T> String printCollectionWith(Collection<T> collection, PrintStrategy<T> strategy) {
        if (isEmpty(collection)) return "[]";

        StringBuilder stringBuilder = new StringBuilder();
        Iterator<T> it = collection.iterator();
        if (it.hasNext()) {
            stringBuilder.append("[").append(strategy.print(it.next()));
        }

        while (it.hasNext()) {
            stringBuilder.append(",").append(strategy.print(it.next()));
        }

        return stringBuilder.append("]").toString();
    }

}
