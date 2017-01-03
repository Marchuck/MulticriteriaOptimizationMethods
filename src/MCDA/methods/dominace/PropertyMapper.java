package MCDA.methods.dominace;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Łukasz Marczak on 2017-01-02.
 */
@Deprecated
public class PropertyMapper {

    double j = 0.0;

    Map<String, Double> properties = new HashMap<>();

    public PropertyMapper() {

    }

    public void put(String property) {
        properties.put(property, ++j);
    }

    public boolean contains(String prop) {
        return properties.containsKey(prop);
    }

}
