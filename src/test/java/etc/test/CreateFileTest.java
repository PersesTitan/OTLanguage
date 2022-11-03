package etc.test;

import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CreateFileTest {
    public static void main(String[] args) throws Exception {
//        String encodstring = encodeFileToBase64Binary(new File("image/icon.png"));
        String encodstring = encodeFileToBase64Binary(new File("image/icon.png"));
        System.out.println(encodstring);
        new File("image/icon.png");

        Process process = Runtime.getRuntime().exec("");
//        Image image = ImageIO.read();

//        System.out.println(image.toString());
//        Taskbar.getTaskbar().setIconImage(image);
    }
    private static String encodeFileToBase64Binary(File file) throws Exception{
        FileInputStream fileInputStreamReader = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        fileInputStreamReader.read(bytes);
        return new String(Base64.encodeBase64(bytes), "UTF-8");
    }
}
