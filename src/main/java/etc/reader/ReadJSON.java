package etc.reader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ReadJSON {
    private final static JSONParser parser = new JSONParser();

    public static String version(String file) {
        try (Reader reader = new FileReader("./etc/" + file)) {
            JSONObject object = (JSONObject) parser.parse(reader);
            return object.get("version").toString();
        } catch (Exception ignored) {}
        return "";
    }

    public static String founder(String file) {
        try (Reader reader = new FileReader("./etc/" + file)) {
            JSONObject object = (JSONObject) parser.parse(reader);
            return object.get("founder").toString();
        } catch (Exception ignored) {}
        return "";
    }

    public static String github(String file) {
        try (Reader reader = new FileReader("./etc/" + file)) {
            JSONObject object = (JSONObject) parser.parse(reader);
            return object.get("github-link").toString();
        } catch (Exception ignored) {}
        return "";
    }

    public static String kind(String file) {
        try (Reader reader = new FileReader("./etc/" + file)) {
            JSONObject object = (JSONObject) parser.parse(reader);
            return object.get("kind").toString();
        } catch (Exception ignored) {}
        return "";
    }

    public static List<String> help(String file) {
        List<String> list = new ArrayList<>();
        try (Reader reader = new FileReader("./etc/" + file)) {
            JSONObject object = (JSONObject) parser.parse(reader);
            JSONArray array = (JSONArray) object.get("help");
            for (Object o : array) list.add(o.toString());
        } catch (Exception ignored) {}
        return list;
    }
}
