package tess4j;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class Tess4j {
    static Tesseract tesseract = new Tesseract();

    public static void main(String[] args) throws TesseractException {
        String text = tesseract.doOCR(new File("module/image/banner.png"));
        System.out.println(text);
    }
}
