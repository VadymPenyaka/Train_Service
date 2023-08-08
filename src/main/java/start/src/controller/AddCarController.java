package start.src.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import start.src.menu.TrainService;
import start.src.entity.Car;
import start.src.entity.ComfortRating;
import start.src.sql.DatabsaseHandler;


import static java.lang.Math.abs;

public class AddCarController implements Initializable {

    @FXML
    public Label numberWarning;
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField CarID;
    @FXML
    private TextField numberOfTrainFild;
    @FXML
    private Button apply;
    @FXML
    private ChoiceBox<String> choseComfortClass;
    private String[] allComfortClasess = {
            "Seating",  "Second class",  "First class", "Luxe"
    };
    @FXML
    private TextField luggageAmount;
    @FXML
    private VBox main;
    @FXML
    private TextField pessengersNumber;
    @FXML
    private  TextField seatsNumber;
    private static TrainService service = new TrainService();
    private static int carNumber=-1;
    private static int carSeatsNumber=0;
    private static int carPassengersNumber=0;
    private static int cargoAmount=0;
    private static ComfortRating comfortClass=null;
    private int trainNumber;
    private Stage stage;
    private DatabsaseHandler databsaseHandler = new DatabsaseHandler();

    @FXML
    public void initialize() {
        databsaseHandler.fillTrainsHashMapData(TrainService.trains);
        databsaseHandler.fillCarsHasMapData(TrainService.cars);
        apply.setOnAction(e -> {
            numberWarning.setText("");
            Car car = setCarInfo();
            if (databsaseHandler.CheckCarNotExist(carNumber) ){
                if (checkInfo() && addCarToTrain(car)) {
                    service.cars.put(car.getCarNumber(), car);
                    databsaseHandler.addCar(carNumber, trainNumber, carSeatsNumber, carPassengersNumber, cargoAmount, comfortClass, car.getSequenceNumber());
                    stage.close();
                }
            }else numberWarning.setText(numberWarning.getText() + "\n" + "A car with such a number already exists");
            if (!checkInfo())
                numberWarning.setText(numberWarning.getText() + "\n" + "Check that you have filled in all the fields");
        });
    }

    private boolean checkInfo(){
        if(carNumber==-1)
            return false;
        if(carSeatsNumber==0)
            return false;
        if(carPassengersNumber==0)
            return false;
        if(cargoAmount==0)
            return false;
        if(comfortClass==null)
            return false;
        return true;
    }
    private Car setCarInfo() {
        setCarNumber();
        setNumberOfSeatsAndPassengers();
        setCargoAmount();
        getTrainNumber();
        Car car = new Car(carNumber, trainNumber, carSeatsNumber, comfortClass, carPassengersNumber, cargoAmount);

        return car;
    }

    private void setCarNumber(){
        try {
            carNumber=Integer.parseInt(CarID.getText());
        }  catch (NumberFormatException e){
            numberWarning.setText(numberWarning.getText()+"\n"+"Car ID must be a number");
        }catch (Exception e){
            numberWarning.setText(numberWarning.getText()+"\n"+e);
        }
    }

    private void setNumberOfSeatsAndPassengers(){

        try {
            int tmpCarSeatsNumber=abs(Integer.parseInt(seatsNumber.getText()));
            int tmpCarPassengersNumber=abs(Integer.parseInt(pessengersNumber.getText()));
            if(tmpCarSeatsNumber < tmpCarPassengersNumber){
                numberWarning.setText(numberWarning.getText()+"\n"+"Number of passengers can not be greater then number of seats\n");
            }else{
                carSeatsNumber=abs(Integer.parseInt(seatsNumber.getText()));
                carPassengersNumber=abs(Integer.parseInt(pessengersNumber.getText()));
            }
        }catch (NumberFormatException e){
            numberWarning.setText(numberWarning.getText()+"\n"+"Number of seats and passengers must be a number");
        }

    }

    private void setCargoAmount(){

        try {
            cargoAmount=Integer.parseInt(luggageAmount.getText());
        }catch (NumberFormatException e){
            numberWarning.setText(numberWarning.getText()+"\n"+"Amount of luggage must be a number");
        }catch (Exception e){
            numberWarning.setText(numberWarning.getText()+"\n"+e);
        }

    }

    private void setComfortClasses(ActionEvent event){
        String className = choseComfortClass.getValue();
        switch (className){
            case "Seating":
                comfortClass=ComfortRating.SEATING;
                break;
            case  "Second class":
                comfortClass=ComfortRating.SECONDCLASS;
                break;
            case "First class":
                comfortClass=ComfortRating.FIRSTCLASS;
                break;
            default:
                comfortClass =ComfortRating.LUXE;
        }
    }

    public boolean addCarToTrain(Car car){

        if(!databsaseHandler.CheckTrainNotExist(trainNumber)){
            TrainService.trains.get(trainNumber).addCar(car);
            return true;
        }else numberWarning.setText(numberWarning.getText()+"\n"+"There isn't train with such ID");
        return false;
    }

    public void getTrainNumber(){
        try {
            trainNumber=Integer.parseInt(numberOfTrainFild.getText());
        }catch (NumberFormatException e){
            numberWarning.setText(numberWarning.getText()+"\n"+"Train ID must be a number");
        }catch (Exception e){
            numberWarning.setText(numberWarning.getText()+"\n"+e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choseComfortClass.getItems().addAll(allComfortClasess);
        choseComfortClass.setOnAction(this::setComfortClasses);
    }

    public void setStage(Stage stage){
        this.stage=stage;
    }

}
