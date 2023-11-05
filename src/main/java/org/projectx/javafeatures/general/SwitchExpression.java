package org.projectx.javafeatures.general;

public class SwitchExpression {

    enum Status {
        NOT_STARTED, STARTED, FINISHED
    }

    public static void main(String[] args) {
        Status status = Status.STARTED;

        //Old Switch statement
        switch (status) {
            case NOT_STARTED:
                System.out.println("NOT_STARTED");
                break;
            case STARTED:
                System.out.println("STARTED");
                break;
            case FINISHED:
                System.out.println("FINISHED");
                break;
            default:
                System.out.println("Case not found");
                break;
        }

        // JAVA 14
        // Swith Expression - power of lamba expression
        switch (status) {
            case NOT_STARTED -> System.out.println("expression - NOT_STARTED");
            case STARTED, FINISHED -> System.out.println("expression - completed");
            default -> System.out.println("Case not found");
        }

        // JAVA 17
        // Pattern matching in switch expression
        Object obj = 20;
        switch (obj) {
            case Integer i -> System.out.println(i + " - an integer");
            case String s -> System.out.println(s + " - a String");
            default -> System.out.println("unknown");
        }

        //Switch expression for sealed classes
        SealedClassFeature.Shape shape = new SealedClassFeature.Circle();
        switch (shape) {
            case SealedClassFeature.Circle c -> System.out.println("circle");
            case SealedClassFeature.Rectangle r -> System.out.println("rectangle");
            default -> throw new IllegalStateException("Unexpected value: " + shape);
        }
    }
}
