package streams;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Person {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }
}

class Transaction {
    String date;
    int amount;

    Transaction(String date, int amount) {
        this.date = date;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}

public class StreamTest2 {

    public static boolean isPrime(int number) {
        if (number <= 1) return false;

        int limit = (int) Math.sqrt(number); // performance improvement - If no divisor is found up to sqrt of number, none will exist beyond it.
        for (int i = 2; i <= limit; i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {

        // Find the longest string in a list of strings using Java Streams
        List<String> strings = Arrays.asList(
                "apple", "banana", "cherry", "date", "grapefruit"
        );

        System.out.println("=================================");
        String longest = strings.stream()
                .max(Comparator.comparing(String::length))
                .get();
        System.out.println(longest);

        // Calculate the average age of a list of Person objects using Java Streams
        List<Person> persons = Arrays.asList(
                new Person("Alice", 25),
                new Person("Bob", 30),
                new Person("Charlie", 35)
        );

        System.out.println("=================================");
        double average = persons.stream()
                .mapToInt(Person::getAge)
                .average()
                .orElse(0);

        System.out.println(average);

        // Check if a list of integers contains a prime number using Java Streams
        List<Integer> numbers = Arrays.asList(
                2, 4, 6, 8, 10, 11, 12, 13, 14, 15
        );

        boolean isPrime = numbers.stream()
                .anyMatch(StreamTest2::isPrime);

        System.out.println(isPrime);

        // Merge two sorted lists into a single sorted list using Java Streams
        List<Integer> list1 = Arrays.asList(1, 3, 5, 7, 9);
        List<Integer> list2 = Arrays.asList(2, 4, 6, 8, 10);

        List<Integer> mergedList = Stream
                .concat(list1.stream(), list2.stream())
                .sorted()
                .collect(Collectors.toList());

        System.out.println(mergedList);

        // Find the intersection of two lists using Java Streams
        List<Integer> list11 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list22 = Arrays.asList(3, 4, 5, 6, 7);

        List<Integer> intersectedList = list11.stream()
                .filter(list22::contains)
                .collect(Collectors.toList());

        System.out.println(intersectedList);

        // Remove duplicates from a list while preserving the order using Java Streams
        List<Integer> numbersWithDuplicates = Arrays.asList(1, 2, 3, 2, 4, 5, 3, 5);

        List<Integer> distinctList = numbersWithDuplicates.stream()
                .distinct()
                .collect(Collectors.toList());

        System.out.println(distinctList);

        // Given a list of transactions, find the sum of transaction amounts for each day using Java Streams
        List<Transaction> transactions = Arrays.asList(
                new Transaction("2022-01-01", 100),
                new Transaction("2022-01-01", 200),
                new Transaction("2022-01-02", 300),
                new Transaction("2022-01-02", 400),
                new Transaction("2022-01-03", 500)
        );

        Map<String, Integer> collect = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate,
                        Collectors.summingInt(Transaction::getAmount)
                ));

        System.out.println(collect);

        // Find the kth smallest element in an array using Java Streams
        int[] array = {4, 2, 7, 1, 5, 3, 8};
        int k = 3; // Find the 3rd smallest element

        int kthSmallest = Arrays.stream(array)
                .sorted()
                .skip(k - 1)
                .findFirst()
                .orElse(-1);

        System.out.println(kthSmallest);

        System.out.println("=================================");

        // Given a list of strings, find the frequency of each word using Java Streams
        List<String> words = Arrays.asList(
                "apple", "banana", "apple", "cherry", "banana", "apple"
        );

        Map<String, Long> collect1 = words.stream()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));

        System.out.println(collect1);

        System.out.println("=================================");

        // Implement a method to partition a list into two groups (odd and even)
        // based on a predicate using Java Streams
        List<Integer> numbers2 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        Map<Boolean, List<Integer>> collect2 = numbers2.stream()
                .collect(Collectors.partitioningBy(
                        num -> num % 2 == 0
                ));

        List<Integer> evens = collect2.get(true);
        List<Integer> odds = collect2.get(false);

        System.out.println(evens);
        System.out.println(odds);

        System.out.println("=================================");

    }
}
