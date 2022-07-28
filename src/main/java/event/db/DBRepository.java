package event.db;

import http.controller.db.define.DataBaseWork;
import http.controller.db.define.SelectWork;

import java.util.ArrayList;
import java.util.List;

public interface DBRepository {
    List<DataBaseWork> dbWorks = new ArrayList<>();
    List<SelectWork> selectWorks = new ArrayList<>();

}
