package pairmatching.domain.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import pairmatching.domain.Course;
import pairmatching.domain.Crew;

public class Crews {
    private Crews() {
    }

    private static final List<Crew> crews = new ArrayList<>();

    public static void addCrew(Crew crew) {
        crews.add(crew);
    }

    public static Crew findCrewByName(String name) {
        return crews.stream()
                .filter(crew -> crew.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 크루"));
    }

    public static List<Crew> getCrewsByCourse(Course course) {
        return crews.stream().filter(crew -> crew.isCrewOf(course))
                .collect(Collectors.toList());
    }

}
