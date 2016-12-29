package pl.marczak.vc_drsa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Łukasz Marczak on 2016-12-29.
 */
public class TestUnion {

    public static void main(String[] args) {

        /**
         * Comprehensive evaluation
         *
         * example from page 548 (book 512)
         * from table 13.1.
         */

        List<DRSAObject> students = new ArrayList<>();

        List<Attribute> s1_attrs = new ArrayList<>();
        s1_attrs.add(new Attribute("Mathematics", 2));
        s1_attrs.add(new Attribute("Physics", 1));
        s1_attrs.add(new Attribute("Literature", 0));
        s1_attrs.add(new Attribute("Comprehensive evaluation", 0));
        DRSAObject S1 = new DRSAObject("S1").withAttributes(s1_attrs);
        students.add(S1);

        List<Attribute> s2_attrs = new ArrayList<>();
        s2_attrs.add(new Attribute("Mathematics", 1));
        s2_attrs.add(new Attribute("Physics", 1));
        s2_attrs.add(new Attribute("Literature", 0));
        s2_attrs.add(new Attribute("Comprehensive evaluation", 1));
        students.add(new DRSAObject("S2").withAttributes(s2_attrs));

        List<Attribute> s3_attrs = new ArrayList<>();
        s3_attrs.add(new Attribute("Mathematics", 1));
        s3_attrs.add(new Attribute("Physics", 1));
        s3_attrs.add(new Attribute("Literature", 1));
        s3_attrs.add(new Attribute("Comprehensive evaluation", 1));
        students.add(new DRSAObject("S3").withAttributes(s3_attrs));

        List<Attribute> s4_attrs = new ArrayList<>();
        s4_attrs.add(new Attribute("Mathematics", 2));
        s4_attrs.add(new Attribute("Physics", 2));
        s4_attrs.add(new Attribute("Literature", 1));
        s4_attrs.add(new Attribute("Comprehensive evaluation", 2));
        students.add(new DRSAObject("S4").withAttributes(s4_attrs));

        List<Attribute> s5_attrs = new ArrayList<>();
        s5_attrs.add(new Attribute("Mathematics", 2));
        s5_attrs.add(new Attribute("Physics", 1));
        s5_attrs.add(new Attribute("Literature", 2));
        s5_attrs.add(new Attribute("Comprehensive evaluation", 2));
        students.add(new DRSAObject("S5").withAttributes(s5_attrs));

        List<Attribute> s6_attrs = new ArrayList<>();
        s6_attrs.add(new Attribute("Mathematics", 2));
        s6_attrs.add(new Attribute("Physics", 2));
        s6_attrs.add(new Attribute("Literature", 2));
        s6_attrs.add(new Attribute("Comprehensive evaluation", 2));
        students.add(new DRSAObject("S6").withAttributes(s6_attrs));

        List<Attribute> s7_attrs = new ArrayList<>();
        s7_attrs.add(new Attribute("Mathematics", 0));
        s7_attrs.add(new Attribute("Physics", 0));
        s7_attrs.add(new Attribute("Literature", 0));
        s7_attrs.add(new Attribute("Comprehensive evaluation", 0));
        students.add(new DRSAObject("S7").withAttributes(s7_attrs));

        List<Attribute> s8_attrs = new ArrayList<>();
        s8_attrs.add(new Attribute("Mathematics", 0));
        s8_attrs.add(new Attribute("Physics", 0));
        s8_attrs.add(new Attribute("Literature", 1));
        s8_attrs.add(new Attribute("Comprehensive evaluation", 0));
        students.add(new DRSAObject("S8").withAttributes(s8_attrs));

        List<DRSAObject> dominating =
                new RoughApproximation(RoughApproximation.Kind.PLUS, S1)
                        .fromDataset(students)
                        .ofAttributes("Mathematics", "Physics")
                        .evaluate();
        for (DRSAObject object : dominating) {
            System.out.println("S1 dominates " + object);
        }

        List<DRSAObject> dominated =
                new RoughApproximation(RoughApproximation.Kind.MINUS, S1)
                        .fromDataset(students)
                        .fromDataset(students)
                        .ofAttributes("Mathematics", "Physics")
                        .evaluate();
        for (DRSAObject object : dominated) {
            System.out.println("S1 is dominated by " + object);
        }

    }


}
