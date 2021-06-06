import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

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
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        }
        else if(event.getSource().equals(joinButton)) {
            int roomId = Integer.parseInt(roomfield.getText());
            String userId = userfield.getText();
            String nickName = nickfield.getText();

            System.out.println("roomId : " + roomId + "userId : " + userId + "nick : " + nickName);

            MeetingRoomData roomData = new MeetingRoomData();

            DBHandler dbHandler = new DBHandler();
            boolean isVal = dbHandler.checkRoomId(roomId);
            System.out.println(isVal);
            if(isVal){
                dbHandler.joinMeetingRoom(roomId,userId,nickName);
                roomData.setRoomId(roomId);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("MeetingRoomView.fxml"));
                Parent root = (Parent) loader.load();
                Stage stage = new Stage();
                stage.setTitle("Meeting Room");
                stage.setScene(new Scene(root));
                stage.show();

            }


        }
    }
}
