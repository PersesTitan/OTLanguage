package cos.poison.run;

import bin.token.LoopToken;
import com.sun.net.httpserver.Headers;
import cos.poison.root.RootWork;
import cos.poison.work.PoisonReturnWork;

import java.util.Map;

public class GetCookie implements RootWork, LoopToken, PoisonReturnWork {

    @Override
    public boolean check(String line) {
        return false;
    }

    @Override
    public String start(String line, String origen, Headers requestHeader,
                        Map<String, Map<String, Object>>[] repositoryArray) {
        return null;
    }
}
