package at.fhhagenberg.sqe.converter;

import at.fhhagenberg.sqe.model.Building;
import at.fhhagenberg.sqe.model.Elevator;
import at.fhhagenberg.sqe.model.Floor;
import at.fhhagenberg.sqe.model.IElevator;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InterfaceToModelConverter {
    private Building convert(IElevator es) throws RemoteException {
        int numberOfElevators = es.getElevatorNum();
        int numberOfFloors = es.getFloorNum();
        List<Floor> floors = new ArrayList<Floor>();
        List<Elevator> elevators = new ArrayList<Elevator>();
        for (int i = 0; i < numberOfFloors; i++) {
            floors.add(new Floor(i, es.getFloorButtonUp(i), es.getFloorButtonDown(i), es.getFloorHeight()));
        }
        for (int i = 0; i < numberOfElevators; i++) {
            HashMap<Floor, Boolean> floorButtonMap = new HashMap<>();
            for (Floor floor : floors) {
                if (es.getServicesFloors(i, floor.getNumber())) {
                    floorButtonMap.put(floor, es.getElevatorButton(i, floor.getNumber()));
                }
            }
            elevators.add(new Elevator(i, es.getCommittedDirection(i), es.getElevatorAccel(i), es.getElevatorDoorStatus(i), floors.get(es.getElevatorFloor(i)), es.getElevatorPosition(i), es.getElevatorSpeed(i), es.getElevatorWeight(i), es.getElevatorCapacity(i), floors.get(es.getTarget(i)), floorButtonMap));
        }
        return new Building(elevators, floors);
    }
}
