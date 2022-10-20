package cos.poison;

import cos.poison.run.GetCookie;
import cos.poison.run.Redirect;
import cos.poison.run.SetCookie;
import cos.poison.work.PoisonReturnWork;
import cos.poison.work.PoisonStartWork;

import java.util.ArrayList;
import java.util.List;

import static bin.token.LoopToken.*;

public interface PoisonRepository {
    List<PoisonReturnWork> poisonReturnWorks = new ArrayList<>() {{
        add(new GetCookie(GET_COOKIE));
    }};

    List<PoisonStartWork> poisonStartWorks = new ArrayList<>() {{
        add(new SetCookie(SET_COOKIE));
        add(new Redirect(REDIRECT));
    }};
}
