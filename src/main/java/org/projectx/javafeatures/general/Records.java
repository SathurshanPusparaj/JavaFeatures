package org.projectx.javafeatures.general;

public class Records {

    public static void main(String[] args) {

        //JAVA 17
        //a record is a special type of class declaration aimed at reducing the boilerplate code.
        //Record can implement an interface but not extends a class
        record Point (int x, int y) {
            //It is possible to override, equals(), hashcode(), toString() methods in record
        }

        var point = new Point(2, 4);

        System.out.println(point.x() + " " + point.y());
    }
}
