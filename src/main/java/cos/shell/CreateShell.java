package cos.shell;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.token.VariableToken.TOTAL_LIST;

public class CreateShell extends StartWorkV3 {
    public static GroovyShell sh;

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        Binding binding = new Binding();
        TOTAL_LIST.forEach(type -> repositoryArray.get(0).get(type).forEach(binding::setVariable));
        CreateShell.sh = new GroovyShell(binding);
    }
}
