package pl.marczak.electreTri;

/**
 * MulticriteriaOptimizationMethods
 *
 * @author Lukasz Marczak
 * @since 17 gru 2016.
 * 01 : 13
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author stive
 */
public class Data {

    protected Map<Long, Cryterium> cryterias;
    protected Map<Long, Element> profiles;

    protected Map<Long, Element> alternatives;

    public Data() {
        cryterias = new HashMap<>(5);
        cryterias.put(0L, new Cryterium(0.2, "a1"));
        cryterias.put(1L, new Cryterium(0.2, "a2"));
        cryterias.put(2L, new Cryterium(0.2, "a3"));
        cryterias.put(3L, new Cryterium(0.2, "a4"));
        cryterias.put(4L, new Cryterium(0.2, "a5"));

        profiles = new HashMap<>(3);
        Element p1 = new Element(cryterias, Arrays.asList(2d, 2d, 2d, 2d, 2d));
        profiles.put(0L, p1);
        Element p2 = new Element(cryterias, Arrays.asList(4d, 4d, 4d, 4d, 4d));
        profiles.put(1L, p2);

        alternatives = new HashMap<>(4);
        Element e1 = new Element(cryterias, Arrays.asList(5d, 5d, 5d, 5d, 5d));
        alternatives.put(0L, e1);
        Element e2 = new Element(cryterias, Arrays.asList(1d, 2d, 3d, 4d, 5d));
        alternatives.put(1L, e2);
        Element e3 = new Element(cryterias, Arrays.asList(1d, 3d, 3d, 2d, 1d));
        alternatives.put(2L, e3);
        Element e4 = new Element(cryterias, Arrays.asList(1d, 1d, 2d, 1d, 1d));
        alternatives.put(3L, e4);
    }
}
