package etc.db.replace;

import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public class DBCheck extends ReturnWorkV3 {
    // 1
    public DBCheck(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        try {
            Class.forName(params[0]);
            return TRUE;
        } catch (ClassNotFoundException e) {
            return FALSE;
        }
    }
}
