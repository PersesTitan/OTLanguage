package http;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class CreateImageToHTML {
    public static void main(String[] args) throws Exception {
        File file = new File("image/favicon.ico");
        String contentType = Files.probeContentType(file.toPath());
        byte[] data = Files.readAllBytes(file.toPath());
        String base64str = Base64.getEncoder().encodeToString(data);

        // cretate "data URI"
        StringBuilder sb = new StringBuilder();
        sb.append("data:");
        sb.append(contentType);
        sb.append(";base64,");
        sb.append(base64str);
        System.out.println(sb.toString());

//        String text = print("image/poison.png", true);
//        StringBuilder builder = new StringBuilder();
//        for (int i=0; i<text.length(); i++) {
//            if (i%200 == 0) builder.append("\n");
//            builder.append(text.charAt(i));
//        }
//
//        builder.toString().lines().forEach(v -> {
//            System.out.printf("builder.append(\"%s\");\n", v);
//        });

//        text = print("image/poison.png", true);
//        builder = new StringBuilder();
//        for (int i=0; i<text.length(); i++) {
//            if (i%200 == 0) builder.append("\n");
//            builder.append(text.charAt(i));
//        }
//        System.out.println(builder);
//
//        System.out.println(print("image/icon.png", true));
    }

    private static String print(String path, boolean icon) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(path));
        byte[] imageBytes = new byte[0];
        for(byte[] ba = new byte[bis.available()];
            bis.read(ba) != -1;) {
            byte[] baTmp = new byte[imageBytes.length + ba.length];
            System.arraycopy(imageBytes, 0, baTmp, 0, imageBytes.length);
            System.arraycopy(ba, 0, baTmp, imageBytes.length, ba.length);
            imageBytes = baTmp;
        }
        return icon
                ? "<img src='data:image/png;base64," + DatatypeConverter.printBase64Binary(imageBytes) + "'>"
                : "<link rel='icon' type='image/x-icon' href='data:image/x-icon;base64," + DatatypeConverter.printBase64Binary(imageBytes) + "'>";
    }
}
