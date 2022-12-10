package etc.opencv;

import org.opencv.core.Mat;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;

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

    }
}
