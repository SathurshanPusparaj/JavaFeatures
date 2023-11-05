package org.projectx.javafeatures.collections;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionFeature {
    public static void main(String[] args) {
        // JAVA 9
        List<String> list = List.of("one", "two" , "three");
        System.out.println(list);

        Set<String> set = Set.of("first", "second", "thrid");
        System.out.println(set);

        //Map.of("key1", "value1", "key2", "value2");
        Map<String, String> map = Map.of("foo", "one", "bar", "two");
        System.out.println(map);
    }
}
