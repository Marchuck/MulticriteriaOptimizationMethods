package pl.marczak.electreTri;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * MulticriteriaOptimizationMethods
 *
 * @author Lukasz Marczak
 * @since 17 gru 2016.
 * 01 : 13
 */
public class Element {

    double value = 0;
    int clasOptym = 0;
    int clasPesym = 0;
    List<Double> data;

    public Element(Map<Long, Cryterium> cryterias, List<Double> values) {
        data = new ArrayList<Double>(values.size());

        for (Double d : values) {
            data.add( d);
        }

        for (Long crId : cryterias.keySet()) {
            value += cryterias.get(crId).weight*data.get(crId.intValue());
        }
        int a = 3;
    }
}
