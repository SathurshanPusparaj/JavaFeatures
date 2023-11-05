package org.projectx.javafeatures.types;

import java.util.stream.Collectors;

public class DataTypeFeature {
    public static void main(String[] args) {

        //Java 10
        //Type inference
        String myName = "Martha";
        var newName = "Stephan";

        //Java 11
        System.out.println("Rock".isBlank());
        System.out.println("Thor\nloves\nhis\nhammer".lines().collect(Collectors.toList()));
        System.out.println("\t strip \n".strip()); //does the same job as trim() but it is unicode aware
        System.out.println(" K stripLeading   ".stripLeading());// removes the whitespace at the end of the string
        System.out.println(" R stripTrailing   ".stripTrailing());// removes the whitespace at the beginning of the string

        //JAVA 15
        //Multiline String/ Text blocks
        String html = """
                <html>
                    <body>
                        <p>Hello, World</p>
                    </body>
                </html>
                """;
        System.out.println(html);
    }
}
