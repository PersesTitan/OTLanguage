package etc.database.start;

import work.v3.ReturnWorkV3;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

public class DatabaseCheckTest extends ReturnWorkV3 {
    // 1
    public DatabaseCheckTest(int... counts) {
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
