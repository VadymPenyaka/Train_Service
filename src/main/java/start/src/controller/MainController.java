package start.src.controller;
import javafx.scene.control.*;
import start.src.command.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import start.src.file.AddToFile;
import start.src.menu.TrainService;
import start.src.entity.Car;
import start.src.sql.DatabsaseHandler;


public class MainController {

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private MenuItem createNewCar;
    @FXML
    private MenuItem createNewTrain;

    @FXML
    private MenuItem delCar;

    @FXML
    private MenuItem delTrain;

    @FXML
    private VBox main;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Menu menuCRND;

    @FXML
    private Menu menuShow;


    @FXML
    private MenuItem showCarWithNumber;

    @FXML
    private MenuItem showTrainWithCars;

    @FXML
    private MenuItem showTrainWithNumber;
    @FXML
    private Button searchButton;
    @FXML
    private TextField trainIDFild;
    @FXML
    private TextField freePlacesFild;
    @FXML
    private Label warningsLabel;
    @FXML
    private MenuItem showTrainsInFile;
    @FXML
    private MenuItem showCarsInFile;

    private static final TrainService service = new TrainService();
    DatabsaseHandler databsaseHandler = new  DatabsaseHandler();
    private int trainID=-1;
    private int freePlacesNumber=-1;
    public static Receiver addAllCommand(){
        Receiver receiver = new Receiver();
        receiver.addCommand(new CreateTrainComand(service));
        receiver.addCommand(new CreateCarCommand(service));
        receiver.addCommand(new DeleteTrain(service));
        receiver.addCommand(new DeleteCar(service));
        receiver.addCommand(new ShowCarWithNumber(service));
        receiver.addCommand(new ShowTrainWithNumber(service));
        receiver.addCommand(new ShowTrainWithParameters(service));
        return receiver;
    }



    public void initialize() {
        Receiver receiver = addAllCommand();
        DatabsaseHandler databsaseHandler = new DatabsaseHandler();
        databsaseHandler.fillTrainsHashMapData(TrainService.trains);

        searchButton.setOnAction(e->{
            setTrainID();
            setFreePlacesFild();
            List<Car> necessaryCars = getNecessaryCars();
            warningsLabel.setText("");
            if(!necessaryCars.isEmpty()){
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succeed");
                StringBuilder carsSequences= new StringBuilder();
                for(Car car: necessaryCars)
                    carsSequences.append(car.getSequenceNumber());
                alert.setContentText("The appropriate cars' from train "+trainID+":\n"+carsSequences);
                alert.showAndWait();
            }else warningsLabel.setText(warningsLabel.getText()+"There is not cars with such number of free place in train!");
        });

        createNewTrain.setOnAction(e-> receiver.runCommand(0));
        createNewCar.setOnAction(e-> receiver.runCommand(1));
        delTrain.setOnAction(e-> receiver.runCommand(2));
        delCar.setOnAction(e-> receiver.runCommand(3));
        showCarWithNumber.setOnAction(e-> receiver.runCommand(4));
        showTrainWithNumber.setOnAction(e-> receiver.runCommand(5));
        showTrainWithCars.setOnAction(e-> receiver.runCommand(6));
        showCarsInFile.setOnAction(e-> openCarsFile());
        showTrainsInFile.setOnAction(e->openTrainsFile());



    }

    private void setTrainID(){
        try {
            if(checkIDGreterThenZero(Integer.parseInt(trainIDFild.getText())) )
                trainID=Integer.parseInt(trainIDFild.getText());
            else warningsLabel.setText(warningsLabel.getText()+"There is no train with such ID");
        }  catch (NumberFormatException e){
            warningsLabel.setText(warningsLabel.getText()+"\n"+"Train ID must be a number");
        }catch (Exception e){
            warningsLabel.setText(warningsLabel.getText()+"\n"+e);
        }
    }

    private void setFreePlacesFild(){
        try {
            if(checkIDGreterThenZero(Integer.parseInt(freePlacesFild.getText())))
                freePlacesNumber=Integer.parseInt(freePlacesFild.getText());
        }  catch (NumberFormatException e){
            warningsLabel.setText(warningsLabel.getText()+"\n"+"Number of free places must be a number");
        }catch (Exception e){
            warningsLabel.setText(warningsLabel.getText()+"\n"+e);
        }
    }

    public boolean checkIDGreterThenZero(int number){
        if(number<0) {
            warningsLabel.setText(warningsLabel.getText()+"\n"+"All numbers must be greater then zero");
            return false;
        }
        return true;
    }

    private List<Car> getNecessaryCars(){
        List<Car> cars = new ArrayList<>();
        for(Car car: TrainService.trains.get(trainID).getCars()){
            if(car.getFreePlaces()==freePlacesNumber)
                cars.add(car);
        }
        return cars;
    }

    private void openCarsFile(){
        AddToFile addToFile = new AddToFile("cars");
        databsaseHandler.addCarsToFile(addToFile);
        addToFile.openFile();

    }

    private void openTrainsFile(){
        AddToFile addToFile = new AddToFile("trains");
        databsaseHandler.addTrainsToFile(addToFile);
        addToFile.openFile();

    }


}