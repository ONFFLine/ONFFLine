import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CustomizingController implements Initializable {

    @FXML
    private ComboBox<String> hairShapeOption;
    @FXML
    private ComboBox<String> hairColorOption;
    @FXML
    private ComboBox<String> eyeColorOption;
    @FXML
    private ComboBox<String> skinColorOption;

    @FXML
    private QuadCurve upHair;
    @FXML
    private Rectangle downHair;
    @FXML
    private Ellipse skin;
    @FXML
    private Ellipse leftEye;
    @FXML
    private Ellipse rightEye;




    ObservableList<String> hairShapeList = FXCollections.observableArrayList("삭발","긴머리","짧은머리");
    ObservableList<String> hairColorList = FXCollections.observableArrayList("마젠타","블랙");
    ObservableList<String> eyeColorList = FXCollections.observableArrayList("초록","블랙","파랑");
    ObservableList<String> skinColorList = FXCollections.observableArrayList("황","흰색","블랙");

    public CustomizingController() {
    }

    @FXML
    private void itemChanged(ActionEvent actionEvent) {
        if(actionEvent.getSource().equals(hairColorOption)) {
            String t = hairColorOption.getValue();
            Paint color = null;

            if(t.equals("마젠타"))
                color = Color.MAGENTA;
            if(t.equals("블랙"))
                color = Color.BLACK;
            upHair.setFill(color);
            downHair.setFill(color);
        }
        if(actionEvent.getSource().equals(eyeColorOption)) {
            String t = eyeColorOption.getValue();
            Paint color = null;

            if(t.equals("초록"))
                color = Color.DARKGREEN;
            if(t.equals("블랙"))
                color = Color.BLACK;
            if(t.equals("파랑"))
                color = Color.SKYBLUE;
            leftEye.setFill(color);
            rightEye.setFill(color);
        }
        if(actionEvent.getSource().equals(skinColorOption)) {
            String t = skinColorOption.getValue();
            Paint color = null;

            if(t.equals("황"))
                color = Color.YELLOW;
            if(t.equals("블랙"))
                color = Color.BLACK;
            if(t.equals("흰색"))
                color = Color.WHITESMOKE;
            skin.setFill(color);
        }
        if(actionEvent.getSource().equals(hairShapeOption)) {
            String t = hairShapeOption.getValue();

            if(t.equals("삭발")){
                upHair.setVisible(false);
                downHair.setVisible(false);
            }
            if(t.equals("긴머리")) {
                upHair.setVisible(true);
                downHair.setVisible(true);
            }
            if(t.equals("짧은머리")) {
                upHair.setVisible(true);
                downHair.setVisible(false);
            }
        }

    }

    @FXML
    private void saveOptions() throws IOException {
        String hairShape = hairShapeOption.getValue();
        String hairColor = hairColorOption.getValue();
        String eyeColor = eyeColorOption.getValue();
        String skinColor = skinColorOption.getValue();

        DBHandler dbHandler = new DBHandler();
        CustomData customData = new CustomData(hairShape, hairColor, eyeColor, skinColor);

        dbHandler.saveCustoms(customData);
        PageController pageController = new PageController();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainView.fxml")));
        pageController.setTitle("ONFFLINE");
        pageController.changePage(new Scene(root,500,382.0));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        hairShapeOption.setItems(hairShapeList);
        hairColorOption.setItems(hairColorList);
        eyeColorOption.setItems(eyeColorList);
        skinColorOption.setItems(skinColorList);
    }
}
