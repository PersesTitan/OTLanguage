package http.controller.db.place;

import event.db.DBSetting;
import http.controller.db.DatabaseSetting;
import http.controller.db.controller.pattern.DataBaseShell;
import http.controller.db.controller.pattern.DataBaseShellPattern1;
import http.controller.db.controller.pattern.DataBaseShellPattern2;
import http.controller.db.define.DataBaseWork;
import http.controller.db.define.SelectWork;
import origin.exception.MatchException;
import origin.exception.MatchMessage;
import origin.variable.model.Repository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface DataBasePlace extends Repository {
    String className = "ㄷㅇㄷ";

    static void setClass(String line, String patternText) {
        line = line.replaceFirst(patternText, "").strip();
        if (!line.isBlank()) {
            if (!uuidMap.containsKey(line)) throw new MatchException(MatchMessage.grammarError);
            //괄호 제거 작업
            var value = uuidMap.get(line).substring(1, uuidMap.get(line).length()-1).strip();
            value = value.replace("::", className + "::");
            for (String lines : value.split("\\n")) {
                if (DBSetting.start(lines) == null) return;
            }
        }
    }

    //종류, ip, port, dbName, user, password
    DataBaseShell dataBaseClass = new DataBaseShell(className, 6) {
        @Override
        public void start(String line) {
            Pattern pattern = Pattern.compile(getPattern());
            Matcher matcher = pattern.matcher(line);
            String[] values = getValues(matcher);
            String kind = values[0].trim();
            String ip = values[1].trim();
            String port = values[2].trim();
            String dbName = values[3].trim();
            String user = values[4].trim();
            String password = values[5].trim();
            DBSetting.databaseSetting = new DatabaseSetting(kind, ip, port, dbName, user, password);

            setClass(line, getPattern());
        }
    };

    //ㅇㅁㅇ[url][user][password]
    DataBaseShell dataBaseClass1 = new DataBaseShell(className, 3) {
        @Override
        public void start(String line) {
            Pattern pattern = Pattern.compile(getPattern());
            Matcher matcher = pattern.matcher(line);
            String[] values = getValues(matcher);
            String url = values[0].trim();
            String user = values[1].trim();
            String password = values[2].trim();
            DBSetting.databaseSetting = new DatabaseSetting(url, user, password);
            line = line.replaceFirst(getPattern(), "").strip();

            setClass(line, getPattern());
        }
    };

    SelectWork selectWork = new DataBaseShellPattern2(className, "ㅋㄹㅋ", 2) {
        @Override
        public String start(String line) {
            if (DBSetting.databaseSetting == null) throw new MatchException(MatchMessage.grammarError);
            Matcher matcher = getPattern().matcher(line);
            String[] values = getValues(matcher);
            String sql = values[0].trim();
            String value = values[1].trim();

            String patternText = getPatternText();
            while (matcher.find())
                line = line.replaceFirst(patternText, DBSetting.databaseSetting.getSelectValue(sql, value));
            return line;
        }
    };

    //className::메소드[][]
    //ㄷㅇㄷ::ㅋㄹㅋ[]
    DataBaseWork sqlWork = new DataBaseShellPattern1(className, "ㅋㄹㅋ", 1) {
        @Override
        public void start(String line) {
            if (DBSetting.databaseSetting == null) throw new MatchException(MatchMessage.grammarError);
            Matcher matcher = getPattern().matcher(line);
            String[] values = getValues(matcher);
            String sql = values[0].trim();
            DBSetting.databaseSetting.setting(sql);
        }
    };

}
