package org.projectx.javafeatures.optionals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

public class OptionalFeature {

    @Getter
    @Setter
    @AllArgsConstructor
    private static class User {
        private String name;
    }

    public static void main(String[] args) {
        // JAVA 9
        Optional<User> user = Optional.ofNullable(new User("John wick"));
        user.ifPresentOrElse((u) -> System.out.println("Hi, " + u.getName()), () -> System.out.println("Bye"));
    }
}
