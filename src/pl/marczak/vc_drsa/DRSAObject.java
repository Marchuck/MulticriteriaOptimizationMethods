package pl.marczak.vc_drsa;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Marczak on 2016-12-28.
 */
public class DRSAObject {

    public final String name;

    public List<Attribute> attributes = new ArrayList<>();


    public DRSAObject(String name) {
        this.name = name;
    }

    public DRSAObject withAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
        return this;
    }

    public int compare(DRSAObject object, String selectedAttributeName) {

        Attribute otherAttribute = find(object.attributes, selectedAttributeName);
        Attribute a = find(attributes, selectedAttributeName);
        if (a == null) return 0;
        return a.compareTo(otherAttribute);
    }

    static Attribute find(List<Attribute> attrs, String name) {
        for (Attribute attribute : attrs) if (attribute.name.equals(name)) return attribute;
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}

