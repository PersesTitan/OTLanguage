package etc.db.item.db;

import java.util.ArrayList;
import java.util.List;

public final class DBItem {
    private final DBItemDTO[] DTO;

    private DBItem(DBItemDTO...DTO) {
        this.DTO = DTO;
    }

    public static DBItem create(DBItemDTO...DTO) {
        return new DBItem(DTO);
    }

    private String getItemDTO(DBItemDTO item) {
        final DBOption[] options = item.option();
        String type = item.type().getQuery();
        String name = item.name();
        // ID INT
        if (options.length == 0) return name.concat(" ").concat(type);
        else {
            List<String> queryList = new ArrayList<>();
            queryList.add(name);
            queryList.add(type);
            for (DBOption option : options) {
                queryList.add(option.getQuery());
            }
            return String.join(" ", queryList);
        }
    }

    public String query(int i) {
        return getItemDTO(DTO[i]);
    }
}
