package org.projectx.javafeatures.general;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexFeature {
    public static void main(String[] args) {

        //Java 21
        //Named groups - allow you to give capturing groups a name, and get them in your code using that name

        String line  = "1;New York;8 336 817";

        Pattern pattern = Pattern.compile("""
            (?<index>\\d+);\
            (?<city>[ a-zA-Z]+);\
            (?<population>[ \\d]+)$""");


        Matcher matcher = pattern.matcher(line);

        if (matcher.matches()) {
            System.out.println(matcher.group("index"));
            System.out.println(matcher.group("city"));
            System.out.println(matcher.group("population"));
        }


    }
}
