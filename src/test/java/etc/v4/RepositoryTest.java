package etc.v4;

import bin.define.method.MethodReturn;
import bin.define.method.MethodVoid;
import etc.v4.variable.StartVariable;
import work.v4.ReturnWork;
import work.v4.StartWork;

import java.util.HashMap;
import java.util.Map;

public interface RepositoryTest {
    HashMap<String, Map<String, StartWork>> priorityStartWorks = new HashMap<>();
    HashMap<String, Map<String, StartWork>> startWorks = new HashMap<>();
    HashMap<String, Map<String, ReturnWork>> returnWorks = new HashMap<>();
    StartVariable startVariable = new StartVariable();
    MethodVoid methodVoid = new MethodVoid();
    MethodReturn methodReturn = new MethodReturn();
}
