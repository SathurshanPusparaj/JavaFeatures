package org.projectx.javafeatures.collections;

import java.util.*;

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

        System.out.println(List.copyOf(list)); // Set.copyOf(), Map.copyOf()

        // Java 17
        Deque<String> deque = new ArrayDeque<>();
        deque.addFirst("Java 17");
        deque.addLast("Java 21");
        System.out.println(deque.getFirst());
        System.out.println(deque.getLast());

        // JAVA 21

        List<String> events = List.of("Event1", "Event2", "Event3");
        ListIterator<String> reverseIterator = events.listIterator((events.size()));
        while (reverseIterator.hasPrevious()) {
            System.out.println(reverseIterator.previous());
        }


        // sequenced collection is all about order so this collection interface not applicable to HashMap, HashSet
        /**
         * interface SequencedCollection<E> extends Collection<E> {
         *
         *   // new method
         *   SequencedCollection<E> reversed();
         *
         *   // methods promoted from Deque
         *   void addFirst(E);
         *   void addLast(E);
         *   E getFirst();
         *   E getLast();
         *   E removeFirst();
         *   E removeLast();
         * }
         */
        List<String> names = new ArrayList<>();

        names.addFirst("Chloe");
        names.add("Lisa");
        names.addLast("Abraham");

        System.out.println(names.getFirst());
        System.out.println(names.getLast());
        System.out.println(names);

    }
}
