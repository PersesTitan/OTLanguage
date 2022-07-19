package etc.reader;

import event.Setting;
import origin.exception.FileFailException;
import origin.exception.FileFailMessage;

import java.io.File;

public class RunOTL extends Setting {

    public void startOTL(String path) {
        if (!new File(path).canRead()) throw new FileFailException(FileFailMessage.doNotReadFile);

    }
}
