package event.db;

import http.controller.db.DatabaseSetting;
import http.controller.db.controller.DatabaseCheck;
import http.controller.db.define.DataBaseWork;
import http.controller.db.define.SelectWork;
import http.controller.db.place.DataBasePlace;
import origin.variable.model.Repository;

import static event.Controller.getVariable;

public class DBSetting implements DBRepository, Repository,
        DataBasePlace {
    public static DatabaseSetting databaseSetting = null;

    public static String start(String line) {
        if (line.isBlank()) return null;
        if (dataBaseClass.check(line)) {dataBaseClass.start(line); return null;}
        if (dataBaseClass1.check(line)) {dataBaseClass1.start(line); return null;}

        if (getVariable.check(line)) line = getVariable.start(line); //변수 불러오기
        for (SelectWork works : selectWorks) {if (works.check(line)) line = works.start(line);}
        for (DataBaseWork works : dbWorks) {if (works.check(line)) {works.start(line);return null;}}

        return line;
    }

    public void firstStart() {
        reset();
        //ㄷㅇㄷ[종류, ip, port, dbName, user, password]
        loopWorks.add(dataBaseClass); //루프
        loopWorks.add(dataBaseClass1);

        selectWorks.add(new DatabaseCheck()); //지원하는 디비인지 확인
        selectWorks.add(selectWork); //select

        dbWorks.add(sqlWork); //alter, commit, ...
    }

    public void reset() {
        dbWorks.clear();
        selectWorks.clear();
    }

    public boolean check(String line) {
        return line.contains(className);
    }
}
