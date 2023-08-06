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

public class ShowCarController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button showCarButton;

    @FXML
    private TextField showCarIDFild;
    private Stage myStage;
    private String warnings="";
    private DatabsaseHandler databsaseHandler = new DatabsaseHandler();
    private int carID=-1;
    @FXML
    public void initialize() {
            showCarButton.setOnAction(e->{
                databsaseHandler.fillCarsHasMapData(TrainService.cars);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                setCarID();
                if(carID!=-1) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succeed car Info:");
                    alert.setContentText(String.valueOf(databsaseHandler.getCar(carID)));
                    alert.showAndWait();
                    myStage.close();
                }else{
                    alert.setContentText(warnings+"\n"+"Fill the car ID field");
                    alert.showAndWait();
                    warnings = "";
                }
            });

        }
    private void setCarID() {
        try {
            if (checkIDGreterThenZero(Integer.parseInt(showCarIDFild.getText())) && !databsaseHandler.CheckCarNotExist(carID))
                carID = Integer.parseInt(showCarIDFild.getText());
            else warnings = warnings + "\n" + "There is no car with such ID";
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
