package streams;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class StreamTest3 {

    public static void main(String[] args) {

        // Given a list of strings, find the longest common prefix using Java Streams
        List<String> strings = Arrays.asList("flower", "flow", "flight");

        Optional<String> reduce = strings.stream()
                .reduce((String s1, String s2) -> {
                    int length = Math.min(s1.length(), s2.length());
                    int i = 0;
                    while (i < length && s1.charAt(i) == s2.charAt(i)) {
                        i++;
                    }
                    System.out.println(
                            "log: " + "s1=" + s1 + ", s2=" + s2 +
                            ", substr=" + s1.substring(0, i)
                    );
                    return s1.substring(0, i);
                });

        System.out.println(reduce.get());

        System.out.println("=================================");

        // Find the number of occurrences of a given character in a list of strings using Java Streams
        List<String> strings2 = Arrays.asList(
                "apple", "banana", "orange", "grape", "melon"
        );
        char target = 'a';

        long count = strings2.stream()
                .flatMapToInt(CharSequence::chars)
                .filter(c -> c == target)
                .count();

        System.out.println(count);

        System.out.println("=================================");

        // Find the number of occurrences of a given character in a list of strings (alternative approach)
        List<String> strings3 = Arrays.asList(
                "apple", "banana", "orange", "grape", "melon"
        );
        String target2 = "a";

        long count2 = strings3.stream()
                .filter(str -> str.contains(target2))
                .flatMap(str -> Arrays.stream(str.split("")))
                .filter(s -> s.equals(target2))
                .count();

        System.out.println(count2);

        System.out.println("=================================");
    }
}
