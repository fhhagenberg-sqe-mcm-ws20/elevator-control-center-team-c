package at.fhhagenberg.elevator;

import at.fhhagenberg.elevator.converter.InterfaceToModelConverter;
import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.view.ElevatorControlCenterPane;
import javafx.application.Platform;
import lombok.SneakyThrows;
import sqelevator.IElevator;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class RMIElevatorAdapter {

    private IElevator controller;
    private InterfaceToModelConverter converter;
    private Boolean connected=false;

    public RMIElevatorAdapter() {
        connect();
    }

    public Boolean isConnected() {
        return connected;
    }

    public void updateBuilding(Building building) {
        try {
            converter.convert(building);
        } catch (Exception e) {
            reconnect();
        }
    }

    public void setTarget(int elevatorNumber, int target) {
        try {
            controller.setTarget(elevatorNumber, target);
        } catch (RemoteException e) {
            reconnect();
        }
    }

    private void connect() {
        try {
            controller = (IElevator) Naming.lookup("rmi://localhost/ElevatorSim");
            converter = new InterfaceToModelConverter(controller);
            connected= true;
        } catch (Exception e) {
            connected = false;
        }
    }

    private void reconnect() {
        connected = false;
        connect();
    }

}
