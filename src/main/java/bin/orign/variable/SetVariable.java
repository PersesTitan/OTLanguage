package bin.orign.variable;

import bin.token.VariableToken;
import org.apache.hadoop.hive.metastore.api.NoSuchTxnException;
import work.ReturnWork;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetVariable implements ReturnWork, VariableToken {
    private final Matcher matcher = Pattern.compile(VARIABLE).matcher("");

    @Override
    public boolean check(String line) {
        return (matcher.reset(line)).find();
    }

    @Override
    public String start(String line,
                        Map<String, Map<String, Object>>[] repositoryArray) {
        matcher.reset();
        while (matcher.find()) {
            String group = matcher.group();
            String value = bothEndCut(group);
            int access = accessCount(value);
            if (repositoryArray.length <= access) continue;
            var repository = repositoryArray[access];
            line = getValue(group, value.substring(access), line, repository);
        }
        return line;
    }

    private String getValue(String group, String variableName, String line,
                            Map<String, Map<String, Object>> repository) {
        for (var rep : repository.values()) {
            if (rep.containsKey(variableName))
                line = line.replaceFirst(Pattern.quote(group), rep.get(variableName).toString());
        }
        return line;
    }
}
