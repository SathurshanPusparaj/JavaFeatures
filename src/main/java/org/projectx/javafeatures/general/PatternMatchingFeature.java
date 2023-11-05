package org.projectx.javafeatures.general;

public class PatternMatchingFeature {
    public static void main(String[] args) {

        Object obj = "Hello, Mark";

        if (obj instanceof String) {
            String str  = (String) obj;
            System.out.println(str + " antony.");
        }

        //Pattern matching instanceof
        if (obj instanceof String s) {
            System.out.println(s + ", pattern matching");
        }
    }
}
