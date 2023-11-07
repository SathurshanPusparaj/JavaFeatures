package org.projectx.javafeatures.stream;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamFeature {
    public static void main(String[] args) {
        //JAVA 9

        Stream.iterate("", s-> s+"s").takeWhile( s-> s.length() < 5).forEach(System.out::println);
        //Stream.iterate("hg", s-> s.length() < 10, s-> s+"s").forEach(System.out::println);

        IntStream.range(4, 10).boxed().dropWhile(num -> (num / 4 == 1)).forEach(System.out::println);

        //JAVA 10
        Stream.of(1,2,3,4,5)
                .collect(Collectors.toUnmodifiableList());
                //.collect(Collectors.toUnmodifiableSet());

        Map<Integer, Integer> collectUnModifiableMap = IntStream.range(1, 6)
                .boxed()
                .collect(Collectors.toUnmodifiableMap(k -> k, v -> v * 2));

        System.out.println(collectUnModifiableMap);

        //JAVA 11
        // Local-Variable Type Inference (var/String/Integer) for lambda parameters
        Consumer<String> localVariableTypeInference = (String name) -> System.out.println("hi," + name);



    }
}
