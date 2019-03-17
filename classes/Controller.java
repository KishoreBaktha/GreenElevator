
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Comparator;
import elevator.rmi.Elevator;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import elevator.Elevators;
import elevator.rmi.Elevator;
import elevator.rmi.MakeAll;
//import elevator.Job;
//import elevator.EventHandler;
import elevator.rmi.MakeAll;

public class Controller extends Thread implements ActionListener {
    Elevator[] elevators;
    ElevatorController[] elevatorControllers;
    String rmihost;
    public Controller(String[] args) {
        rmihost = /* (args.length > 0)? args[0] : */"localhost";
    }

    public void run()
    {
        try {
            //Initiate and add listeners to floor and inside buttons
            MakeAll.init(rmihost);
            MakeAll.addFloorListener(this);
            MakeAll.addInsideListener(this);

            elevators = new Elevator[MakeAll.getNumberOfElevators()];
            elevatorControllers = new ElevatorController[MakeAll.getNumberOfElevators()];

            //Get all elevators and assign handlers to them
            for (int i = 0; i < elevators.length; i++) {
                elevators[i] = MakeAll.getElevator(i + 1);
                elevatorControllers[i] = new ElevatorController(elevators[i], i + 1);
                elevatorControllers[i].start();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Controller(args).start();
    }

    public void actionPerformed(ActionEvent e) {
        try {
            String action = e.getActionCommand();
            String[] arguments = action.split(" ");
            //If event is from a floor button
            if (action.contains("b"))
            {
                int floor = Integer.parseInt(arguments[1]);
                int direction = Integer.parseInt(arguments[2]);
                //Create job and assign it to some elevator
                Job job = new Job(floor);
                job.setDirection(direction);
                assignJob(job);
            }
            //If event is from an inside button
            else if (action.contains("p")) {
                //If it is the stop button, toggle stop
                if (action.contains("32000"))
                {
                    int elevatorIndex = Integer.parseInt(arguments[1]) - 1;
                    elevatorControllers[elevatorIndex].toggleStop();
                }
                //Otherwise add the job directly to the elevator
                else {
                    int floor = Integer.parseInt(arguments[2]);
                    int elevatorIndex = Integer.parseInt(arguments[1]) - 1;
                    Job job = new Job(floor);
                    elevatorControllers[elevatorIndex].addJob(job);
                }
            }
        }
        catch (Exception e1)
        {
            e1.printStackTrace();
        }
    }
    //assignJob - Assigns a job to some elevator depending on cost
    public void assignJob(Job job) {
        double min = Double.MAX_VALUE;
        int elevatorIndex = 0;
        System.out.println("Cost calculation for the job: " + job.toString());
        //Go through all elevators and find the one with the lowest cost
        for (int i = 0; i < elevatorControllers.length; i++) {
            System.out.println();
            System.out.println("Calculation of cost for elevator- "+(i+1));
            double cost = elevatorControllers[i].getCost(job);
            if (cost < min) {
                min = cost;
                elevatorIndex = i;
            }
        }
        System.out.println();
        //Assign the job to the elevator with the lowest cost
        elevatorControllers[elevatorIndex].addJob(job);
    }
}
