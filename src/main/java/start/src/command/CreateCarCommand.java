package start.src.command;

import start.src.menu.TrainService ;

public class CreateCarCommand implements Command{
    TrainService service = new TrainService();

    public CreateCarCommand(TrainService service){
        this.service = service;
    }

    @Override
    public void executeMenu(TrainService service) {
        service.createCar();
    }

}
