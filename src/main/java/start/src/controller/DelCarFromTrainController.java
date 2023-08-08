package start.src.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import start.src.menu.TrainService;
import start.src.entity.Car;
import start.src.sql.DatabsaseHandler;

public class DelCarFromTrainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TextField carNumberFild;
    @FXML
    private TextField trainNumberFild;

    @FXML
    private Button deleteButton;

    private String warnings="\0";
    public Stage myStage;
    private int carNumber=-1;
    private int traiNumber=-1;
    private DatabsaseHandler databsaseHandler = new DatabsaseHandler();
    @FXML
    public void initialize() {
        databsaseHandler.fillTrainsHashMapData(TrainService.trains);
        databsaseHandler.fillCarsHasMapData(TrainService.cars);

        deleteButton.setOnAction(e->{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            setCarNumber();
            setTrainNumber();
            if(checkInfo()) {
                if (checkCarExist()) {
                    deleteFromTrain();
                    TrainService.cars.remove(carNumber);
                    databsaseHandler.deleteCar(carNumber);
                    alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succeed");
                    alert.setContentText("The car:"+carNumber+" has been deleted successfully from train:"+traiNumber);
                    alert.showAndWait();

                    myStage.close();
                }

            }else {
                warnings = warnings + "\n" + "Check that you have filled in all the fields";
                    alert.setTitle("Alert");
                    alert.setContentText(warnings);
                    alert.showAndWait();
            }
        });
    }

    public void setCarNumber(){
        try {
            if(checkIDGreterThenZero(Integer.parseInt(trainNumberFild.getText())))
                carNumber=Integer.parseInt(carNumberFild.getText());
        }catch (NumberFormatException e){
            warnings=warnings+"\n"+"Car ID must be a number";
        }catch (Exception e){
            warnings=warnings+"\n"+e;
        }
    }

    public void setTrainNumber(){
        try {
            if(TrainService.trains.get(Integer.parseInt(trainNumberFild.getText()))!=null && checkIDGreterThenZero(Integer.parseInt(trainNumberFild.getText())))
                traiNumber=Integer.parseInt(trainNumberFild.getText());
            else  warnings=warnings+"\n"+"The train with such ID doesn't exist";
        }  catch (NumberFormatException e){
            warnings=warnings+"\n"+"Train ID must be a number";
        }catch (Exception e){
            warnings=warnings+"\n"+e;
        }
    }

    public int searchCarInTrain(){
        List<Car> cars= TrainService.trains.get(traiNumber).getCars();
        for(Car car: cars){
            if(car.getCarNumber()==carNumber)
                return car.getSequenceNumber()-1;
        }
        return -1;
    }

    public void deleteFromTrain(){
        if(searchCarInTrain()!=-1) {
            TrainService.trains.get(traiNumber).delCar(searchCarInTrain());
        }
        else warnings=warnings+"\n"+"The car is in another train ID:"+TrainService.cars.get(carNumber).getTrainNumber();
    }

    public boolean checkCarExist(){
        if(TrainService.cars.get(carNumber)!=null){
            return true;
        }else warnings=warnings+"\n"+"The car with such ID doesn't exist";
        return false;
    }
    public boolean checkInfo(){
        if(carNumber==-1) return false;
        if(traiNumber==-1) return false;
        return true;
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

