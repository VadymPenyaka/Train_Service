package start.src.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class Train {
    private final int trainNumber;
    private int numberOfCars=0;
    private LocalDateTime startTime;
    private LocalDateTime arriveTime;
    private String destination;

    private List<Car> cars = new ArrayList<>();


    public Train(int trainNumber, String destination, LocalDateTime startTime, LocalDateTime arriveTime){
        this.trainNumber=trainNumber;
        this.destination=destination;
        this.arriveTime = arriveTime;
        this.startTime = startTime;
    }

    public static void setSequenceNumbers(List<Car> cars){
        for(int i= 0; i<cars.size(); i++)
            cars.get(i).setSequenceNumber(i+1);
    }

    public void addCar(Car car){
        this.cars.add(car);
        setSequenceNumbers(cars);
        car.setTrainNumber(this.trainNumber);
    }

    public Car delCar(int sequenceNumber){
        Car deletedCar = cars.get(sequenceNumber);
        this.cars.remove(sequenceNumber);
        return deletedCar;
    }


    public int getTrainNumber() {
        return trainNumber;
    }

    public int getNubberOfCars(){
        numberOfCars=cars.size();
        return numberOfCars;
    }


    public String getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "Train ID:"+trainNumber+ "; Destination:" +destination + ";\nStart time-"+startTime +"; Arrive time:"+arriveTime+ " Number of cars:" +cars.size() ;
    }

    Comparator<Car> carComfortRatingComparator = new Comparator<Car>(){

        @Override
        public int compare(Car o1, Car o2) {
            return o1.getComfortClass().getComfortRating() - o2.getComfortClass().getComfortRating();
        }

    };
    public List<Car> getSortCarsByPasangerNumber(){
        List<Car> sortedCars = cars;
        sortedCars.sort(carComfortRatingComparator);
        return sortedCars;
    }

    public List<Car> getCars() {
        return cars;
    }



}
