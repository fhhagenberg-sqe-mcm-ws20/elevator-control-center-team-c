package at.fhhagenberg.sqe.converter;

import at.fhhagenberg.sqe.model.Building;
import at.fhhagenberg.sqe.model.Elevator;
import at.fhhagenberg.sqe.model.Floor;
import at.fhhagenberg.sqe.model.IElevator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InterfaceToModelConverter {

    private IElevator elevatorConnection;

    public Building convert() throws RemoteException {
        List<Floor> floors = getFloorsFromInterface();
        List<Elevator> elevators = getElevatorsFromInterface(floors);

        return new Building(elevators, floors);
    }

    private List<Floor> getFloorsFromInterface() throws RemoteException {
        List<Floor> floors = new ArrayList<Floor>();
        int numberOfFloors = elevatorConnection.getFloorNum();
        for (int i = 0; i < numberOfFloors; i++) {
            floors.add(new Floor(i, elevatorConnection.getFloorButtonUp(i), elevatorConnection.getFloorButtonDown(i), elevatorConnection.getFloorHeight()));
        }
        return floors;
    }

    private List<Elevator> getElevatorsFromInterface(List<Floor> floors) throws RemoteException {
        List<Elevator> elevators = new ArrayList<Elevator>();
        int numberOfElevators = elevatorConnection.getElevatorNum();

        for (int i = 0; i < numberOfElevators; i++) {
            HashMap<Floor, Boolean> floorButtonMap = new HashMap<>();
            for (Floor floor : floors) {
                if (elevatorConnection.getServicesFloors(i, floor.getNumber())) {
                    floorButtonMap.put(floor, elevatorConnection.getElevatorButton(i, floor.getNumber()));
                }
            }
            elevators.add(new Elevator(i, elevatorConnection.getCommittedDirection(i), elevatorConnection.getElevatorAccel(i), elevatorConnection.getElevatorDoorStatus(i), floors.get(elevatorConnection.getElevatorFloor(i)), elevatorConnection.getElevatorPosition(i), elevatorConnection.getElevatorSpeed(i), elevatorConnection.getElevatorWeight(i), elevatorConnection.getElevatorCapacity(i), floors.get(elevatorConnection.getTarget(i)), floorButtonMap));
        }
        return elevators;
    }
}
