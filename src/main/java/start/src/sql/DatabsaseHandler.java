package start.src.sql;

import start.src.file.AddToFile;
import start.src.entity.Car;
import start.src.entity.ComfortRating;
import start.src.entity.Train;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Map;

public class DatabsaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionStr = "jdbc:mysql://" +dbHost+ ":"
                +dbPort+"/"+dbName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(connectionStr, dbUser, dbPass);
        return dbConnection;
    }
    public void addTrain(int trainID, String destination, LocalDateTime startTime, LocalDateTime arriveTime){
        //String insertValues = "INSERT INTO "+Const.TRAIN_TABLE+"( "+Const.TRAIN_ID+", "+Const.TRAIN_DESTINATION+ "," +Const.TRAIN_START_TIME+","+Const.TRAIN_ARRIVE_TIME+ ","+Const.TRAIN_DATE+ " ) "+ "VALUES(?, ?, ?, ?)";
        String insertValues = "INSERT INTO project.trains (trainID, destination, startTime, arriveTime) VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insertValues);
            prSt.setInt(1, trainID);
            prSt.setString(2, destination);
            prSt.setTimestamp(3, Timestamp.valueOf(startTime));
            prSt.setTimestamp(4, Timestamp.valueOf(arriveTime));
            prSt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public boolean CheckTrainNotExist(int trainID)  {

        String select = "SELECT * FROM "+dbName+".trains WHERE trainID="+trainID;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();
            if(resultSet.next())
                return false;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public void addCar(int carID, int trainID, int seatsNumber, int pessengersNumber, int cargoAmount, ComfortRating comfortRating, int sequenceNumber){
        String insert = "INSERT INTO project.cars (carID, trainID, seatsNumber, pessengersNumber, cargoAmount, comfortClass, sequenceNumber) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setInt(1, carID);
            prSt.setInt(2, trainID);
            prSt.setInt(3, seatsNumber);
            prSt.setInt(4, pessengersNumber);
            prSt.setInt(5, cargoAmount);
            prSt.setString(6, String.valueOf(comfortRating));
            prSt.setInt(7, seatsNumber);
            prSt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public boolean CheckCarNotExist(int carID)  {//переписати

        String select = "SELECT * FROM "+dbName+".cars WHERE carID="+carID;
        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            ResultSet resultSet = prSt.executeQuery();
            if(resultSet.next())
                return false;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    public void fillCarsHasMapData(Map<Integer, Car> cars){
        try {

            dbConnection=getDbConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT carID, trainID, seatsNumber, pessengersNumber, cargoAmount, comfortClass, sequenceNumber FROM "+dbName+".cars");
            Car car;

            while (resultSet.next()){
                car=new Car(resultSet.getInt("carID"), resultSet.getInt("trainID"),
                        resultSet.getInt("seatsNumber"), setComfortClass(resultSet.getString("comfortClass")),
                        resultSet.getInt("pessengersNumber"), resultSet.getInt("cargoAmount"));
                if(cars.get(car.getCarNumber())==null) {
                    car.setSequenceNumber(resultSet.getInt("sequenceNumber"));
                    cars.put(car.getCarNumber(), car);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void fillTrainsHashMapData(Map<Integer, Train> trains){

        try {
            dbConnection=getDbConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM "+dbName+".trains");
            Train train;
            while (resultSet.next()){
               if(trains.get(resultSet.getInt("trainID"))==null) {
                   train = new Train(resultSet.getInt("trainID"), resultSet.getString("destination"), resultSet.getTimestamp("startTime").toLocalDateTime(),
                           resultSet.getTimestamp("startTime").toLocalDateTime());
                   trains.put(train.getTrainNumber(), train);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    private ComfortRating setComfortClass(String RatingStr){
        ComfortRating comfortClass;
        switch (RatingStr){
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
        return comfortClass;
    }

    public void deleteCar(int carID){
        try {
            dbConnection = getDbConnection();
            Statement statement = dbConnection.createStatement();
            String deleteCarStr = "DELETE FROM project.cars WHERE carID ="+carID +" AND ID>0";
            statement.executeUpdate(deleteCarStr);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteTrain(int trainID){
        try {
            dbConnection = getDbConnection();
            Statement statement = dbConnection.createStatement();
            String deleteTrainStr = "DELETE FROM project.trains WHERE trainID ="+trainID;
            String deleteAllCarsFromTrainStr ="DELETE FROM project.cars WHERE trainID ="+trainID ;

            statement.executeUpdate(deleteAllCarsFromTrainStr);
            statement.executeUpdate(deleteTrainStr);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Train getTrain(int trainID){
        Train train=null;
        try {
            dbConnection=getDbConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM "+dbName+".trains WHERE trainID="+trainID);
            if(resultSet.next()) {
                train = new Train(resultSet.getInt("trainID"), resultSet.getString("destination"), resultSet.getTimestamp("startTime").toLocalDateTime(),
                        resultSet.getTimestamp("startTime").toLocalDateTime());
                return train;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return train;
    }

    public Car getCar(int carID){
        Car car=null;
        try {
            dbConnection=getDbConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM "+dbName+".cars WHERE carID="+carID+" AND ID>0");
            if(resultSet.next()) {
                car=new Car(resultSet.getInt("carID"), resultSet.getInt("trainID"),
                        resultSet.getInt("seatsNumber"), setComfortClass(resultSet.getString("comfortClass")),
                        resultSet.getInt("pessengersNumber"), resultSet.getInt("cargoAmount"));
                return car;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return car;
    }

    public void deleteAllCarFromDB(){
        try {
            dbConnection = getDbConnection();
            Statement statement = dbConnection.createStatement();
            String deleteCarStr = "DELETE FROM project.cars ";
            statement.executeUpdate(deleteCarStr);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCarsToFile(AddToFile addToFile){
        try {
            dbConnection=getDbConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM "+dbName+".cars");

            StringBuilder carStr = new StringBuilder("All Cars\n");
            while(resultSet.next()) {
                String tmp = "CarID:"+String.valueOf(resultSet.getInt("carID"))+"; TrainID:"+resultSet.getInt("trainID")+"; Seats number:"+
                        resultSet.getInt("seatsNumber")+ "; ComfortClass:"+ setComfortClass(resultSet.getString("comfortClass"))+"; Pessenger number"+
                        resultSet.getInt("pessengersNumber")+"; Cargo amount"+ resultSet.getInt("cargoAmount")+";";
                carStr.append(tmp+"\n-----------------------------------------------------------------------------------------------------");

            }
            addToFile.writeToFile(String.valueOf(carStr), 1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTrainsToFile(AddToFile addToFile) {
        try {
            dbConnection = getDbConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + dbName + ".trains");
            StringBuilder carStr = new StringBuilder("All trains:\n");

            while (resultSet.next()) {
                String tmp = "CarID:" + resultSet.getInt("trainID") + "; destination;" + resultSet.getString("destination") + "; start time:" + resultSet.getTimestamp("startTime").toLocalDateTime() +
                        "; arrive time:" + resultSet.getTimestamp("arriveTime").toLocalDateTime() + ";";
                carStr.append(tmp + "\n-----------------------------------------------------------------------------------------------------");

            }
            addToFile.writeToFile(String.valueOf(carStr), 1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

/*    private int getNumberOfRows(String tableName){

        try {
            Statement countStatement = dbConnection.createStatement();
            ResultSet resultSet = countStatement.executeQuery("select count(*) from "+dbName+"."+tableName);
            return resultSet.getRow();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }*/
}
