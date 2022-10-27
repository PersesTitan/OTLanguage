package cos.poison.v3;

import bin.exception.VariableException;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.check.VariableCheck.isInteger;
import static cos.poison.Poison.httpServerManager;

public class PoisonCreate extends StartWorkV3 {
    public PoisonCreate(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        int count = params.length;
        if (count == 1 && params[0].isEmpty()) httpServerManager.createServer();
        else if (count == 1) {
            if (isInteger(params[0])) httpServerManager.createServer(Integer.parseInt(params[0]));
            else httpServerManager.createServer(params[0]);
        } else {
            if (isInteger(params[1])) httpServerManager.createServer(params[0], Integer.parseInt(params[1]));
            else throw VariableException.typeMatch();
        }
    }
}
