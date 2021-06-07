import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Objects;

public class MainController {

    @FXML
    private Button createRoomButton;
    @FXML
    private Button goCustomizeButton;
    @FXML
    private Button joinTeamButton;
    @FXML
    private Button saveUser;
    @FXML
    private TextField useridField;
    @FXML
    private TextField nickField;

    @FXML
    private void buttonClicked(MouseEvent event) throws IOException {
        if(event.getSource().equals(createRoomButton)) {

            UserData userData = new UserData();
            DBHandler dbHandler = new DBHandler();
            MeetingRoomData roomData = new MeetingRoomData();
            int roomId = dbHandler.createRoom(userData);
            roomData.setRoomId(roomId);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MeetingRoomView.fxml"));
            Parent root = (Parent) loader.load();
            PageController pageController = new PageController();
            pageController.setTitle("Meeting Room");
            pageController.changePage(new Scene(root));
        }
        if(event.getSource().equals(goCustomizeButton)) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomizationView.fxml"));
            Parent root = (Parent) loader.load();
            PageController pageController = new PageController();
            pageController.setTitle("Customizing");
            pageController.changePage(new Scene(root));
        }
        if(event.getSource().equals(joinTeamButton)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("JoinRoomView.fxml"));
            Parent root = (Parent) loader.load();
            PageController pageController = new PageController();
            pageController.setTitle("Join to Meeting room");
            pageController.changePage(new Scene(root));
        }
        if(event.getSource().equals(saveUser)) {
            UserData userData = new UserData();
            String userId = useridField.getText();
            String userNick = nickField.getText();
            userData.setUserId(userId);
            userData.setNickName(userNick);
        }

    }

}
