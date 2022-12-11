package etc.opencv;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class OpenCV {
    static {
        File file = new File("module/opencv/libopencv_java460.dylib");
        System.load(file.getAbsolutePath());
    }
    public static void main(String[] args) {
        new OpenCV();
    }

    private OpenCV() {
        String cfg = new File("module/opencv/yolov3.cfg").getAbsolutePath();
        String weights = new File("module/opencv/yolov3.weights").getAbsolutePath();
        String car = new File("module/opencv/car.png").getAbsolutePath();

        Net net = Dnn.readNetFromDarknet(cfg, weights);
        Mat image = Imgcodecs.imread(car, Imgcodecs.IMREAD_COLOR);
        net.setInput(image);

        JFrame frame = new JFrame();
        JLabel label = new JLabel();
        frame.setContentPane(label);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setBounds(0, 0, 1000, 1000);

        try {
            label.setIcon(getImage(net.forward()));
            label.repaint();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ImageIcon getImage(Mat mat) throws IOException {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, matOfByte);
        byte[] bytes = matOfByte.toArray();
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
        return new ImageIcon(image);
    }
}
