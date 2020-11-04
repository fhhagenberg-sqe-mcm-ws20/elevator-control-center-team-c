package at.fhhagenberg.elevator.converter;

import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.model.Elevator;
import at.fhhagenberg.elevator.model.Floor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import sqe.IElevator;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class which holds the connection to the elevator interface and converts the information of the interface to the data model
 *
 * The converts first reads out all floors and then all elevators, connecting the elevators to the floors it services
 * The connection of elevators and floor is currently stored inside the elevator class.
 */
public class InterfaceToModelConverter {

    /**
     * Elevator interface to retrieve data from
     */
    private IElevator elevatorConnection;

    public InterfaceToModelConverter(IElevator elevatorConnection){
        this.elevatorConnection=elevatorConnection;
    }

    private Long lastClockTick = -1L;

    /**
     * Converts the information retrieved from the elevator interface to the data model
     * First converts all floors, then all elevators and connects the elevators to all floors
     * This function creates a new building object and then copies the values to the existing one
     * It also checks the clocktick of the interface and throws away the building object, which means it isn't copied
     * if the the clocktick before extracting anything and the clocktick after extracting everything doesn't match
     *
     * @param building the building to which the new values should be copied
     * @return data model representation of the building as a Building object
     * @throws RemoteException
     */
    public void convert(Building building) throws RemoteException {
        Long firstClockTick = elevatorConnection.getClockTick();
        if (!firstClockTick.equals(lastClockTick)) {
            List<Floor> floors = getFloorsFromInterface();
            List<Elevator> elevators = getElevatorsFromInterface(floors);
            Building newBuildingMapping = new Building(elevators, floors, getFloorHeight());
            lastClockTick = elevatorConnection.getClockTick();

            if (compareTicks(firstClockTick, lastClockTick)) {
                if (building.isEmpty()) {
                    building.setFloors(newBuildingMapping.getFloors());
                    building.setElevators(newBuildingMapping.getElevators());
                    building.setFloorHeight(newBuildingMapping.getFloorHeight());
                } else {
                    building.copyValues(newBuildingMapping);
                }
            }
        }
    }

    /**
     * Converts the information about floors from the elevator interface
     * Reads the number of floors and the creates as many Floor objects
     *
     * @return list of floors
     * @throws RemoteException
     */
    private List<Floor> getFloorsFromInterface() throws RemoteException {
        List<Floor> floors = new ArrayList<Floor>();
        int numberOfFloors = elevatorConnection.getFloorNum();
        for (int i = 0; i < numberOfFloors; i++) {
            floors.add(new Floor(i, elevatorConnection.getFloorButtonUp(i), elevatorConnection.getFloorButtonDown(i)));
        }
        return floors;
    }

    /**
     * Converts the information about elevators from the elevator interface
     * Reads the number of elevators and the creates as many Elevator objects
     * Connects the elevators to the floors according to the getServicesFloor() information from the interface
     *
     * @param floors all floors of the building to connect it to the elevators
     * @return list of all elevators
     * @throws RemoteException
     */
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

    /**
     * Extracts the floorHeight from the interface
     *
     * @return
     * @throws RemoteException
     */
    private int getFloorHeight() throws RemoteException {
        return elevatorConnection.getFloorHeight();
    }

    /**
     * Compares two clock ticks of the interface
     *
     * @param firstClockTick
     * @param secondClockTick
     * @return if the two clock ticks have the same values
     */
    private boolean compareTicks(Long firstClockTick, long secondClockTick) {
        return firstClockTick == secondClockTick;
    }
}
