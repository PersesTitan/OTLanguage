package cos.ocr;

import bin.apply.item.CodeItem;
import bin.apply.item.Item;
import bin.apply.mode.TypeMode;
import bin.exception.FileException;
import bin.token.SepToken;
import bin.variable.custom.CustomSet;
import lombok.Getter;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.leptonica.global.leptonica;
import org.bytedeco.tesseract.TessBaseAPI;

import java.io.File;

@Getter
class OCRItem extends TessBaseAPI implements Item {
    private final static String MODULE_PATH = CodeItem.getModulePath("ocr");
    private final static String EXT = ".traineddata";

    private final CustomSet<String> languages = new CustomSet<>(TypeMode.STRING);
    private boolean isInit = false;

    public void init(String path, String language) {
        if (Init(path, language) == -1) throw OCRException.DO_NOT_FIND_LANGUAGE.getThrow(language);
        else this.isInit = true;
    }

    public void init(String language) {
        this.init(MODULE_PATH, language);
    }

    public void init() {
        this.init(String.join("+", languages));
    }

    // add language
    public void addLanguage(String language) {
        File file = new File(MODULE_PATH + SepToken.FILE + language + EXT);
        if (file.exists() && file.isFile()) this.languages.add(language);
        else throw FileException.DO_NOT_FIND.getThrow(language + " language file");
    }

    // remove language
    public void removeLanguage(String language) {
        this.languages.remove(language);
    }

    public String read(String path) {
        if (languages.isEmpty()) throw OCRException.NOT_HAVE_LANGUAGE.getThrow(null);
        if (!isInit) throw OCRException.NOT_INTI.getThrow(null);
        PIX image = leptonica.pixRead(path);
        SetImage(image);
        BytePointer outText = super.GetUTF8Text();
        try {
            return outText.getString();
        } finally {
            super.End();
            outText.deallocate();
            leptonica.pixDestroy(image);
        }
    }

    @Override
    public String toString() {
        return itemToString(OCRToken.OCR);
    }
}
