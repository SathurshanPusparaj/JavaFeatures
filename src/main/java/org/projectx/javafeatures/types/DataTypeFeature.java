package org.projectx.javafeatures.types;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class DataTypeFeature {
    public static void main(String[] args) throws IOException {

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

        //Java 21
        System.out.println(new StringBuilder()
                .append("String builder repeat feature: ")
                .repeat("123", 5));

        try (ExecutorService ex = Executors.newCachedThreadPool()) {
            System.out.println("executor service, fork join pool, httpclient is auto closable");
        }

        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        int charLength = chars.length;
        for (int i = 0, j = charLength - 1; j > 0; i++, j--) {
            int r = (i * j + chars[i] + chars[j]) % charLength;
            char temp = chars[i];
            chars[i] = chars[r];
            chars[r] = temp;
        }
    }
}
