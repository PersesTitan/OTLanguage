package cos.poison;

import cos.poison.run.replace.GetCookie;
import cos.poison.run.replace.GetUrlParam;
import cos.poison.run.start.DeleteCookie;
import cos.poison.run.start.Redirect;
import cos.poison.run.start.SetCookie;
import cos.poison.work.PoisonStartWork;
import work.v3.ReturnWorkV3;
import work.v3.StartWorkV3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static bin.token.LoopToken.*;

public interface PoisonRepository {
    Map<String, Map<String, StartWorkV3>> poisonStartWorks = new HashMap<>();
    Map<String, Map<String, ReturnWorkV3>> poisonReturnWorks = new HashMap<>();
    List<PoisonStartWork> poisonStartList = new ArrayList<>();

    //    List<PoisonReturnWork> poisonReturnWorks = new ArrayList<>() {{
//        add(new GetCookie(POISON, GET_COOKIE));
//        add(new GetUrlParam(POISON, GET_URL));
//    }};
//
//    List<PoisonStartWork> poisonStartWorks = new ArrayList<>() {{
//        add(new SetCookie(POISON, SET_COOKIE));
//        add(new Redirect(POISON, REDIRECT));
//        add(new DeleteCookie(POISON, COOKIE, DELETE_COOKIE));
//    }};
}
