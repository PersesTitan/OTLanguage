package bin.orign.variable.list.get;

import bin.exception.VariableException;
import bin.token.LoopToken;
import work.StartWork;

import java.util.Map;
import java.util.regex.Pattern;

public class ListDelete implements StartWork, LoopToken {
    private final String patternText = startEndMerge(VARIABLE_ACCESS, LIST_DELETE, "\\d");
    private final Pattern pattern = Pattern.compile(patternText);

    @Override
    public boolean check(String line) {
        return pattern.matcher(line).find();
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {
        int count = accessCount(line);
        if (repositoryArray.length < count) throw VariableException.localNoVariable();
        var repository = repositoryArray[count];

    }
}
