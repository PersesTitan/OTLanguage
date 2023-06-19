package cos.file;

import bin.apply.Repository;
import bin.token.KlassToken;
import work.ResetWork;

public class Reset implements ResetWork, FileToken, Repository {
    @Override
    public String version() {
        return "1.0.0";
    }

    @Override
    public void reset() {
        create(FILE, FileItem.class, v -> new FileItem((String) v[0]), s);

        methodWorks.add(FILE, IS_FILE, FileItem::isFile, b);
        methodWorks.add(FILE, IS_DIR, FileItem::isDir, b);
        methodWorks.add(FILE, EXISTS, FileItem::exists, b);

        methodWorks.add(FILE, GET_NAME, FileItem::getName, s);
        methodWorks.add(FILE, GET_PATH, FileItem::getPath, s);

        methodWorks.add(FILE, FILE_LIST, FileItem::list, KlassToken.LIST);
        methodWorks.add(FILE, FILE_READ, FileItem::read, ls);

        methodWorks.add(FILE, WRITHE, ls, FileItem::write);
    }
}
