package cos.poison;

import cos.poison.work.PoisonStartWork;
import work.v3.ReturnWorkV3;
import work.v3.StartWorkV3;

import java.util.*;

public interface PoisonRepository {
    Map<String, Map<String, StartWorkV3>> poisonStartWorks = new HashMap<>();
    Map<String, Map<String, ReturnWorkV3>> poisonReturnWorks = new HashMap<>();
    List<PoisonStartWork> poisonStartList = new ArrayList<>();

    Set<String> passUrl = new HashSet<>() {{
        add("/favicon.ico/");
    }};

    String POISON_PASS_URL = "<<!";
}
