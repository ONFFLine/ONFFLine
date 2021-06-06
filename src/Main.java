import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opencv.core.Core;

import java.util.Objects;

public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{

        PageController pageController = new PageController();

        PageController.setWindow(primaryStage);

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("MainView.fxml")));
        pageController.changePage(new Scene(root,500,382.0));
        pageController.setTitle("ONFFLINE");
        primaryStage.show();
        primaryStage.setOnCloseRequest(event -> {
            System.exit(1);
        });

    }


    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME );
        launch(args);
}
}
