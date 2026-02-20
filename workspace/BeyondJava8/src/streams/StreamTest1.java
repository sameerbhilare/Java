package streams;

import java.util.*;
import java.util.stream.*;

class Student {
    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String departmentName;
    private int joinedYear;
    private String city;
    private int rank;

    public Student(int id, String firstName, String lastName, int age, String gender,
                   String departmentName, int joinedYear, String city, int rank) {

        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.departmentName = departmentName;
        this.joinedYear = joinedYear;
        this.city = city;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", joinedYear=" + joinedYear +
                ", city='" + city + '\'' +
                ", rank=" + rank +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getJoinedYear() {
        return joinedYear;
    }

    public void setJoinedYear(int joinedYear) {
        this.joinedYear = joinedYear;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
public class StreamTest1 {

    public static void main(String[] args) {

        List<Student> list = Arrays.asList(
            new Student(1, "Rohit", "Malik", 30, "Male",
                    "Mechanical Engineering", 2015, "Mumbai", 122),
            new Student(2, "Pulkit", "Singh", 35, "Male",
                    "Computer Engineering", 2018, "Delhi", 67),
            new Student(3, "Ankit", "Patil", 25, "Female",
                    "Mechanical Engineering", 2019, "Kerala", 164),
            new Student(4, "Satish", "Ray", 30, "Male",
                    "Mechanical Engineering", 2014, "Kerala", 26),
            new Student(5, "Rohan", "Jadhav", 24, "Male",
                    "Mechanical Engineering", 2023, "Karnataka", 12),
            new Student(6, "Chetan", "Star", 24, "Male",
                    "Electronics Engineering", 2014, "Karnataka", 90),
            new Student(7, "Anur", "Vittal", 26, "Male",
                    "Computer Engineering", 2018, "Karnataka", 324),
            new Student(8, "Sonu", "Shankar", 31, "Female",
                    "Computer Engineering", 2014, "Karnataka", 433),
            new Student(9, "Monu", "Pandey", 27, "Female",
                    "Computer Engineering", 2018, "Karnataka", 7),
            new Student(10, "Shubham", "Pandey", 20, "Male",
                    "Instrumentation Engineering", 2017, "Mumbai", 98)
        );

        // 1. Find list of students whose first name starts with alphabet A
        List<Student> out1 = list.stream()
                .filter(student -> student.getFirstName().startsWith("A"))
                .collect(Collectors.toList());

        out1.forEach(System.out::println);
        System.out.println("================================");

        // 2. Group the student by department names
        Map<String, List<Student>> out2 = list.stream()
                .collect(Collectors.groupingBy(Student::getDepartmentName));

        System.out.println(out2);
        System.out.println("================================");

        // 3. Find the total count of student using stream
        long count = list.stream().count();
        System.out.println(count);

        // 4. Find the max age of student
        OptionalInt out4 = list.stream()
                .mapToInt(Student::getAge)
                .max();

        System.out.println(out4.getAsInt());
        System.out.println("================================");

        // 5. Find all departments names
        List<String> out5 = list.stream()
                .map(Student::getDepartmentName)
                .distinct()
                .collect(Collectors.toList());

        out5.forEach(System.out::println);
        System.out.println("================================");

        // 6. Find the count of student in each department
        Map<String, Long> out6 = list.stream()
                .collect(Collectors.groupingBy(
                        Student::getDepartmentName,
                        Collectors.counting()
                ));

        System.out.println(out6);
        System.out.println("================================");

        // 7. Find the list of students whose age is less than 30
        list.stream()
                .filter(student -> student.getAge() < 30)
                .forEach(System.out::println);

        System.out.println("================================");

        // 8. Find the list of students whose rank is in between 50 and 100
        list.stream()
                .filter(student -> student.getRank() > 50 && student.getRank() < 100)
                .forEach(System.out::println);

        System.out.println("================================");

        // 9. Find the average age of male and female students
        Map<String, Double> out9 = list.stream()
                .collect(Collectors.groupingBy(
                        Student::getGender,
                        Collectors.averagingInt(Student::getAge)
                ));

        System.out.println(out9);
        System.out.println("================================");

        // 10. Find the department who is having maximum number of students
        Map.Entry<String, Long> out10 = list.stream()
                .collect(Collectors.groupingBy(
                        Student::getDepartmentName,
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();

        System.out.println(out10);

        // 11. Find the students who stays in Delhi and sort them by their names
        list.stream()
                .filter(student ->
                        student.getCity().equalsIgnoreCase("Delhi"))
                .sorted(Comparator.comparing(Student::getFirstName))
                .forEach(System.out::println);

        System.out.println("================================");

        // 12. Find the average rank in all departments
        Map<String, Double> out12 = list.stream()
                .collect(Collectors.groupingBy(
                        Student::getDepartmentName,
                        Collectors.averagingInt(Student::getRank)
                        // Collectors.averagingDouble(Student::getRank) also works fine here.
                        // Ideally for int fields we should use averagingInt() and for double fields we should use averagingDouble()
                ));

        System.out.println(out12);
        System.out.println("================================");

        // 13. Find the highest rank in each department
        Map<String, Optional<Student>> out13 = list.stream()
                .collect(Collectors.groupingBy(
                        Student::getDepartmentName,
                        Collectors.maxBy(
                                Comparator.comparing(Student::getRank)
                        )
                ));

        System.out.println(out13);
        System.out.println("================================");

        // 14. Find the list of students and sort them by their rank
        list.stream()
                .sorted(Comparator.comparing(Student::getRank))
                .forEach(System.out::println);

        System.out.println("================================");

        // 15. Find the student who has second rank
        Optional<Student> out15 = list.stream()
                .sorted(Comparator.comparing(Student::getRank))
                .skip(1)
                .findFirst();

        System.out.println(out15.get());
        System.out.println("================================");
    }
}
