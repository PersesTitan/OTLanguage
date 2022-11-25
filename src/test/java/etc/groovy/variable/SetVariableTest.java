package etc.groovy.variable;

import bin.token.MergeToken;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;
import java.util.StringTokenizer;

import static bin.token.VariableToken.TOTAL_LIST;

public class SetVariableTest extends StartWorkV3 implements MergeToken {
    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        Binding binding = new Binding();
        TOTAL_LIST.forEach(type -> repositoryArray.get(0).get(type).forEach(binding::setVariable));
        GroovyShell sh = new GroovyShell(binding);

        StringTokenizer tokenizer = new StringTokenizer(bothEndCut(params[params.length-1].strip()), ",");
        String total = getLoopTotal(tokenizer.nextToken(), tokenizer.nextToken(), tokenizer.nextToken());

        StringBuilder builder = new StringBuilder();
        total.lines()
                .map(v -> v.substring(v.indexOf(' ') + 1))
                .forEach(builder::append);
        sh.evaluate(builder.toString());
    }
}
