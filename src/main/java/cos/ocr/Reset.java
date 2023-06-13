package cos.ocr;

import bin.apply.Repository;
import work.ResetWork;

public class Reset implements ResetWork, OCRToken, Repository {
    @Override
    public void reset() {
        create(OCR, OCRItem.class, v -> new OCRItem());

        methodWorks.<OCRItem>add(OCR, INIT, OCRItem::init);
        methodWorks.<OCRItem, String>add(OCR, INIT, s, OCRItem::init);
        methodWorks.<OCRItem, String, String>add(OCR, INIT, s, s, OCRItem::init);
        methodWorks.add(OCR, ADD, s, OCRItem::addLanguage);
        methodWorks.add(OCR, GET, OCRItem::getLanguages, ss);
        methodWorks.add(OCR, REMOVE, s, OCRItem::removeLanguage);
        methodWorks.add(OCR, START, s, OCRItem::read, s);
    }
}
