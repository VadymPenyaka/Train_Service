package start.src.menu;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import start.src.controller.*;

import start.src.file.*;
import start.src.entity.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TrainService{
    private static final Scanner obj = new Scanner(System.in);
    public static Map<Integer, Train> trains = new HashMap<>();
    public static Map<Integer, Car> cars = new HashMap<>();

    public static AddToFile trainsFile = new AddToFile("trains");//check
    public static AddToFile carsFile = new AddToFile("cars");
    public static AddToFile actionsFile = new AddToFile("actions");
    private static String tmpStr;

    public void createCar(){
        FXMLLoader createCarLoader = new FXMLLoader();
        createCarLoader.setLocation(getClass().getResource("/start/src/createCar.fxml"));
        try {
            createCarLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        AddCarController createCarController = createCarLoader.getController();
        createCarController.initialize();
        Parent root= createCarLoader.getRoot();
        Stage stage= new Stage();
        stage.setScene(new Scene(root));
        createCarController.setStage(stage);
        stage.showAndWait();

    }




    public void createTrain(){
        FXMLLoader createTrainLoader = new FXMLLoader();
        createTrainLoader.setLocation(getClass().getResource("/start/src/createTrain.fxml"));
        try {
            createTrainLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        CreateTrainController createTrainController = createTrainLoader.getController();
        createTrainController.initialize();
        Parent root= createTrainLoader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        createTrainController.setStage(stage);
        stage.showAndWait();
    }





    public void deleteTrain(){
        FXMLLoader deleteTrain = new FXMLLoader();
        deleteTrain.setLocation(getClass().getResource("/start/src/delTrain.fxml"));
        try {
            deleteTrain.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        DelTrainController delTrainController = deleteTrain.getController();
        delTrainController.initialize();
        Parent root= deleteTrain.getRoot();
        Stage stage= new Stage();
        stage.setScene(new Scene(root));
        delTrainController.setStage(stage);
        stage.showAndWait();
    }

    public void deleteCar(){

        FXMLLoader deleteCarLoader = new FXMLLoader();
        deleteCarLoader.setLocation(getClass().getResource("/start/src/delCaFromTrain.fxml"));
        try {
            deleteCarLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }
        DelCarFromTrainController delCarFromTrainController = deleteCarLoader.getController();
        delCarFromTrainController.initialize();
        Parent root= deleteCarLoader.getRoot();
        Stage stage= new Stage();
        stage.setScene(new Scene(root));
        delCarFromTrainController.setStage(stage);
        stage.showAndWait();
    }


    public void showTrainWithNumber(){
        FXMLLoader showTrainLoader = new FXMLLoader();
        showTrainLoader.setLocation(getClass().getResource("/start/src/showTrainWitnNumber.fxml"));
        try {
            showTrainLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }

        ShowTrainController showTrainController = showTrainLoader.getController();
        showTrainController.initialize();
        Parent root= showTrainLoader.getRoot();
        Stage stage= new Stage();
        stage.setScene(new Scene(root));
        showTrainController.setStage(stage);
        stage.showAndWait();
    }

    public void showCarWithNumber(){
        FXMLLoader showCarLoader = new FXMLLoader();
        showCarLoader.setLocation(getClass().getResource("/start/src/showCarWithNumber.fxml"));
        try {
            showCarLoader.load();
        }catch (IOException e){
            e.printStackTrace();
        }

        ShowCarController showCarController = showCarLoader.getController();
        showCarController.initialize();
        Parent root= showCarLoader.getRoot();
        Stage stage= new Stage();
        stage.setScene(new Scene(root));
        showCarController.setStage(stage);
        stage.showAndWait();

    }

    public void showTrainWythSortedCarsByComfort(){
        FXMLLoader showTrainAndCarsLoadr = new FXMLLoader();
        showTrainAndCarsLoadr.setLocation(getClass().getResource("/start/src/showTrainWithCars.fxml"));
        try {
            showTrainAndCarsLoadr.load();
        }catch (IOException e){
            e.printStackTrace();
        }

        ShowTrainWithCarsController showTrainWithCarsController = showTrainAndCarsLoadr.getController();
        showTrainWithCarsController.initialize();
        Parent root= showTrainAndCarsLoadr.getRoot();
        Stage stage= new Stage();
        stage.setScene(new Scene(root));
        showTrainWithCarsController.setStage(stage);
        stage.showAndWait();

    }


}





