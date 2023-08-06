package start.src.command;

import start.src.menu.TrainService ;

public class ShowTrainWithNumber implements Command{

    TrainService service = new TrainService();

    public ShowTrainWithNumber(TrainService service){
        this.service = service;
    }

    @Override
    public void executeMenu(TrainService service) {
        service.showTrainWithNumber();

    }

}
