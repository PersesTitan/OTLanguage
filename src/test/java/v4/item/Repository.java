package v4.item;

import v4.work.ReturnWork;
import v4.work.StartWork;

import java.util.HashMap;
import java.util.Map;

public interface Repository {
    HashMap<String, Map<String, StartWork>> priorityStartWorks = new HashMap<>();
    HashMap<String, Map<String, StartWork>> startWorks = new HashMap<>();
    HashMap<String, Map<String, ReturnWork>> returnWorks = new HashMap<>();
}
