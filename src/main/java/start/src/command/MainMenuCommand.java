package start.src.command;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import start.src.controller.MainController;
import start.src.menu.TrainService;

import java.io.IOException;

public class MainMenuCommand implements Command {

    @Override
    public void executeMenu(TrainService service) {
        FXMLLoader createCarLoader = new FXMLLoader();
        createCarLoader.setLocation(getClass().getResource("/start/src/mainWindow.fxml"));
        try {
            createCarLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        MainController main=new MainController();
        main.initialize();
        Parent root= createCarLoader.getRoot();
        Stage stage= new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

}
