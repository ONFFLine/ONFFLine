import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class PageController {
    @FXML
    private static AnchorPane displayView;

    private static Stage window;

    public static void setWindow(Stage window) {
        PageController.window = window;
    }

    public void setTitle(String title) {
        window.setTitle(title);
    }
    public void changePage(Scene page){
        window.setScene(page);
    }

    public void detachPage(AnchorPane page) {
    }

    public PageController() throws IOException {

    }
}
