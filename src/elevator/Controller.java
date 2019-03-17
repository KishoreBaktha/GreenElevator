import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import elevator.rmi.Elevator;
import elevator.rmi.MakeAll;

public class Controller extends Thread implements ActionListener {
    Elevator[] elevators;
    ElevatorHandler[] elevatorHandlers;
    String rmihost;

    public Controller(String[] args) {
        rmihost = /* (args.length > 0)? args[0] : */"localhost";
    }
}