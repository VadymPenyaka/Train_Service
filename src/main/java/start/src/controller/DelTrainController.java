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
import start.src.object.Car;
import start.src.object.Train;
import start.src.sql.DatabsaseHandler;

public class DelTrainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField trainIDFild;

    @FXML
    private Button deleteButton;
    Stage myStage;
    private String warnings="\0";
    private int traiNumber=-1;
    private DatabsaseHandler databsaseHandler = new DatabsaseHandler();
    @FXML
    public void initialize() {//Добавити видалення всіх вагоні поїзді з БД
       databsaseHandler.fillCarsHasMapData(TrainService.cars);
       databsaseHandler.fillTrainsHashMapData(TrainService.trains);
       deleteButton.setOnAction(e->{
           Alert alert = new Alert(Alert.AlertType.ERROR);
           setTrainNumber();
           if(TrainService.trains.get(traiNumber)!=null) {
                deleteAllTrainFromTrainList();
               databsaseHandler.deleteTrain(traiNumber);
               TrainService.trains.remove(traiNumber);
               alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setTitle("Succeed");
               alert.setContentText("The train:"+traiNumber+" has been deleted successfully!");
               alert.showAndWait();

               myStage.close();
           }else{
               alert.setContentText(warnings+"\n"+"Fill the train ID field");
               alert.showAndWait();
           }
       });
    }

    private void deleteAllTrainFromTrainList() {
        for(Car car: TrainService.trains.get(traiNumber).getCars()){
            TrainService.cars.remove(car.getCarNumber());
            TrainService.trains.get(traiNumber).delCar(car.getCarNumber());
        }
    }

    public void setTrainNumber(){
        try {
            if(TrainService.trains.get(Integer.parseInt(trainIDFild.getText()))!=null && checkIDGreterThenZero(Integer.parseInt(trainIDFild.getText())))
                traiNumber=Integer.parseInt(trainIDFild.getText());
            else  warnings=warnings+"\n"+"The train with such ID doesn't exist";
        }catch (NumberFormatException e){
            warnings=warnings+"\n"+"Train ID must be a number";
        }catch (Exception e){
            warnings=warnings+"\n"+e;
        }
    }

    public boolean checkIDGreterThenZero(int ID){
        if(ID<0) {
            warnings=warnings+"\n"+"ID must be greater then zero";
            return false;
        }
        return true;
    }

    public void setStage(Stage stage) {
        myStage = stage;
    }
}


