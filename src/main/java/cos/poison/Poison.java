package cos.poison;

import bin.apply.Repository;
import bin.token.LoopToken;
import cos.poison.controller.HttpServerManager;
import cos.poison.root.VariableHTML;
import cos.poison.run.replace.GetCookie;
import cos.poison.run.replace.GetUrlParam;
import cos.poison.run.replace.IsEmptyCookie;
import cos.poison.run.start.DeleteCookie;
import cos.poison.run.start.Redirect;
import cos.poison.run.start.SetCookie;
import work.v3.StartWorkV3;

import java.util.LinkedList;
import java.util.Map;

public class Poison extends StartWorkV3 implements LoopToken, PoisonRepository {

    public Poison(int... counts) {
        super(counts);
    }

    @Override
    public void start(String line, String[] params,
                      LinkedList<Map<String, Map<String, Object>>> repositoryArray) {

    }
}
