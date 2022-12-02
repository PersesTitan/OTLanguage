package bin.apply.sys.run;

import bin.CreateReturnWorks;
import work.v3.ReturnWorkV3;

import java.util.LinkedList;
import java.util.Map;

import static bin.token.cal.BoolToken.FALSE;
import static bin.token.cal.BoolToken.TRUE;

// 존재하는 값인지 확인하는 로직
public class IsCheck extends ReturnWorkV3 {
    // 1    // :테스트_
    public IsCheck(int... counts) {
        super(counts);
    }

    @Override
    public String start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        String value = CreateReturnWorks.sub(params[0], null, repositoryArray);
        return value == null ? FALSE : TRUE;
    }
}
