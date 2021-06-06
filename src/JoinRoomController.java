import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class JoinRoomController {
    @FXML
    private TextField roomfield;

    @FXML
    private TextField userfield;

    @FXML
    private TextField nickfield;

    @FXML
    private Button cancelButton;

    @FXML
    private Button joinButton;

    @FXML
    private void buttonClicked(MouseEvent event) throws IOException {
        if(event.getSource().equals(cancelButton)) {
            PageController pageController = new PageController();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainView.fxml")));
            pageController.setTitle("ONFFLINE");
            pageController.changePage(new Scene(root,500,382.0));
        }
        else if(event.getSource().equals(joinButton)) {
            int roomId = Integer.parseInt(roomfield.getText());
            UserData userData = new UserData();
            String userId = userData.getUserId();
            String nickName = userData.getNickName();

            MeetingRoomData roomData = new MeetingRoomData();

            DBHandler dbHandler = new DBHandler();
            boolean isVal = dbHandler.checkRoomId(roomId);
            System.out.println(isVal);
            if(isVal){
                String creator = dbHandler.joinMeetingRoom(roomId,userId,nickName);
                roomData.setRoomId(roomId);
                roomData.setCreator(creator);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MeetingRoomView.fxml"));
                Parent root = (Parent) loader.load();
                PageController pageController = new PageController();
                pageController.setTitle("Meeting room");
                pageController.changePage(new Scene(root));
            }


        }
    }
}
