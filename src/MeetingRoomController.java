import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
import java.io.File;
import java.io.IOException;


public class MeetingRoomController{
    @FXML
    private Button exitButton;

    @FXML
    private ImageView camView;

    private DaemonThread myThread = null;
    int count = 0;
    VideoCapture webSource = null;
    Mat frame = new Mat();
    MatOfByte mem = new MatOfByte(); /// start button


    @FXML
    private void buttonClicked(MouseEvent event) throws IOException {
        if(event.getSource().equals(exitButton)) {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        }
    }


    public MeetingRoomController() {


        // Check if video capturing is enabled
        webSource = new VideoCapture(0);
        myThread = new DaemonThread();
        Thread t = new Thread(myThread);
        t.setDaemon(true);
        myThread.runnable = true;
        t.start();



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
