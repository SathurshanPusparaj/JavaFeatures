package org.projectx.javafeatures.stream;

import java.util.function.Consumer;
import java.util.stream.Stream;

public class StreamFeature {
    public static void main(String[] args) {
        //JAVA 9

        //Stream.iterate("", s-> s+"s").takeWhile( s-> s.length() < 10).forEach(System.out::println);
        Stream.iterate("", s-> s+"s").dropWhile( s-> s.length() < 10).forEach(System.out::println);

        //Stream.iterate("hg", s-> s.length() < 10, s-> s+"s").forEach(System.out::println);


        //JAVA 11
        // Local-Variable Type Inference (var/String/Integer) for lambda parameters
        Consumer<String> localVariableTypeInference = (String name) -> System.out.println("hi," + name);



    }
}
