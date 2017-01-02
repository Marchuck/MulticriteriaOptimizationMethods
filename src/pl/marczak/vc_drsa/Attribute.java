package pl.marczak.vc_drsa;

import org.w3c.dom.Attr;

/**
 * Created by Łukasz Marczak on 2016-12-28.
 */
public class Attribute implements Comparable<Attribute> {

    public final String name;
    public final float value;


    public Attribute(  String name, float value) {

        this.name = name;
        this.value = value;
    }

    @Override
    public int compareTo(Attribute o) {
        return Float.compare(value, o.value);
    }
}
