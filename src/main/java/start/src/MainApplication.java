package start.src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import start.src.sql.DatabsaseHandler;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
            Scene scene = new Scene(root);
            stage.setWidth(900);
            stage.setHeight(600);
            stage.setFullScreen(false);
            stage.setTitle("Train Service");
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(e->{
                DatabsaseHandler dbHendler = new DatabsaseHandler();
                dbHendler.deleteAllCarFromDB();
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }


}
