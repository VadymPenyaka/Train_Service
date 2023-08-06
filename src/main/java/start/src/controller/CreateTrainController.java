package start.src.controller;

import java.net.URL;
import java.time.*;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import start.src.menu.TrainService;
import start.src.object.Train;
import start.src.sql.DatabsaseHandler;

public class CreateTrainController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button applyButton;

    @FXML
    private TextField arriveTimeHoursFild;
    @FXML
    private TextField arriveTimeMinutesFild;
    @FXML
    private TextField dayFild;

    @FXML
    private TextField destinationFild;

    @FXML
    private VBox main;

    @FXML
    private TextField monthFild;

    @FXML
    private Label numberWarning;

    @FXML
    private TextField startTimeHoursFild;

    @FXML
    private TextField startTimeMinutesFild;

    @FXML
    private TextField trainIDFild;

    @FXML
    private TextField yearFild;
    private Stage myStage;
    private int trainNumber=0;
    private LocalDateTime start=null;
    private LocalDateTime arrive=null;
    private String destination=null;
    private  DatabsaseHandler databsaseHandler = new DatabsaseHandler();
    @FXML
    public void initialize() {
        databsaseHandler.fillTrainsHashMapData(TrainService.trains);
        applyButton.setOnAction(e->{
            numberWarning.setText("");
            Train train = setTrainInfo();
            if(databsaseHandler.CheckTrainNotExist(trainNumber) && checkInfo()){
                TrainService.trains.put(train.getTrainNumber(), train);
                databsaseHandler.addTrain(trainNumber, destination, start, arrive);
                myStage.close();
            }
            else numberWarning.setText(numberWarning.getText()+"\n"+"A car with such a number already exists");
            if(!checkInfo())
                numberWarning.setText(numberWarning.getText()+"\n"+"Check that you have correct filled in all the fields");
        });
    }

    private boolean checkInfo(){
        if(trainNumber==0)
            return false;
        if(start==null)
            return false;
        if(arrive==null)
            return false;
        if(destination==null)
            return false;
        return true;
    }

    private Train setTrainInfo(){
        setTrainNumber();
        setTime();
        destination=destinationFild.getText();
        Train train = new Train(trainNumber, destination, start, arrive);

        return train;
    }

    private void setTrainNumber(){
        try {
            trainNumber=Integer.parseInt(trainIDFild.getText());
        }  catch (NumberFormatException e){
            numberWarning.setText(numberWarning.getText()+"\n"+"Car ID must be a number");
        }catch (Exception e){
            numberWarning.setText(numberWarning.getText()+"\n"+"e");
        }
    }

    public void setTime(){
        try{
            String startStr = (LocalDate.of(Integer.parseInt(yearFild.getText()), Integer.parseInt(monthFild.getText()), Integer.parseInt(dayFild.getText()))+" "+
                    startTimeHoursFild.getText()+":"+startTimeMinutesFild.getText());
            String arriveStr = (LocalDate.of(Integer.parseInt(yearFild.getText()), Integer.parseInt(monthFild.getText()), Integer.parseInt(dayFild.getText()))+
                    " "+arriveTimeHoursFild.getText()+":"+arriveTimeMinutesFild.getText());
            DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            arrive = LocalDateTime.parse(arriveStr, formater);
            start = LocalDateTime.parse(startStr, formater);

        } catch (DateTimeParseException e){
            numberWarning.setText(numberWarning.getText()+"\n"+"The time entered is incorrect psrse"+e);
        } catch (DateTimeException e){
            numberWarning.setText(numberWarning.getText()+"\n"+"The time entered is incorrect");
        } catch (Exception e){
            numberWarning.setText(numberWarning.getText()+"\n"+e);
        }


    }


    public void setStage (Stage stage){
        myStage=stage;
    }
}
