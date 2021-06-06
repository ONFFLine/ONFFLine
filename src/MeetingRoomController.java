import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;


public class MeetingRoomController{
    @FXML
    private Button exitButton;

    @FXML
    private ImageView camView;

    private MeetingRoomData roomData;

    @FXML
    private ListView<String> partList;

    participantsUpdater updater = null;
    private DaemonThread myThread = null;
    int count = 0;
    VideoCapture webSource = null;
    Mat frame = new Mat();
    MatOfByte mem = new MatOfByte(); /// start button

    class participantsUpdater extends Thread {

        @Override
        public void run(){

            DBHandler dbHandler = new DBHandler();
            ObservableList<String> parts;
            while(!Thread.currentThread().isInterrupted()){
                String list = dbHandler.getParticipantsList(roomData.getRoomId());
                Gson gson = new Gson();
                roomData.setPartipantsList(gson.fromJson(list, String[].class));

                parts= FXCollections.observableArrayList(roomData.getPartipantsList());
                System.out.println(list);
                if(!Thread.currentThread().isInterrupted()) {
                    partList.setItems(parts);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                }
            }

        }
    }

    @FXML
    private void buttonClicked(MouseEvent event) throws IOException {
        if(event.getSource().equals(exitButton)) {
            DBHandler dbHandler = new DBHandler();
            UserData userData = new UserData();
            dbHandler.leaveMeetingRoom(roomData.getRoomId(),userData.getUserId());
            updater.interrupt();
            updater.stop();
            PageController pageController = new PageController();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainView.fxml")));
            pageController.setTitle("ONFFLINE");
            pageController.changePage(new Scene(root,500,382.0));
        }
    }

    public MeetingRoomController() {


        // Check if video capturing is enabled


        roomData = new MeetingRoomData();
        webSource = new VideoCapture(0);
        myThread = new DaemonThread();
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        t.start();
        updater = new participantsUpdater();
        updater.start();

        String path = System.getProperty("user.dir");
        System.out.println("Working Directory = " + path);



    }

    class DaemonThread implements Runnable
    {
        protected volatile boolean runnable = false;

        @Override
        public void run()
        {
            synchronized(this)
            {
                while(runnable)
                {
                    if(webSource.grab())
                    {
                        try
                        {
                            String filenameFaceCascade = "haarData/haarcascade_frontalface_alt.xml";
                            String filenameEyesCascade = "haarData/haarcascade_eye_tree_eyeglasses.xml";
                            int cameraDevice = 0;
                            CascadeClassifier faceCascade = new CascadeClassifier();
                            CascadeClassifier eyesCascade = new CascadeClassifier();
                            if (!faceCascade.load(filenameFaceCascade)) {
                                System.err.println("--(!)Error loading face cascade: " + filenameFaceCascade);
                                System.exit(0);
                            }
                            if (!eyesCascade.load(filenameEyesCascade)) {
                                System.err.println("--(!)Error loading eyes cascade: " + filenameEyesCascade);
                                System.exit(0);
                            }
                            while (webSource.read(frame)) {
                                if (frame.empty()) {
                                    System.err.println("--(!) No captured frame -- Break!");
                                    break;
                                }
                                //-- 3. Apply the classifier to the frame

                                Mat frameGray = new Mat();
                                Imgproc.cvtColor(frame, frameGray, Imgproc.COLOR_BGR2GRAY);
                                Imgproc.equalizeHist(frameGray, frameGray);
                                // -- Detect faces
                                MatOfRect faces = new MatOfRect();
                                Mat frameimg = new Mat();
                                frameimg = Imgcodecs.imread("assets/diffuse_fox_v2.jpg");

                                faceCascade.detectMultiScale(frameGray, faces);
                                java.util.List<Rect> listOfFaces = faces.toList();
                                for (Rect face : listOfFaces) {
                                    org.opencv.core.Point center = new org.opencv.core.Point(face.x + face.width / 2, face.y + face.height / 2);
                                    Imgproc.ellipse(frame, center, new Size(face.width / 2, face.height / 2), 0, 0, 360,
                                            new Scalar(255, 0, 255));
                                    Mat faceROI = frameGray.submat(face);
                                    // -- In each face, detect eyes
                                    MatOfRect eyes = new MatOfRect();
                                    eyesCascade.detectMultiScale(faceROI, eyes);
                                    List<Rect> listOfEyes = eyes.toList();
                                    for (Rect eye : listOfEyes) {
                                        org.opencv.core.Point eyeCenter = new Point(face.x + eye.x + eye.width / 2, face.y + eye.y + eye.height / 2);
                                        int radius = (int) Math.round((eye.width + eye.height) * 0.25);



                                        Imgproc.circle(frame, eyeCenter, radius, new Scalar(255, 0, 0), 4);
                                    }
                                }

                                Imgcodecs.imencode(".bmp", frame, mem);
                                Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));
                                BufferedImage buff = (BufferedImage) im;
                                camView.setImage(SwingFXUtils.toFXImage(buff, null));
                            }


                            if(runnable == false)
                            {
                                this.wait();
                            }
                        }
                        catch(Exception ex)
                        {
                        }
                    }
                }
            }
        }
    }
}
