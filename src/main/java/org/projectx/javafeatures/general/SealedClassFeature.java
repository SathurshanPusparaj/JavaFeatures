package org.projectx.javafeatures.general;

public class SealedClassFeature {

     static sealed class Shape permits Circle, Rectangle {
          boolean isValid() {
               return true;
          }
     }

     static final class Circle extends Shape {

     }

     static final class Rectangle extends Shape{

     }


}
