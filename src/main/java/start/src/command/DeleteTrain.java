package start.src.command;

import start.src.menu.TrainService;

public class DeleteTrain implements Command{

    TrainService service;

    public DeleteTrain(TrainService service){
        this.service = service;
    }

    @Override
    public void executeMenu(TrainService service) {
        service.deleteTrain();
    }

}
