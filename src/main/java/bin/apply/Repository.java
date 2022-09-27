package bin.apply;

import work.ReturnWork;
import work.StartWork;

import java.util.*;

public interface Repository {
    Map<String, Map<String, Object>> repository = new HashMap<>();
    Set<String> noUse = new HashSet<>();

    List<ReturnWork> returnWorks = new ArrayList<>();
    List<StartWork> startWorks = new ArrayList<>();
    List<StartWork> priorityWorks = new ArrayList<>();

    static Set<String> getSet(Map<String, Map<String, Object>> repository) {
        Set<String> set = new HashSet<>(noUse);
        repository.forEach((k, v) -> set.addAll(v.keySet()));
        return set;
    }
}
