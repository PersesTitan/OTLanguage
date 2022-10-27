package cos.poison.v3;

import cos.http.controller.HttpMethod;
import cos.poison.Poison;
import cos.poison.method.PoisonTools;
import work.v3.StartWorkV3;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;

public class PoisonMethod extends StartWorkV3 implements PoisonTools {
    private final HttpMethod httpMethod;
    public PoisonMethod(HttpMethod httpMethod, int... counts) {
        super(counts);
        this.httpMethod = httpMethod;
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {
        // 1: url(/home/), 2: params(ㅇㅁㅇ ㅁ:ㅁ)
        int count = params.length;
        String[] total = matchSplitError(params[count-1], BLANK + RETURN_TOKEN + BLANK, 2);
        Poison.httpServerManager.addMethod(
                httpMethod,
                params[0],
                getTotal(total[0]),
                getParams(Arrays.copyOfRange(params, 1, count-1)),
                total[1]);
    }
}
