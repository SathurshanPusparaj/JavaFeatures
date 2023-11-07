package org.projectx.javafeatures.general;

/**
 * JDK 14 introduces records, which are a new kind of type declaration. Like an enum, a record is a restricted form of a class.
 * It’s ideal for "plain data carriers," classes that contain data not meant to be altered and
 * only the most fundamental methods such as constructors and accessors.
 *
 * Restrictions on Records
 *
 * The following are restrictions on the use of records:
 *
 * Records cannot extend any class
 * Records cannot declare instance fields (other than the private final fields that correspond to the components of the record component list); any other declared fields must be static
 * Records cannot be abstract; they are implicitly final
 * The components of a record are implicitly final
 * Beyond these restrictions, records behave like regular classes:
 *
 * You can declare them inside a class; nested records are implicitly static
 * You can create generic records
 * Records can implement interfaces
 * You instantiate records with the new keyword
 * You can declare in a record's body static methods, static fields, static initializers, constructors, instance methods, and nested types
 * You can annotate records and a record's individual components
 */
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


        // Record Patterns
        record Name(String fName, String lName, int age) { };
        Name host = new Name("William", "Koranus", 21);

        // Deconstructors
        if (host instanceof Name(var first, var last, var age)) {
            System.out.println(first + " " + last + " - " + age);
        }

        record Address(String addressLine, String city){ };
        Address address = new Address("31st main road", "NY");

        record Person(Name name, Address address){ };
        Person person = new Person(host, address);

        // Deconstructors - with nested record patterns
        if (person instanceof Person(Name(var first, var last, var age), Address(var add, var cit))) {
            System.out.println(first + " " + last + " - " + age);
            System.out.println(add + " " + cit);
        }

        // Record patterns with switch expression
        String printName = switch (host) {
            case Name(var first, var last, var age) -> "Record patterns with switch expression " + first + " " + last + " - " + age;
        };
        System.out.println(printName);


    }
}
