import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Method;

public class MainController {

    @FXML
    private Button createRoomButton;
    @FXML
    private Button goCustomizeButton;
    @FXML
    private Button joinTeamButton;
    @FXML
    private Button acceptButton;
    @FXML
    private Button cancelButton;

    @FXML
    private void buttonClicked(MouseEvent event) throws IOException {
        if(event.getSource().equals(createRoomButton)) {

            UserData userData = new UserData();
            DBHandler dbHandler = new DBHandler();
            userData.setUserId("hjuhyjmj");
            userData.setNickName("polar boar");
            MeetingRoomData roomData = new MeetingRoomData();
            int roomId = dbHandler.createRoom(userData);
            roomData.setRoomId(roomId);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MeetingRoomView.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Meeting Room");
            stage.setScene(new Scene(root));
            stage.show();
        }
        if(event.getSource().equals(goCustomizeButton)) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomizationView.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Customizing");
            stage.setScene(new Scene(root));
            stage.show();
        }
        if(event.getSource().equals(joinTeamButton)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("JoinRoomView.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Join to Meeting room");
            stage.setScene(new Scene(root));
            stage.show();

        }

        if(event.getSource().equals(cancelButton)) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();

        }
    }

}
