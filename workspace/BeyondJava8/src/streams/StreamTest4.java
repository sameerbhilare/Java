package streams;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

class Employee {
    String name;
    double salary;
    String department;

    Employee(String name, double salary, String department) {
        this.name = name;
        this.salary = salary;
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public String toString() {
        return "Employee {" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                '}';
    }
}
public class StreamTest4 {

    public static void main() {

        List<Employee> employees = List.of(
                new Employee("Alice", 70000, "HR"),
                new Employee("Bob", 80000, "Engineering"),
                new Employee("Charlie", 60000, "HR"),
                new Employee("David", 90000, "Engineering"),
                new Employee("Eve", 50000, "HR")
        );

        // 1. (Hard) Find the second-highest salaried employee per department.
        // Need map of department and second-highest salary Employee object.
        Map<String, Optional<Employee>> secondHigestSalariedEmpByDepartment = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.collectingAndThen(
                                Collectors.toList(), // collect employees into a list of Employee

                                // apply on collected employees
                                empList -> empList.stream()
                                        .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                                        .skip(1)
                                        .findFirst()
                        )));
        System.out.println("Second highest salary employee for each department => " + secondHigestSalariedEmpByDepartment);

        // 2. (Hard) Find the second-highest salary per department.
        // Need map of department and second-highest salary only not entire Employee object.
        Map<String, Optional<Double>> secondHighestSalaryByDepartment = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment,
                        Collectors.mapping(Employee::getSalary, // project salaries
                                Collectors.collectingAndThen(
                                        Collectors.toList(), // collect salaries into list of Double

                                        // apply on collected salaries
                                        salaries -> salaries.stream()
                                                .sorted(Comparator.reverseOrder())
                                                .skip(1)
                                                .findFirst()
                                ))));
        System.out.println("Second highest salary for each department => " +secondHighestSalaryByDepartment);

        /* 3. Third Most Frequent Element
        Problem:
            Given an array of integers arr[], find the element that has the third highest frequency.
            If multiple elements have the same frequency, choose the largest numerical value among them.
            If fewer than 3 distinct frequency levels exist, return -1.

        Constraints:
            Ignore negative numbers.

        Input:
            int arr[] = {4, 4, 1, 2, 2, 3, 3, 3, 5, 5, 5, 5};

        Output:
            4
         */
        int arr[] = {4, 4, 1, 2, 2, 3, 3, 3, 5, 5, 5, 5};

        int result =
                Arrays.stream(arr)
                        .filter(n -> n >= 0) // ignore negatives
                        // must be boxed bcz groupingBy() required Object stream, and without boxed() we get primitive stream (IntStream)
                        .boxed()
                        .collect(Collectors.groupingBy(
                                n -> n,
                                Collectors.counting()
                        ))
                        .entrySet()
                        .stream()

                        // group numbers by frequency
                        .collect(Collectors.groupingBy(
                                Map.Entry::getValue,
                                Collectors.mapping(Map.Entry::getKey, Collectors.toList())
                        ))
                        .entrySet()
                        .stream()

                        // sort frequencies descending
                        .sorted(Map.Entry.<Long, List<Integer>>comparingByKey(Comparator.reverseOrder()))
                        .skip(2) // third highest frequency
                        .findFirst()

                        // pick the largest number among same frequency
                        .map(e -> Collections.max(e.getValue()))
                        .orElse(-1);

        System.out.println(result);

    }
}
