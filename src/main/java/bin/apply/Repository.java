package bin.apply;

import bin.apply.sys.item.HpMap;
import bin.define.method.MethodReturn;
import bin.define.method.MethodVoid;
import bin.orign.variable.StartVariable;
import bin.orign.variable.Variable;
import work.v3.ReturnWorkV3;
import work.v3.StartWorkV3;

import java.util.*;

import static bin.token.LoopToken.*;
import static bin.token.cal.BoolToken.*;

public interface Repository {
    List<String> repositoryItems = new ArrayList<>(TOTAL_LIST) {{
        add(METHOD);
    }};
    LinkedList<Map<String, Map<String, Object>>> repository = new LinkedList<>() {{
        add(new HashMap<>() {{
            repositoryItems.forEach(v -> put(v, new HpMap(v)));
        }});
    }};

    Set<String> noUse = new HashSet<>();
    List<String> noContains = Arrays.asList(TOKEN, OR, AND, NO_TOKEN);

    static boolean containsVariable(String variable, Map<String, Map<String, Object>> repository) {
        return TOTAL_LIST.stream().anyMatch(v -> repository.get(v).containsKey(variable));
    }

    // Version 3
    Variable variable = new Variable(1);
    StartVariable startVariable = new StartVariable(2);
    MethodVoid methodVoid = new MethodVoid();
    MethodReturn methodReturn = new MethodReturn();

    HashMap<String, Map<String, StartWorkV3>> priorityStartWorksV3 = new HashMap<>();
    HashMap<String, Map<String, StartWorkV3>> startWorksV3 = new HashMap<>();
    HashMap<String, Map<String, ReturnWorkV3>> returnWorksV3 = new HashMap<>();
}
