package at.fhhagenberg.elevator;

import at.fhhagenberg.elevator.converter.InterfaceToModelConverter;
import at.fhhagenberg.elevator.model.Building;
import lombok.SneakyThrows;
import sqelevator.IElevator;

import java.rmi.Naming;
import java.rmi.RemoteException;

public class RMIElevatorAdapter {

    private IElevator controller;
    private InterfaceToModelConverter converter;
    private Boolean connected = false;
    private String lookupName;

    public RMIElevatorAdapter(String lookupName) {
        this.lookupName = lookupName;
        reconnect();
    }

    public RMIElevatorAdapter(IElevator elevatorInterface) {
        converter = new InterfaceToModelConverter(elevatorInterface);
        controller = elevatorInterface;
        connected = true;
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

    public void setCommitedDirection(int elevatorNumber, int direction) {
        try {
            controller.setCommittedDirection(elevatorNumber, direction);
        } catch (RemoteException e) {
            reconnect();
        }
    }

    private void connect() {
        try {
            controller = (IElevator) Naming.lookup(lookupName);
            converter = new InterfaceToModelConverter(controller);
            connected = true;

        } catch (Exception e) {
            connected = false;
        }
    }

    public void reconnect() {
        connected = false;
        Runnable runnable = new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (Boolean.FALSE.equals(connected)) {
                    connect();
                    Thread.sleep(100);
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }
}
