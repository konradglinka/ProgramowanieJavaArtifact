import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/sample.fxml"));
        Parent root = myLoader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(true);

        primaryStage.setTitle("Projekt zaliczeniowy Konrad Glinka, Adrian Żak, Michał Rus");
        primaryStage.show();



    }


    public static void main(String[] args) {
        launch(args);
    }
}
