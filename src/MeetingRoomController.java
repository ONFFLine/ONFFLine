import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;


public class MeetingRoomController{
    @FXML
    private Button exitButton;

    @FXML
    private ImageView camView;

    private MeetingRoomData roomData;

    @FXML
    private ListView<String> partList;

    private DaemonThread myThread = null;
    int count = 0;
    VideoCapture webSource = null;
    Mat frame = new Mat();
    MatOfByte mem = new MatOfByte(); /// start button

    class participantsUpdater extends Thread {

        @Override
        public void run(){

            DBHandler dbHandler = new DBHandler();
            while(true){
                String list = dbHandler.getParticipantsList(roomData.getRoomId());
                Gson gson = new Gson();
                roomData.setPartipantsList(gson.fromJson(list, String[].class));

                ObservableList<String> parts = FXCollections.observableArrayList(roomData.getPartipantsList());
                System.out.println(list);
                partList.setItems(parts);
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
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
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
        participantsUpdater updater = new participantsUpdater();
        Thread tt = new Thread(updater);
        tt.start();



    }

    class DaemonThread implements Runnable
    {
        protected volatile boolean runnable = false;

        @Override
        public  void run()
        {
            synchronized(this)
            {
                while(runnable)
                {
                    if(webSource.grab())
                    {
                        try
                        {
                            webSource.retrieve(frame);
                            Imgcodecs.imencode(".bmp", frame, mem);
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.toArray()));

                            BufferedImage buff = (BufferedImage) im;
                            camView.setImage(SwingFXUtils.toFXImage(buff, null));

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
