package start.src.command;

import start.src.menu.TrainService;

public class DeleteCar implements Command{

    TrainService service;

    public DeleteCar(TrainService service){
        this.service = service;
    }

    @Override
    public void executeMenu(TrainService service) {
        service.deleteCar();
    }

}