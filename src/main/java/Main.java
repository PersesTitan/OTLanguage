import java.io.IOException;

import static make.MakeLanguage.endCode;
import static make.MakeLanguage.getURL;

public class Main {
    public static void main(String[] args) throws IOException {
        String total = getURL(args, ".otl", true);

        

        endCode();
    }
}
