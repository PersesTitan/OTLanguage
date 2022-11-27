package cos.shell;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import work.v3.StartWorkV3;

import java.util.*;
import java.util.stream.Collectors;

import static bin.token.VariableToken.*;
import static bin.token.cal.BoolToken.TRUE;

public class CreateShell extends StartWorkV3 {
    public static GroovyShell sh;

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        Binding binding = new Binding();
        CreateShell.sh = new GroovyShell(binding);
        TOTAL_LIST.forEach(type -> repositoryArray.get(0).get(type).forEach((k, v) -> {
            switch (type) {
                case BOOL_VARIABLE -> sh.setVariable(k, v.equals(TRUE));
                case SET_BOOLEAN -> sh.setVariable(k, setSet((Collection<String>) v));
                case LIST_BOOLEAN -> sh.setVariable(k, setList((Collection<String>) v));
                case MAP_BOOLEAN -> sh.setVariable(k, setMap((LinkedHashMap<String, String>) v));
                default -> sh.setVariable(k, v);
            }
        }));
    }

    private LinkedList<Boolean> setList(Collection<String> collections) {
        return collections
                .stream()
                .map(v -> v.equals(TRUE))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    private LinkedHashSet<Boolean> setSet(Collection<String> collections) {
        return collections
                .stream()
                .map(v -> v.equals(TRUE))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private LinkedHashMap<String, Boolean> setMap(LinkedHashMap<String, String> map) {
        LinkedHashMap<String, Boolean> hashMap = new LinkedHashMap<>();
        map.forEach((k, v) -> hashMap.put(k, v.equals(TRUE)));
        return hashMap;
    }
}
