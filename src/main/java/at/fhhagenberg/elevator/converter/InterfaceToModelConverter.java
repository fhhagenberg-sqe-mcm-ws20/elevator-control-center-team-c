package at.fhhagenberg.elevator.converter;

import at.fhhagenberg.elevator.model.Building;
import at.fhhagenberg.elevator.model.Elevator;
import at.fhhagenberg.elevator.model.Floor;
import sqelevator.IElevator;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which holds the connection to the elevator interface and converts the information of the interface to the data model
 * <p>
 * The converts first reads out all floors and then all elevators, connecting the elevators to the floors it services
 * The connection of elevators and floor is currently stored inside the elevator class.
 */
public class InterfaceToModelConverter {

    /**
     * Elevator interface to retrieve data from
     */
    private IElevator elevatorConnection;

    public InterfaceToModelConverter(IElevator elevatorConnection) {
        this.elevatorConnection = elevatorConnection;
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
     * @return boolean if building object was updated
     * @throws RemoteException
     */
    public boolean convert(Building building) throws RemoteException {
        Long firstClockTick = elevatorConnection.getClockTick();
        if (!firstClockTick.equals(lastClockTick)) {
            List<Floor> floors = getFloorsFromInterface();
            if (!compareTicks(firstClockTick, elevatorConnection.getClockTick())) {
                return false;
            }
            List<Elevator> elevators = getElevatorsFromInterface(floors);
            if (!compareTicks(firstClockTick, elevatorConnection.getClockTick())) {
                return false;
            }
            int floorHeight = getFloorHeight();
            lastClockTick = elevatorConnection.getClockTick();
            if (compareTicks(firstClockTick, lastClockTick)) {
                Building newBuildingMapping = new Building(elevators, floors, floorHeight);
                building.copyValues(newBuildingMapping);
                return true;
            }
        }
        return false;
    }

    /**
     * Converts the information about floors from the elevator interface
     * Reads the number of floors and the creates as many Floor objects
     *
     * @return list of floors
     * @throws RemoteException
     */
    private List<Floor> getFloorsFromInterface() throws RemoteException {
        int numberOfFloors = elevatorConnection.getFloorNum();
        List<Floor> floors = new ArrayList<>();
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
        List<Elevator> elevators = new ArrayList<>();
        int numberOfElevators = elevatorConnection.getElevatorNum();
        for (int i = 0; i < numberOfElevators; i++) {
            List<Boolean> floorButtons = new ArrayList<>();
            List<Integer> listOfServicedFloors = new ArrayList<>();
            for (int j = 0; j < floors.size(); j++) {
                if (elevatorConnection.getServicesFloors(i, j)) {
                    listOfServicedFloors.add(j);
                }
                floorButtons.add(elevatorConnection.getElevatorButton(i, j));
            }
            elevators.add(new Elevator(i, elevatorConnection.getCommittedDirection(i), elevatorConnection.getElevatorAccel(i), elevatorConnection.getElevatorDoorStatus(i), elevatorConnection.getElevatorFloor(i), elevatorConnection.getElevatorPosition(i), elevatorConnection.getElevatorSpeed(i), elevatorConnection.getElevatorWeight(i), elevatorConnection.getElevatorCapacity(i), elevatorConnection.getTarget(i), listOfServicedFloors, floorButtons));
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
