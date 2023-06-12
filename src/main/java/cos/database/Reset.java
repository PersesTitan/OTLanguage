package cos.database;

import bin.apply.Repository;
import work.ResetWork;

public class Reset implements ResetWork, DatabaseToken, Repository {
    @Override
    public void reset() {
        create(DATABASE, DatabaseItem.class, v -> new DatabaseItem((String) v[0]), s);
        create(DATABASE, DatabaseItem.class, v -> new DatabaseItem((String) v[0], (String) v[1], (String) v[2]), s, s, s);

        methodWorks.add(DATABASE, SQL, s, DatabaseItem::sql);
        loopWorks.put(DATABASE, SELECT, new Select());
    }
}
