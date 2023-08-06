package start.src.command;

import start.src.menu.TrainService;

import java.util.ArrayList;
import java.util.List;

public class Receiver {
    private List<Command> commands = new ArrayList<>();
    private final TrainService service = new TrainService();
    public void addCommand(Command command){
        commands.add(command);
    }

    public void runCommand(int commandIndex){
        try {
            commands.get(commandIndex).executeMenu(service);
        }catch (Exception e){
            System.out.println(e);
        }

    }

}
