package start.src.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import start.src.menu.TrainService;
import start.src.sql.DatabsaseHandler;

public class ShowTrainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button showButton;

    @FXML
    private TextField showTrainIDFild;
    private Stage myStage;
    private String warnings="";
    private DatabsaseHandler databsaseHandler = new DatabsaseHandler();
    private int trainID=-1;
    @FXML
    public void initialize() {

        showButton.setOnAction(e->{
            databsaseHandler.fillTrainsHashMapData(TrainService.trains);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            setTrainID();
            if(trainID!=-1) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succeed Train Info:");
                alert.setContentText(String.valueOf(databsaseHandler.getTrain(trainID)));
                alert.showAndWait();
                myStage.close();
            }else{
                alert.setContentText(warnings+"\n"+"Fill the train ID field");
                alert.showAndWait();
                warnings = "";
            }
        });

    }

    private void setTrainID() {
        try {
            if (checkIDGreterThenZero(Integer.parseInt(showTrainIDFild.getText())) )
                trainID = Integer.parseInt(showTrainIDFild.getText());
            else warnings = warnings + "\n" + "The train ID should be  greater then zero";
        } catch (NumberFormatException e) {
            warnings = warnings + "\n" + "Train ID must be a number";
        } catch (Exception e) {
            warnings = warnings + "\n" + e;
        }
    }

    public boolean checkIDGreterThenZero(int number){
        if(number<0) {
            warnings=warnings+"\n"+"All numbers must be greater then zero";
            return false;
        }
        return true;
    }

    public void setStage(Stage stage) {
        myStage = stage;
    }

}
