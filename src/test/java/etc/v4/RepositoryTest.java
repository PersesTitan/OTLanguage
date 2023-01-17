package etc.v4;

import work.v4.ReturnWork;
import work.v4.StartWork;

import java.util.HashMap;
import java.util.Map;

public interface RepositoryTest {
    HashMap<String, Map<String, StartWork>> priorityStartWorks = new HashMap<>();
    HashMap<String, Map<String, StartWork>> startWorks = new HashMap<>();
    HashMap<String, Map<String, ReturnWork>> returnWorks = new HashMap<>();
}
