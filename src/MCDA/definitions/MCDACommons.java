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

    public static boolean anyEmpty(Collection<?>... collections) {
        for (Collection<?> c : collections) {
            if (c.isEmpty()) return true;
        }
        return false;
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

}
