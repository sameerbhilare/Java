package streams;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
Member class has memberId, memberName and List of Workout the member has done.
The Workout class contains workoutId, integer startTime, int endTime, int totalDuration.

Write a java program using streams to get a map of memberId and average workout duration of that member.
 */

class Workout {
    private String workoutId;
    private int startTime;
    private int endTime;
    private int totalDuration;  // in minutes, presumably

    // Constructor
    public Workout(String workoutId, int startTime, int endTime, int totalDuration) {
        this.workoutId = workoutId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalDuration = totalDuration;
    }

    public int getTotalDuration() {
        return totalDuration;
    }
}

class Member {
    private String memberId;
    private String memberName;
    private List<Workout> workouts;

    // Constructor
    public Member(String memberId, String memberName, List<Workout> workouts) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.workouts = workouts != null ? workouts : new ArrayList<>();
    }

    public String getMemberId() {
        return memberId;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }
}

public class StreamTest5 {

    private static List<Member> createSampleData() {
        List<Workout> w1 = Arrays.asList(
                new Workout("w1", 600, 700, 45),
                new Workout("w2", 800, 930, 60),
                new Workout("w3", 1000, 1100, 55)
        );

        List<Workout> w2 = Arrays.asList(
                new Workout("w4", 700, 800, 30),
                new Workout("w5", 900, 1030, 75)
        );

        List<Workout> w3 = Collections.emptyList(); // no workouts

        return Arrays.asList(
                new Member("M001", "Sameer", w1),
                new Member("M002", "Nisha", w2),
                new Member("M003", "Rahul", w3)
        );
    }

    // version 1 - preferred solution
    public static Map<String, Double> getAverageWorkoutDurationByMemberV1(List<Member> members) {

        return members.stream()
                .collect(Collectors.toMap(
                        Member::getMemberId,
                        member -> member.getWorkouts().stream()
                                .mapToInt(Workout::getTotalDuration)
                                .average()
                                .orElse(0.0)
                ));
    }

    // version 2 - avoid this due to wrong use of averagingDouble
    public static Map<String, Double> getAverageWorkoutDurationByMemberV2(List<Member> members) {

        return members.stream()
                .collect(Collectors.groupingBy(
                        Member::getMemberId,
                        Collectors.averagingDouble(member ->
                                member.getWorkouts().stream()
                                        .mapToInt(Workout::getTotalDuration)
                                        .average()
                                        .orElse(0.0)
                )));
    }

    // vesion 3
    public static Map<String, Double> getAverageWorkoutDurationByMemberV3(List<Member> members) {

        return members.stream()
                // Flatten: member → workouts
                .flatMap(member -> member.getWorkouts().stream()
                        // Pair each workout with its member's ID
                        .map(workout -> new AbstractMap.SimpleEntry<>(
                                member.getMemberId(),
                                workout.getTotalDuration()
                        )))
                // Group by memberId and compute average
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.averagingInt(Map.Entry::getValue)
                ));
    }

    static void main() {
        List<Member> members = createSampleData();

        // Way 1
        Map<String, Double> averages = getAverageWorkoutDurationByMemberV1(members);
        averages.forEach((memberId, avg) ->
                System.out.printf("Member %s → average duration: %.2f minutes%n", memberId, avg));

        System.out.println("---------------------------------");

        // Way 2 - incorrect use of averagingDouble
        averages = getAverageWorkoutDurationByMemberV2(members);
        averages.forEach((memberId, avg) ->
                System.out.printf("Member %s → average duration: %.2f minutes%n", memberId, avg));

        System.out.println("---------------------------------");

        // Way 3
        averages = getAverageWorkoutDurationByMemberV3(members);
        averages.forEach((memberId, avg) ->
                System.out.printf("Member %s → average duration: %.2f minutes%n", memberId, avg));

        System.out.println("---------------------------------");

    }

}
