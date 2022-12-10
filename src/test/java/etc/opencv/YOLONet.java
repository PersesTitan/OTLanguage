package etc.opencv;

import jdk.incubator.vector.FloatVector;
import jdk.incubator.vector.IntVector;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Net;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.opencv.core.CvType.CV_32F;
import static org.opencv.dnn.Dnn.blobFromImage;
import static org.opencv.dnn.Dnn.readNetFromDarknet;
import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.LINE_8;
import static org.opencv.imgproc.Imgproc.rectangle;

public class YOLONet {
    public static void main(String[] args) {
        Mat image = imread("dog.jpg");

        YOLONet yolo = new YOLONet(
                "yolov4.cfg",
                "yolov4.weights",
                "coco.names",
                608, 608);
        yolo.setup();

        List<ObjectDetectionResult> results = yolo.predict(image);

        System.out.printf("Detected %d objects:\n", results.size());
        for(ObjectDetectionResult result : results) {
            System.out.printf("\t%s - %.2f%%\n", result.className, result.confidence * 100f);

            // annotate on image
            rectangle(image,
                    new Point(result.x, result.y),
                    new Point(result.x + result.width, result.y + result.height),
                    Scalar.MAGENTA, 2, LINE_8, 0);
        }

        imshow("YOLO", image);
        waitKey();
    }

    private Path configPath;
    private Path weightsPath;
    private Path namesPath;
    private int width;
    private int height;

    private float confidenceThreshold = 0.5f;
    private float nmsThreshold = 0.4f;

    private Net net;
    private StringVector outNames;

    private List<String> names;

    public YOLONet(String configPath, String weightsPath, String namesPath, int width, int height) {
        this.configPath = Paths.get(configPath);
        this.weightsPath = Paths.get(weightsPath);
        this.namesPath = Paths.get(namesPath);
        this.width = width;
        this.height = height;
    }

    /**
     * Initialises the network.
     *
     * @return True if the network initialisation was successful.
     */
    public boolean setup() {
        net = readNetFromDarknet(
                configPath.toAbsolutePath().toString(),
                weightsPath.toAbsolutePath().toString());

        // setup output layers
        outNames = net.getUnconnectedOutLayersNames();

        // enable cuda backend if available
        if (getCudaEnabledDeviceCount() > 0) {
            net.setPreferableBackend(opencv_dnn.DNN_BACKEND_CUDA);
            net.setPreferableTarget(opencv_dnn.DNN_TARGET_CUDA);
        }

        // read names file
        try {
            names = Files.readAllLines(namesPath);
        } catch (IOException e) {
            System.err.println("Could not read names file!");
            e.printStackTrace();
        }

        return !net.empty();
    }

    /**
     * Runs the object detection on the frame.
     *
     * @param frame Input frame.
     * @return List of objects that have been detected.
     */
    public List<ObjectDetectionResult> predict(Mat frame) {
        Mat inputBlob = blobFromImage(frame,
                1 / 255.0,
                new Size(width, height),
                new Scalar(0.0),
                true, false, CV_32F);

        net.setInput(inputBlob);

        // run detection
        MatVector outs = new MatVector(outNames.size());
        net.forward(outs, outNames);

        // evaluate result
        List<ObjectDetectionResult> result = postprocess(frame, outs);

        // cleanup
        outs.releaseReference();
        inputBlob.release();

        return result;
    }

    /**
     * Remove the bounding boxes with low confidence using non-maxima suppression
     *
     * @param frame Input frame
     * @param outs  Network outputs
     * @return List of objects
     */
    private List<ObjectDetectionResult> postprocess(Mat frame, MatVector outs) {
        final IntVector classIds = new IntVector();
        final FloatVector confidences = new FloatVector();
        final RectVector boxes = new RectVector();

        for (int i = 0; i < outs.size(); ++i) {
            // extract the bounding boxes that have a high enough score
            // and assign their highest confidence class prediction.
            Mat result = outs.get(i);
            FloatIndexer data = result.createIndexer();

            for (int j = 0; j < result.rows(); j++) {
                // minMaxLoc implemented in java because it is 1D
                int maxIndex = -1;
                float maxScore = Float.MIN_VALUE;
                for (int k = 5; k < result.cols(); k++) {
                    float score = data.get(j, k);
                    if (score > maxScore) {
                        maxScore = score;
                        maxIndex = k - 5;
                    }
                }

                if (maxScore > confidenceThreshold) {
                    int centerX = (int) (data.get(j, 0) * frame.cols());
                    int centerY = (int) (data.get(j, 1) * frame.rows());
                    int width = (int) (data.get(j, 2) * frame.cols());
                    int height = (int) (data.get(j, 3) * frame.rows());
                    int left = centerX - width / 2;
                    int top = centerY - height / 2;

                    classIds.push_back(maxIndex);
                    confidences.push_back(maxScore);

                    boxes.push_back(new Rect(left, top, width, height));
                }
            }

            data.release();
            result.release();
        }

        // remove overlapping bounding boxes with NMS
        IntPointer indices = new IntPointer(confidences.size());
        FloatPointer confidencesPointer = new FloatPointer(confidences.size());
        confidencesPointer.put(confidences.get());

        NMSBoxes(boxes, confidencesPointer, confidenceThreshold, nmsThreshold, indices, 1.f, 0);

        // create result list
        List<ObjectDetectionResult> detections = new ArrayList<>();
        for (int i = 0; i < indices.limit(); ++i) {
            final int idx = indices.get(i);
            final Rect box = boxes.get(idx);

            final int clsId = classIds.get(idx);

            detections.add(new ObjectDetectionResult() {{
                classId = clsId;
                className = names.get(clsId);
                confidence = confidences.get(idx);
                x = box.x();
                y = box.y();
                width = box.width();
                height = box.height();
            }});

            box.releaseReference();
        }

        // cleanup
        indices.releaseReference();
        confidencesPointer.releaseReference();
        classIds.releaseReference();
        confidences.releaseReference();
        boxes.releaseReference();

        return detections;
    }

    /**
     * Dataclass for object detection result.
     */
    public static class ObjectDetectionResult {
        public int classId;
        public String className;

        public float confidence;

        public int x;
        public int y;
        public int width;
        public int height;
    }
}