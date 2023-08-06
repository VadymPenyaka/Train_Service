package start.src.command;

import start.src.menu.TrainService ;

public class ShowTrainWithParameters implements Command{

    TrainService service = new TrainService();

    public ShowTrainWithParameters(TrainService service){
        this.service = service;
    }

    @Override
    public void executeMenu(TrainService service) {
        service.showTrainWythSortedCarsByComfort();

    }
}
