import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MeetingRoomController{
    @FXML
    private Button exitButton;

    @FXML
    private ImageView camView;


    @FXML
    private void buttonClicked(MouseEvent event) throws IOException {
        if(event.getSource().equals(exitButton)) {
            Stage stage = (Stage) exitButton.getScene().getWindow();
            stage.close();
        }
    }

    public MeetingRoomController() {

    }
}
