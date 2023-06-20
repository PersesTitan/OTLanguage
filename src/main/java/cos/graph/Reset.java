package cos.graph;

import bin.apply.Repository;
import work.ResetWork;

public class Reset implements ResetWork, Repository {
    @Override
    public String version() {
        return "1.0.0";
    }

    @Override
    public void reset() {
        checkModuleError("gui");

    }
}