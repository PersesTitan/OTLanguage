package cos.poison;

import cos.poison.run.GetCookie;
import cos.poison.run.GetUrlParam;
import cos.poison.run.Redirect;
import cos.poison.run.SetCookie;
import cos.poison.work.PoisonReturnWork;
import cos.poison.work.PoisonStartWork;

import java.util.ArrayList;
import java.util.List;

import static bin.token.LoopToken.*;

public interface PoisonRepository {
    List<PoisonReturnWork> poisonReturnWorks = new ArrayList<>() {{
        add(new GetCookie(POISON, GET_COOKIE));
        add(new GetUrlParam(POISON, GET_URL));
    }};

    List<PoisonStartWork> poisonStartWorks = new ArrayList<>() {{
        add(new SetCookie(POISON, SET_COOKIE));
        add(new Redirect(POISON, REDIRECT));
    }};
}
