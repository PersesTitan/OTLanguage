package cos.poison;

import work.StartWork;

import java.util.Map;

public class Poison implements StartWork {

    @Override
    public boolean check(String line) {
        return false;
    }

    @Override
    public void start(String line, String origen,
                      Map<String, Map<String, Object>>[] repositoryArray) {

    }
}
